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


import java.io.UnsupportedEncodingException;

public class Client {
    private String accessKey;
    private String accessSecret;
    private String productKey;
    private String deviceName;
    private DealClient dealClient;

    public Client(String accessKey, String accessSecret, String productKey, String deviceName, DealClient dealClient) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.dealClient = dealClient;
    }


    public void Mqtt(String payload) throws ClientException, UnsupportedEncodingException {
        RRpcRequest request = new RRpcRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        request.setRequestBase64Byte(Base64.encodeBase64String(payload.getBytes()));
        request.setTimeout(6000);
        DefaultAcsClient client = OpenApiClient.getClient(accessKey, accessSecret);

        RRpcResponse response =  client.getAcsResponse(request);
        if (response != null && "SUCCESS".equals(response.getRrpcCode())) {
            String send_back = new String(Base64.decodeBase64(response.getPayloadBase64Byte()), "UTF-8");
            dealClient.deal_send_back(send_back);
        }
    }
}
