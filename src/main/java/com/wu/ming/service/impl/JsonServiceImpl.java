package com.wu.ming.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.exception.BusinessException;
import com.wu.ming.service.JsonService;
import com.wu.ming.utils.JSONTools;
import org.json.CDL;
import org.json.JSONArray;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service

public class JsonServiceImpl implements JsonService {
    @Override
    public String json2XmlCompress(String jsonString) throws JsonProcessingException {
        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode rootNode = jsonMapper.readTree(jsonString);
        if (rootNode.isArray()) {
            // 如果JSON是一个数组，则将其包装在一个对象中
            ObjectNode wrapperNode = jsonMapper.createObjectNode();
            wrapperNode.set("items", rootNode);
            rootNode = wrapperNode;}
            XmlMapper xmlMapper = new XmlMapper();
            String xmlString ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xmlMapper.writeValueAsString(rootNode);
            return xmlString;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String json2Xml(String jsonString) throws JsonProcessingException {
        // 创建ObjectMapper和XmlMapper对象
        ObjectMapper objectMapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();

        // 将JSON字符串解析为Java对象
        Object json = objectMapper.readValue(jsonString, Object.class);

        // 将Java对象转换为XML字符串
        String xmlString = JSONTools.replaceSpaceWithUnderscoreInXml(xmlMapper.writeValueAsString(json));

        return xmlString;
    }

    @Override
    public String json2Yaml(String jsonString) throws JsonProcessingException {
        String yamlString;
//        如果是json数组则调用该方法，反之调用下面的方法
        if (jsonString.charAt(0) == '[') {
            // 使用Jackson库将JSON字符串解析为List<Map<String, Object>>对象
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> jsonList = objectMapper.readValue(jsonString, List.class);

            // 使用SnakeYAML库将List<Map<String, Object>>对象转换为YAML格式字符串
            Yaml yaml = new Yaml();
            String yamlStr = yaml.dump(jsonList);
            // 使用Jackson库将List<Map<String, Object>>对象转换为YAML格式字符串
            YAMLFactory yamlFactory = new YAMLFactory();
            ObjectMapper yamlMapper = new ObjectMapper(yamlFactory);
            yamlString = yamlMapper.writeValueAsString(jsonList);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            Yaml yaml = new Yaml();
            Map<String, Object> map = (Map<String, Object>) jsonObject;
            yamlString = yaml.dump(map);
        }

        return yamlString;
    }

    @Override
    public String json2Csv(String jsonStr) throws JsonProcessingException {
        String csv;

        if(jsonStr.charAt(0)=='['){
            //        调库将jsonArray转为csv字符串
            JSONArray jsonArray = new JSONArray(jsonStr);
            csv = CDL.toString(jsonArray);

        }
        else {
            JsonNode jsonNode = objectMapper.readTree(jsonStr);

            // Get all field names as CSV header
            StringBuilder header = new StringBuilder();
            jsonNode.fieldNames().forEachRemaining(fieldName -> header.append(fieldName).append(","));
            header.deleteCharAt(header.length() - 1);

            // Get all field values as CSV row
            StringBuilder row = new StringBuilder();
            jsonNode.elements().forEachRemaining(jsonValue -> row.append(jsonValue.asText()).append(","));
            row.deleteCharAt(row.length() - 1);
            csv =header.toString()+"\n"+row.toString();
        }
        return csv;
    }
    @Override
    public ResponseEntity<byte[]> fileJson2Xml(MultipartFile file) throws IOException {
        // 读取上传的JSON文件
        String jsonContent = new String(file.getBytes());
//        json格式校验，不符合则抛出异常
        if (jsonContent ==null)
            throw new BusinessException(ErrorCode.NULL_ERROR);
        JSONTools.isJson(jsonContent);
        String xmlContent = json2Xml(jsonContent);

        // 将XML字符串转换为字节数组
        byte[] xmlBytes = xmlContent.getBytes();



        // 设置响应头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.xml");
        // 指定下载文件的名称和类型
        String fileName = "file.xml";
        String contentType = MediaType.APPLICATION_JSON_VALUE;

        // 创建临时文件
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(xmlBytes);
        }
        // 设置下载响应头
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(org.apache.commons.io.FileUtils.readFileToByteArray(tempFile));

    }

    @Override
    public ResponseEntity<byte[]> fileJson2Yaml(MultipartFile file) throws IOException {
        // 读取上传的JSON文件
        String jsonContent = new String(file.getBytes());
        //        json格式校验，不符合则抛出异常
        JSONTools.isJson(jsonContent);
        //        将读取的json字符串转为yaml
        String yamlContent = json2Yaml(jsonContent);
        // 将yaml字符串压缩，去除掉没有意义的空格和换行符
//        yamlContent= jsonCompression.compress(yamlContent);
        // 将yaml字符串转换为字节数组
        byte[] yamlBytes = yamlContent.getBytes();

        // 设置响应头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.xml");
        // 指定下载文件的名称和类型
        String fileName = "file.yaml";
        String contentType = MediaType.APPLICATION_JSON_VALUE;

        // 创建临时文件
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(yamlBytes);
        }
        // 设置下载响应头
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(org.apache.commons.io.FileUtils.readFileToByteArray(tempFile));

    }

    @Override
    public ResponseEntity<byte[]> fileJson2Csv(MultipartFile file) throws IOException {
        // 读取上传的JSON文件
        String jsonContent = new String(file.getBytes());
        //        json格式校验，不符合则抛出异常
        JSONTools.isJson(jsonContent);
        //        将读取的json字符串转为yaml
        String csvContent = json2Csv(jsonContent);

        // 将yaml字符串转换为字节数组
        byte[] csvBytes = csvContent.getBytes();
        // 设置响应头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.xml");
        // 指定下载文件的名称和类型
        String fileName = "file.csv";
        String contentType = MediaType.APPLICATION_JSON_VALUE;

        // 创建临时文件
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(csvBytes);
        }
        // 设置下载响应头
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(org.apache.commons.io.FileUtils.readFileToByteArray(tempFile));

    }
    @Override
   public ResponseEntity<byte[]> fileJson2XmlCompress(MultipartFile file) throws IOException{
        // 读取上传的JSON文件
        String jsonContent = new String(file.getBytes());
//        json格式校验，不符合则抛出异常
        JSONTools.isJson(jsonContent);
        String xmlContent = json2XmlCompress(jsonContent);

        // 将XML字符串转换为字节数组
        byte[] xmlBytes = xmlContent.getBytes();



        // 设置响应头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.xml");
        // 指定下载文件的名称和类型
        String fileName = "file.xml";
        String contentType = MediaType.APPLICATION_JSON_VALUE;

        // 创建临时文件
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(xmlBytes);
        }
        // 设置下载响应头
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(org.apache.commons.io.FileUtils.readFileToByteArray(tempFile));
    }
}
