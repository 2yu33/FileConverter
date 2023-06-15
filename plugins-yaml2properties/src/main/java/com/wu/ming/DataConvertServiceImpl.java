package com.wu.ming;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 数据转换规范
 */
public class DataConvertServiceImpl implements DataConvertService{


    // @Override
    public String dataConvert(String data) {
        return "成功调用:"+data;
    }

    // 文件转换
    // @Override
    public ResponseEntity<byte[]> fileDataConvert(MultipartFile file) {
        return null;
    }

    // @Override
    public String getConvertType() {
        return "Yaml2Properties";
    }
}
