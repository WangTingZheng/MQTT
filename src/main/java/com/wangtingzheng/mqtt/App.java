package com.wangtingzheng.mqtt;

import com.aliyuncs.exceptions.ClientException;
import com.wangtingzheng.mqtt.api.Listener;
import com.wangtingzheng.mqtt.api.Sender;
import com.wangtingzheng.mqtt.deal.DealClient;
import com.wangtingzheng.mqtt.deal.DealServer;
import com.wangtingzheng.mqtt.device.GetDevice;

import java.io.*;

/**
 * @author WangTingZheng
 * @date 2020/5/19 22:19
 * @features
 */
public class App {
    public static void main(String[] args) throws IOException, ClientException, InterruptedException {
        GetDevice getDevice = new GetDevice("device.properties");
        Listener listener = new Listener(getDevice.getProductKey(), getDevice.getDeviceName(), getDevice.getDeviceSecret(), new DealServer() {
            @Override
            public String deal_send_back(String msg) {
                if ("hello".equals(msg))
                {
                    return "yes";
                }
                return null;
            }
        });
        listener.setAccessable(getDevice.getAccessKey(),getDevice.getAccessSecret());
        listener.start();

        Sender sender = new Sender();
        sender.send(listener, "hello", new DealClient() {
            @Override
            public void deal_send_back(String send_back) {
                if ("yes".equals(send_back))
                {
                    System.out.println("done! I got " + send_back);
                }
            }
        });
    }
}
