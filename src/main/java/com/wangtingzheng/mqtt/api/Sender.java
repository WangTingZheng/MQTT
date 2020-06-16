package com.wangtingzheng.mqtt.api;

import com.aliyuncs.exceptions.ClientException;
import com.wangtingzheng.mqtt.deal.DealClient;
import com.wangtingzheng.mqtt.device.Access;
import com.wangtingzheng.mqtt.entity.Client;
import com.wangtingzheng.mqtt.entity.ListenerDevice;


/**
 * @author WangTingZheng
 * @date 2020/5/19 22:03
 * @features
 */
public class Sender {
    private Client client;

    public Sender(ListenerDevice listener, Access access, DealClient dealClient) {
        client = new Client(access.getAccessKey(),access.getAccessSecret(),listener.getProductKey(),listener.getDeviceName(),dealClient);
    }

    public Sender(ListenerDevice listener, Access access,String topic, DealClient dealClient) {
        client = new Client(access.getAccessKey(),access.getAccessSecret(),listener.getProductKey(),listener.getDeviceName(),topic,dealClient);
    }



    public void send(String msg) {
        try {
            client.Mqtt(msg);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}