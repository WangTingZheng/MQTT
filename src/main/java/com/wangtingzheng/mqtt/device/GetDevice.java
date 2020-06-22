package com.wangtingzheng.mqtt.device;

import java.io.*;
import java.util.Properties;

/**
 * @author WangTingZheng
 * @date 2020/5/19 23:18
 * @features
 */
public class GetDevice {
    private  String productKey;
    private  String deviceName;
    private  String deviceSecret;

    private  String accessKey;
    private  String accessSecret;


    public GetDevice(String path) {
        Properties properties = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(path));
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        productKey = properties.getProperty("productKey");
        deviceName = properties.getProperty("deviceName");
        deviceSecret = properties.getProperty("deviceSecret");

        accessKey = properties.getProperty("accessKey");
        accessSecret = properties.getProperty("accessSecret");
    }

    public  String getProductKey() {
        return productKey;
    }

    public  String getDeviceName() {
        return deviceName;
    }

    public  String getDeviceSecret() {
        return deviceSecret;
    }

    public  String getAccessKey() {
        return accessKey;
    }

    public  String getAccessSecret() {
        return accessSecret;
    }

    public ListenerDevice getListenerDevice(){
        return new ListenerDevice(productKey,deviceName);
    }

    public ListenerDeviceLogin getListenerDeviceLogin(){
        return new ListenerDeviceLogin(productKey,deviceName,deviceSecret);
    }

    public Access getAccess(){
        return new Access(accessKey,accessSecret);
    }
}
