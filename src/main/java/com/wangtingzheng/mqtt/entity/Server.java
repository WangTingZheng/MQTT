package com.wangtingzheng.mqtt.entity;

import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.wangtingzheng.mqtt.aliyun.Device;
import com.wangtingzheng.mqtt.deal.DealServer;


import java.io.UnsupportedEncodingException;

public class Server {

    private String productKey;
    private String deviceName;
    private String deviceSecret;
    private String rrpcTopic;
    private DealServer dealServer;

    public Server(String productKey, String deviceName, String deviceSecret, DealServer dealServer) {
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.deviceSecret = deviceSecret;
        this.dealServer = dealServer;
        rrpcTopic = "/sys/" + productKey + "/" + deviceName + "/rrpc/request/+";
    }

    public DealServer getDealServer() {
        return dealServer;
    }

    public void start() throws InterruptedException {
        registerNotifyListener();
        Device.connect(productKey, deviceName, deviceSecret);
        Device.subscribe(rrpcTopic);
    }
    public void registerNotifyListener() {
        LinkKit.getInstance().registerOnNotifyListener(new IConnectNotifyListener() {
            @Override
            public boolean shouldHandle(String connectId, String topic) {
                // 只处理特定topic的消息
                if (topic.contains("/rrpc/request/")) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onNotify(String connectId, String topic, AMessage aMessage) {
                try {
                    String payload = new String((byte[]) aMessage.getData(), "UTF-8");
                    String replay;
                    replay = getDealServer().deal_send_back(payload);
                    String response = topic.replace("/request/", "/response/");
                    if (replay != null)
                        Device.publish(response, replay);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onConnectStateChange(String connectId, ConnectState connectState) {
            }
        });
    }
}
