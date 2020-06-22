package com.wangtingzheng.mqtt;

import com.wangtingzheng.mqtt.api.Listener;
import com.wangtingzheng.mqtt.api.Sender;
import com.wangtingzheng.mqtt.device.GetDevice;


/**
 * @author WangTingZheng
 * @date 2020/5/19 22:19
 * @features
 */
public class App {
    public static void main(String[] args) throws InterruptedException {

        GetDevice getDevice = new GetDevice("device.properties");

        Listener listener = new Listener(getDevice.getListenerDeviceLogin(), msg -> {
            System.out.println("msg "+msg);
            return "ok";
        });
        listener.start();


        Sender sender = new Sender(listener.toListenerDevice(),getDevice.getAccess(),send_back -> {
            if ("ok".equals(send_back)){
                System.out.println("done! I got " + send_back);
            }
        });
        sender.send("hello");
    }
}
