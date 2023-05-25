package com.wu.ming.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface YAMLService {
    /**
     * @author: 小C
     * yaml转换json
     * @param  yamlString
     * @return    转换后的字符串
     */
    String toJSON(String yamlString) throws JsonProcessingException;
    /**
     * @author: 小C
     * yaml转换xml
     * @param  yamlString
     * @return    转换后的字符串
     */
    String toXML(String yamlString) throws JsonProcessingException;
    /**
     * @author: 小C
     * yaml转换csv
     * @param  yamlString
     * @return    转换后的字符串
     */
    String toCSV(String yamlString) throws JsonProcessingException;
    /**
     * @author: 小C
     * yaml文件转换json文件
     * @param  file
     * @return    转换后的文件
     */
    ResponseEntity<byte[]> fileYamlToJson(MultipartFile file) throws IOException;
    /**
     * @author: 小C
     * yaml文件转换xml文件
     * @param  file
     * @return    转换后的文件
     */
    ResponseEntity<byte[]> fileYamlToXml(MultipartFile file) throws IOException;
    /**
     * @author: 小C
     * yaml文件转换csv文件
     * @param  file
     * @return    转换后的文件
     */
    ResponseEntity<byte[]> fileYamlToCsv(MultipartFile file) throws  IOException;
}
