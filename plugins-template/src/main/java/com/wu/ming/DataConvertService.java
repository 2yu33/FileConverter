package com.wu.ming;

/**
 * 数据转换规范
 */
public interface DataConvertService {

    /**
     * 数据转换
     * @return       转换返回结果
     */
    String dataConvert(String data);

    /**
     * 获取当前的转换类型
     * @return 转换类型
     */
    String getConvertType();
}
