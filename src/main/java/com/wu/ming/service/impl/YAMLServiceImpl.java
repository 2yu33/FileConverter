package com.wu.ming.service.impl;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wu.ming.service.YAMLService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

/**
 * @author CH
 * @version 1.0
 * @date 2023/5/12 14:06
 */
@Service
public class YAMLServiceImpl implements YAMLService {
    @Override
    public String toJSON(String yamlString) throws JsonProcessingException {
        try {
            Yaml yaml = new Yaml();
            yaml.load(yamlString);
        } catch (Exception e) {
            return JSON.toJSONString("输入yaml格式错误");
        }
        // 创建YAMLMapper和ObjectMapper实例
        YAMLMapper yamlMapper = new YAMLMapper();
        Object yamlObject = yamlMapper.readValue(yamlString, Object.class);
        return JSON.toJSONString(yamlObject);
    }

    @Override
    public String toXML(String yamlString) throws JsonProcessingException {
        try {
            Yaml yaml = new Yaml();
            yaml.load(yamlString);
        } catch (Exception e) {
            return JSON.toJSONString("输入yaml格式错误");
        }
        // 创建YAMLMapper和ObjectMapper实例
        YAMLMapper yamlMapper = new YAMLMapper();
        ObjectMapper objectMapper = new XmlMapper();
        Object yamlObject = yamlMapper.readValue(yamlString, Object.class);
        String xml = objectMapper.writeValueAsString(yamlObject);
        return xml;
    }

    @Override
    public ResponseEntity<String> toCSV(String yamlString){
        try {
            Yaml yaml = new Yaml();
            yaml.load(yamlString);
        } catch (Exception e) {
            return  new ResponseEntity<>("输入yaml格式错误", HttpStatus.EXPECTATION_FAILED);
        }
        // 创建YAMLMapper和ObjectMapper实例
        YAMLMapper yamlMapper = new YAMLMapper();
        Object yamlObject = null;
        try {
            yamlObject = yamlMapper.readValue(yamlString, Object.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Map<String, Object> yamlMap = (Map<String, Object>) yamlObject;
        StringBuilder csvBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : yamlMap.entrySet()) {
            csvBuilder.append(entry.getKey()).append(",");
            if (entry.getValue() instanceof Map) {
                Map<String, String> mapValue = (Map<String, String>) entry.getValue();
                for (Map.Entry<String, String> mapEntry : mapValue.entrySet()) {
                    csvBuilder.append(mapEntry.getValue()).append(",");
                }
            } else {
                csvBuilder.append(entry.getValue().toString()).append(",");
            }
            csvBuilder.setLength(csvBuilder.length() - 1);
            csvBuilder.append("\n");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        return new ResponseEntity<>(csvBuilder.toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> fileYamlToJson(MultipartFile file) {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> fileYamlToXml(MultipartFile file) {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> fileYamlToCsv(MultipartFile file) {
        return null;
    }


}
