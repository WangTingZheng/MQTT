package com.wangtingzheng.mqtt.device;

/**
 * @author WangTingZheng
 * @date 2020/6/22 14:42
 * @features
 */
public class ListenerDeviceLogin {
    private  String productKey;
    private  String deviceName;
    private  String deviceSecret;

    public ListenerDeviceLogin(String productKey, String deviceName, String deviceSecret) {
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.deviceSecret = deviceSecret;
    }

    public String getProductKey() {
        return productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceSecret() {
        return deviceSecret;
    }
}
