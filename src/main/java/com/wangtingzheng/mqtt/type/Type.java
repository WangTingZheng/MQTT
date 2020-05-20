package com.wangtingzheng.mqtt.type;

/**
 * @author WangTingZheng
 * @date 2020/5/20 11:34
 * @features
 */
public class Type {
    static String command_head = "C:";
    static String message_head = "M:";

    public static String toCommand(String data)
    {
        return command_head+data;
    }

    public static String toMessage(String data)
    {
        return message_head+data;
    }

    public static String getData(String inf){
        if (isCommand(inf) || isMessage(inf))
        {
            return inf.substring(2);
        }
        return inf;
    }

    public static boolean isCommand(String data)
    {
        return data.charAt(0) == 'C' && data.charAt(1) == ':';
    }

    public static boolean isMessage(String data){
        return data.charAt(0) == 'M' && data.charAt(1) == ':';
    }
}
