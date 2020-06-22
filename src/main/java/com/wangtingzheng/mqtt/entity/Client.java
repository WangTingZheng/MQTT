package com.wangtingzheng.mqtt.entity;

/**
 * @author WangTingZheng
 * @date 2020/5/19 22:06
 * @features
 */
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.RRpcRequest;
import com.aliyuncs.iot.model.v20180120.RRpcResponse;
import com.wangtingzheng.mqtt.aliyun.OpenApiClient;
import com.wangtingzheng.mqtt.deal.DealClient;
import org.apache.commons.codec.binary.Base64;


import java.nio.charset.StandardCharsets;

public class Client {
    private String accessKey;
    private String accessSecret;
    private String productKey;
    private String deviceName;
    private String topic;
    private DealClient dealClient;

    public Client(String accessKey, String accessSecret, String productKey, String deviceName, DealClient dealClient) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.dealClient = dealClient;
    }

    public Client(String accessKey, String accessSecret, String productKey, String deviceName, String topic, DealClient dealClient) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.topic = topic;
        this.dealClient = dealClient;
    }

    public void Mqtt(String payload) throws ClientException {
        RRpcRequest request = new RRpcRequest();  //新建阿里RRpc消息对象
        if (topic != null) //如果有自定义的topic
            request.setTopic(topic);  //
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        request.setRequestBase64Byte(Base64.encodeBase64String(payload.getBytes()));
        request.setTimeout(6000);
        DefaultAcsClient client = OpenApiClient.getClient(accessKey, accessSecret);

        RRpcResponse response =  client.getAcsResponse(request);
        if (response != null && "SUCCESS".equals(response.getRrpcCode())) {
            String send_back = new String(Base64.decodeBase64(response.getPayloadBase64Byte()), StandardCharsets.UTF_8);
            dealClient.deal_send_back(send_back);
        }
    }
}
