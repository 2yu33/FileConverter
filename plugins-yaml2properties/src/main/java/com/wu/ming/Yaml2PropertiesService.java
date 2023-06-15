package com.wu.ming;

/**
 * 数据转换规范
 */
public class Yaml2PropertiesService implements DataConvertService{


    @Override
    public String dataConvert(String data) {
        return "成功调用:"+data;
    }

    @Override
    public String getConvertType() {
        return "Yaml2Properties";
    }
}
