package com.wu.ming.service.impl;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wu.ming.service.YAMLService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.io.*;;
import java.util.List;
import java.util.Map;

/**
 * @author CH
 * @version 1.0
 * @date 2023/5/12 14:06
 */
@Service
public class YAMLServiceImpl implements YAMLService {
    /**
     * @param yamlString
     * @return 转换后的字符串
     * yaml转换json
     * @author: 小C
     */
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

    /**
     * @param yamlString
     * @return 转换后的字符串
     * yaml转换xml
     * @author: 小C
     */
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

    /**
     * @param yamlString
     * @return 转换后的字符串
     * yaml转换csv
     * @author: 小C
     */
    @Override
    public String toCSV(String yamlString){
        Yaml yaml = new Yaml();

        // 解析 YAML 字符串
        List<Map<String, Object>> data = yaml.load(yamlString);

        // 创建 StringBuilder 以构建 CSV 字符串
        StringBuilder csvBuilder = new StringBuilder();

        // 写入 CSV 标题行
        Map<String, Object> firstItem = data.get(0);
        for (String key : firstItem.keySet()) {
            csvBuilder.append(key).append(",");
        }
        csvBuilder.deleteCharAt(csvBuilder.length() - 1);
        csvBuilder.append("\n");

        // 写入 CSV 数据行
        for (Map<String, Object> item : data) {
            for (Object value : item.values()) {
                csvBuilder.append(value).append(",");
            }
            csvBuilder.deleteCharAt(csvBuilder.length() - 1);
            csvBuilder.append("\n");
        }

        return csvBuilder.toString();
    }

    /**
     * @param file
     * @return 转换后的文件
     * yaml转换json
     * @author: 小C
     */
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        // 将请求中的数据转换为字节数组
        byte[] fileData = this.toJSON(buffer.toString()).getBytes();
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

    /**
     * @param file
     * @return 转换后的文件
     * yaml转换xml
     * @author: 小C
     */
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        // 将请求中的数据转换为字节数组
        byte[] fileData = this.toXML(buffer.toString()).getBytes();
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

    /**
     * @param file
     * @return 转换后的文件
     * yaml转换csv
     * @author: 小C
     */
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        // 将请求中的数据转换为字节数组
        byte[] fileData = this.toCSV(buffer.toString()).getBytes();
        // 指定下载文件的名称和类型
        String fileName = "file.csv";

        // 创建临时文件
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(fileData);
        }
        // 设置下载响应头
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv"))
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(org.apache.commons.io.FileUtils.readFileToByteArray(tempFile));
    }

}
