package com.wangtingzheng.mqtt.entity;

import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.wangtingzheng.mqtt.aliyun.Device;
import com.wangtingzheng.mqtt.deal.DealServer;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private String productKey; //产品key
    private String deviceName; //设备名称
    private String deviceSecret; //设备密钥
    private List<String> rrpcTopic = new ArrayList<>();  //监视的topic的列表
    private DealServer dealServer; //包含信息处理函数的类

    public Server(String productKey, String deviceName, String deviceSecret, DealServer dealServer) {
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.deviceSecret = deviceSecret;
        this.dealServer = dealServer;
        rrpcTopic.add("/sys/" + productKey + "/" + deviceName + "/rrpc/request/+");
        //harewareTopic = "/"+productKey+"/"+deviceName+"/user/get";
    }

    public Server(String productKey, String deviceName, String deviceSecret, String rrpcTopic, DealServer dealServer) {
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.deviceSecret = deviceSecret;
        this.rrpcTopic.add(rrpcTopic);
        this.dealServer = dealServer;
    }

    public Server(String productKey, String deviceName, String deviceSecret, String[] rrpcTopic, DealServer dealServer) {
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.deviceSecret = deviceSecret;
        Collections.addAll(this.rrpcTopic, rrpcTopic);
        this.dealServer = dealServer;
    }


    public DealServer getDealServer() {
        return dealServer;
    }

    public void start() throws InterruptedException {
        registerNotifyListener();
        Device.connect(productKey, deviceName, deviceSecret);
        for (String topic : rrpcTopic){
            Device.subscribe(topic);
        }
    }
    public void registerNotifyListener() {
        LinkKit.getInstance().registerOnNotifyListener(new IConnectNotifyListener() {
            @Override
            public boolean shouldHandle(String connectId, String topic) {
                // 只处理特定topic的消息
                return topic.contains("/rrpc/request/");
            }

            @Override
            public void onNotify(String connectId, String topic, AMessage aMessage) {
                System.out.println("has data");
                String payload = new String((byte[]) aMessage.getData(), StandardCharsets.UTF_8);
                String replay;
                replay = getDealServer().deal_send_back(payload);
                String response = topic.replace("/request/", "/response/");
                if (replay != null)
                    Device.publish(response, replay);
            }
            @Override
            public void onConnectStateChange(String connectId, ConnectState connectState) {
            }
        });
    }
}
