package com.wu.ming.service.impl;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wu.ming.service.YAMLService;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
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
    public String toCSV(String yamlString){
        try {
            Yaml yaml = new Yaml();
            yaml.load(yamlString);
        } catch (Exception e) {
            return  JSON.toJSONString("输入yaml格式错误");
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

        return csvBuilder.toString();
    }

    @Override
    public ResponseEntity<byte[]> fileYamlToJson(MultipartFile file) throws IOException {
        //读取文件
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line = reader.readLine();
            while (StringUtils.hasLength(line)) {
                buffer.append(line);
                buffer.append("\n");
                line = reader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        // 将请求中的数据转换为字节数组
       byte[] fileData=this.toJSON(buffer.toString()).getBytes();
        // 指定下载文件的名称和类型
        String fileName = "file.json";
        String contentType = MediaType.APPLICATION_JSON_VALUE;

        // 创建临时文件
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(fileData);
        }
        // 设置下载响应头
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(org.apache.commons.io.FileUtils.readFileToByteArray(tempFile));
    }

    @Override
    public ResponseEntity<byte[]> fileYamlToXml(MultipartFile file) throws IOException {
        //读取文件
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line = reader.readLine();
            while (StringUtils.hasLength(line)) {
                buffer.append(line);
                buffer.append("\n");
                line = reader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        // 将请求中的数据转换为字节数组
        byte[] fileData=this.toXML(buffer.toString()).getBytes();
        // 指定下载文件的名称和类型
        String fileName = "file.xml";
        String contentType = MediaType.APPLICATION_XML_VALUE;

        // 创建临时文件
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(fileData);
        }
        // 设置下载响应头
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(org.apache.commons.io.FileUtils.readFileToByteArray(tempFile));
    }

    @Override
    public ResponseEntity<byte[]> fileYamlToCsv(MultipartFile file) throws IOException {
        //读取文件
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line = reader.readLine();
            while (StringUtils.hasLength(line)) {
                buffer.append(line);
                buffer.append("\n");
                line = reader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        // 将请求中的数据转换为字节数组
        byte[] fileData=this.toCSV(buffer.toString()).getBytes();
        // 指定下载文件的名称和类型
        String fileName = "file.csv";
        String contentType = MediaType.TEXT_PLAIN_VALUE;

        // 创建临时文件
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(fileData);
        }
        // 设置下载响应头
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(FileUtils.readFileToByteArray(tempFile));
    }


}
