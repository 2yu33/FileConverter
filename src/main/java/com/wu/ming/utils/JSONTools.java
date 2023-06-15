package com.wu.ming.utils;

import com.alibaba.fastjson.JSON;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.exception.BusinessException;

public class JSONTools {
    /**
     * 将传入的xml中不合法的属性替换为下划线，仅允许字母等合法的属性名称
     * @param xmlStr 传入的xml字符串
     * @return
     */
    public static String replaceSpaceWithUnderscoreInXml(String xmlStr) {
        StringBuilder result = new StringBuilder();
        boolean inTag = false;
        for (int i = 0; i < xmlStr.length(); i++) {
            char c = xmlStr.charAt(i);
            if (c == '<') {
                inTag = true;
            } else if (c == '>') {
                inTag = false;
            }
            if (inTag) {
                if (c == ' ') {
                    result.append('_');
                } else {
                    result.append(c);
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * 验证上传的字符串是否是合法的json字符串
     * @param jsonStr
     */
    public static void isJson(String jsonStr ){
        if (jsonStr ==null)
            throw new BusinessException(ErrorCode.NULL_ERROR);
        try {
            JSON.parse(jsonStr);
            System.out.println("The string is a valid JSON.");
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
}
