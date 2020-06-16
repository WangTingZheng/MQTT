package com.wangtingzheng.mqtt;

import com.wangtingzheng.mqtt.api.Listener;
import com.wangtingzheng.mqtt.api.Sender;
import com.wangtingzheng.mqtt.device.Access;
import com.wangtingzheng.mqtt.device.GetDevice;
import java.io.*;

/**
 * @author WangTingZheng
 * @date 2020/5/19 22:19
 * @features
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        GetDevice getDevice = new GetDevice("device.properties");
        Access access = new Access(getDevice.getAccessKey(),getDevice.getAccessSecret());

        Listener listener = new Listener(getDevice.getProductKey(), getDevice.getDeviceName(), getDevice.getDeviceSecret(), msg -> {
            System.out.println("msg "+msg);
            return "ok";
        });
        listener.start();


        Sender sender = new Sender(listener.toListenerDevice(),access,send_back -> {
            if ("ok".equals(send_back)){
                System.out.println("done! I got " + send_back);
            }
        });
        sender.send("hello");
    }
}
