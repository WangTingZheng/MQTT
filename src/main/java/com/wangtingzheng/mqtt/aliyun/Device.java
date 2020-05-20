package com.wangtingzheng.mqtt.aliyun;

import com.aliyun.alink.dm.api.DeviceInfo;
import com.aliyun.alink.dm.api.InitResult;
import com.aliyun.alink.linkkit.api.ILinkKitConnectListener;
import com.aliyun.alink.linkkit.api.IoTMqttClientConfig;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linkkit.api.LinkKitInitParams;
import com.aliyun.alink.linksdk.channel.core.base.AError;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttSubscribeRequest;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectUnscribeListener;

public class Device {
    /**
     * 建立连接
     *
     * @param productKey 产品key
     * @param deviceName 设备名称
     * @param deviceSecret 设备密钥
     * @throws InterruptedException
     */
    public static void connect(String productKey, String deviceName, String deviceSecret) throws InterruptedException {

        // 初始化参数
        LinkKitInitParams params = new LinkKitInitParams();

        // 设置 Mqtt 初始化参数
        IoTMqttClientConfig config = new IoTMqttClientConfig();
        config.productKey = productKey;
        config.deviceName = deviceName;
        config.deviceSecret = deviceSecret;
        params.mqttClientConfig = config;

        // 设置初始化设备证书信息，传入：
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.productKey = productKey;
        deviceInfo.deviceName = deviceName;
        deviceInfo.deviceSecret = deviceSecret;
        params.deviceInfo = deviceInfo;

        // 初始化
        LinkKit.getInstance().init(params, new ILinkKitConnectListener() {
            public void onError(AError aError) {
                System.out.println("init failed !! code=" + aError.getCode() + ",msg=" + aError.getMsg() + ",subCode="
                        + aError.getSubCode() + ",subMsg=" + aError.getSubMsg());
            }

            @Override
            public void onError(com.aliyun.alink.linksdk.tools.AError aError) {

            }

            @Override
            public void onInitDone(InitResult initResult) {
                System.out.println("init success !!");
            }
        });

        // 确保初始化成功后才执行后面的步骤，可以根据实际情况适当延长这里的延时
        Thread.sleep(2000);
    }

    /**
     * 发布消息
     *
     * @param topic 发送消息的topic
     * @param payload 发送的消息内容
     */
    public static void publish(String topic, String payload) {
        MqttPublishRequest request = new MqttPublishRequest();
        request.topic = topic;
        request.payloadObj = payload;
        request.qos = 0;
        LinkKit.getInstance().getMqttClient().publish(request, new IConnectSendListener() {
            @Override
            public void onResponse(ARequest aRequest, AResponse aResponse) {
            }

            @Override
            public void onFailure(ARequest aRequest, com.aliyun.alink.linksdk.tools.AError aError) {

            }

        });
    }

    /**
     * 订阅消息
     *
     * @param topic 订阅消息的topic
     */
    public static void subscribe(String topic) {
        MqttSubscribeRequest request = new MqttSubscribeRequest();
        request.topic = topic;
        request.isSubscribe = true;
        LinkKit.getInstance().getMqttClient().subscribe(request, new IConnectSubscribeListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(com.aliyun.alink.linksdk.tools.AError aError) {

            }
        });
    }

    /**
     * 取消订阅
     *
     * @param topic 取消订阅消息的topic
     */
    public static void unsubscribe(String topic) {
        MqttSubscribeRequest request = new MqttSubscribeRequest();
        request.topic = topic;
        request.isSubscribe = false;
        LinkKit.getInstance().getMqttClient().unsubscribe(request, new IConnectUnscribeListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(com.aliyun.alink.linksdk.tools.AError aError) {

            }
        });
    }

    /**
     * 断开连接
     */
    public static void disconnect() {
        // 反初始化
        LinkKit.getInstance().deinit();
    }
}