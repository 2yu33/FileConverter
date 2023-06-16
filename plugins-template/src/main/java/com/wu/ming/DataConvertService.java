package com.wu.ming;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
     * 文件数据转换
     */
    ResponseEntity<byte[]> fileDataConvert(MultipartFile file) throws IOException;

    /**
     * 获取当前的转换类型
     * @return 转换类型 input2output
     */
    String getConvertType();
}
