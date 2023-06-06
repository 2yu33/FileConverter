package com.wu.ming.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface JsonService {
    /**
     * json转xml
     * @param json
     * @return 返回xml字符串
     */
     String json2Xml(String json) throws JsonProcessingException;

    /**
     * json转yaml
     * @param json
     * @return  返回yaml字符串
     */
     String json2Yaml(String json) throws JsonProcessingException;

    /**
     * json转xml
     * @param json
     * @return   返回csv字符串
     */
     String json2Csv(String json) throws JsonProcessingException;
    /**
     * json转xml
     * @param file 上传的文件
     * @return  返回xml格式的文件
     */
    ResponseEntity<byte[]> fileJson2Xml(MultipartFile file) throws IOException;
    /**
     * json转yaml
     * @param file 上传的文件
     * @return 返回yaml的文件
     */
    ResponseEntity<byte[]> fileJson2Yaml(MultipartFile file) throws IOException;
    /**
     * json转csv
     * @param file 上传的文件
     * @return 返回csv类型的文件
     */
    /**
     * json转xml
     * @param json
     * @return 返回压缩的xml字符串
     */
    String json2XmlCompress(String json) throws JsonProcessingException;
    ResponseEntity<byte[]> fileJson2Csv(MultipartFile file) throws IOException;
    /**
     * json转xml
     * @param file 上传的文件
     * @return  返回压缩后的xml格式的文件
     */
    ResponseEntity<byte[]> fileJson2XmlCompress(MultipartFile file) throws IOException;
}
