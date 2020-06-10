package com.wangtingzheng.mqtt.entity;

/**
 * @author WangTingZheng
 * @date 2020/6/10 17:33
 * @features
 */
public class ListenerDevice {
    private String productKey;
    private String deviceName;

    public ListenerDevice(String productKey, String deviceName) {
        this.productKey = productKey;
        this.deviceName = deviceName;
    }

    public String getProductKey() {
        return productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }
}
