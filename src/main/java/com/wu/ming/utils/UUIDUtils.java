package com.wu.ming.utils;

import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtils {


    public UUIDUtils() {
    }

    /**
     * 获得一个唯一性UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        //去掉"-"
        return  UUID.randomUUID().toString().replaceAll("-","");

        //不去掉"-"
        // return  UUID.randomUUID().toString();
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }

    /**
     * 生成随机纯数字
     * @return
     */
    public static String getUuidNum() {
        int hashCode = java.util.UUID.randomUUID().toString().hashCode();
        if (hashCode <0){
            hashCode=-hashCode;
        }
        // 0 代表前面补充0
        // 10 代表长度为10
        // d 代表参数为正数型
        return String.format("%010d", hashCode).substring(0, 10);
    }

    public static void main(String[] args){
        System.out.println(getUuidNum());
    }
}
