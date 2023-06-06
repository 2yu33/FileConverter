package com.wu.ming.utils;

import com.alibaba.fastjson.JSON;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.exception.BusinessException;
import org.json.JSONException;

public class jsonValidation {
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
