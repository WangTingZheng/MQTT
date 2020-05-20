package com.wangtingzheng.mqtt.device;

/**
 * @author WangTingZheng
 * @date 2020/5/20 10:49
 * @features
 */
public class Access {
    private  String accessKey;
    private  String accessSecret;

    public Access(String accessKey, String accessSecret) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }
}
