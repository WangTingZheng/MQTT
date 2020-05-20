package com.wangtingzheng.mqtt.api;

import com.aliyuncs.exceptions.ClientException;
import com.wangtingzheng.mqtt.deal.DealClient;
import com.wangtingzheng.mqtt.device.Access;
import com.wangtingzheng.mqtt.entity.Client;


import java.io.UnsupportedEncodingException;
import java.rmi.ServerException;

/**
 * @author WangTingZheng
 * @date 2020/5/19 22:03
 * @features
 */
public class Sender {
    private Client client;

    public void send(Listener listener, Access access, String msg, DealClient dealClient) throws ServerException, ClientException, UnsupportedEncodingException {
        client = new Client(access.getAccessKey(),access.getAccessSecret(),listener.getProductKey(),listener.getDeviceName(), dealClient);
        client.Mqtt(msg);
    }
}
