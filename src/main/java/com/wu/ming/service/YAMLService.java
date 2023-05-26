package com.wu.ming.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface YAMLService {
    /**
     * @author: 小C
     * yaml转换json
     * @param  yamlString
     * @return 转换后的字符串
     */
    String toJSON(String yamlString) throws JsonProcessingException;

    /**
     * @author: 小C
     * @param yamlString
     * @return 转换后的字符串
     * yaml转换xml
     */
    String toXML(String yamlString) throws JsonProcessingException;

    /**
     * @author: 小C
     * @param yamlString
     * @return 转换后的字符串
     * yaml转换csv
     */
    String toCSV(String yamlString) throws IOException;

    /**
     * @author: 小C
     * @param file
     * @return 转换后的文件
     * yaml文件转换json文件
     */
    ResponseEntity<byte[]> fileYamlToJson(MultipartFile file) throws IOException;

    /**
     * @author: 小C
     * @param file
     * @return 转换后的文件
     * yaml文件转换xml文件
     */
    ResponseEntity<byte[]> fileYamlToXml(MultipartFile file) throws IOException;
    /**
     * @author: 小C
     * yaml文件转换csv文件
     * @param  file
     * @return 转换后的文件
     */
    ResponseEntity<byte[]> fileYamlToCsv(MultipartFile file) throws IOException;
}
