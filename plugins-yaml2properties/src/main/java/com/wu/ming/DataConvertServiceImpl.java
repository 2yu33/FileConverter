package com.wu.ming;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 数据转换规范
 */
public class DataConvertServiceImpl implements DataConvertService{


    @Override
    public String dataConvert(String data) {

        return "成功调用:"+data;
    }

    @Override
    public String getConvertType() {
        return "Yaml2Properties";
    }
}
