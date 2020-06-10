package com.wangtingzheng.mqtt.api;

import com.wangtingzheng.mqtt.deal.DealServer;
import com.wangtingzheng.mqtt.entity.ListenerDevice;
import com.wangtingzheng.mqtt.entity.Server;


/**
 * @author WangTingZheng
 * @date 2020/5/19 22:04
 * @features
 */
public class Listener {
    private String productKey;
    private String deviceName;

    private Server server;

    public Listener(String productKey, String deviceName, String deviceSecret, DealServer dealServer) {
        this.productKey = productKey;
        this.deviceName = deviceName;
        server = new Server(productKey,deviceName,deviceSecret, dealServer);
    }

    public void  start() throws InterruptedException {
        server.start();
    }


    public String getProductKey() {
        return productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public ListenerDevice toListenerDevice(){
        return new ListenerDevice(this.productKey, this.deviceName);
    }
}
