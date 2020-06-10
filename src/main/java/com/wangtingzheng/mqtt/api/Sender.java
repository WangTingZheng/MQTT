package com.wangtingzheng.mqtt.api;

import com.aliyuncs.exceptions.ClientException;
import com.wangtingzheng.mqtt.deal.DealClient;
import com.wangtingzheng.mqtt.device.Access;
import com.wangtingzheng.mqtt.entity.Client;
import com.wangtingzheng.mqtt.entity.ListenerDevice;
import com.wangtingzheng.mqtt.type.Type;

import java.io.UnsupportedEncodingException;

/**
 * @author WangTingZheng
 * @date 2020/5/19 22:03
 * @features
 */
public class Sender {
    private Client client;

    public void send(ListenerDevice listener, Access access, String msg, DealClient dealClient) {
        client = new Client(access.getAccessKey(),access.getAccessSecret(),listener.getProductKey(),listener.getDeviceName(), dealClient);
        try {
            client.Mqtt(msg);
        } catch (ClientException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void send_command(ListenerDevice listener, Access access, String msg, DealClient dealClient)  {
        send(listener, access, Type.toCommand(msg), dealClient);
    }

    public void send_message(ListenerDevice listener, Access access, String msg, DealClient dealClient) {
        send(listener, access, Type.toMessage(msg), dealClient);
    }

}