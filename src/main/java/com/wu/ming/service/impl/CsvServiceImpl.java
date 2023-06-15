package com.wu.ming.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.common.ResultUtils;
import com.wu.ming.exception.BusinessException;
import com.wu.ming.service.CsvService;
import com.wu.ming.utils.CsvValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvServiceImpl implements CsvService {

    @Autowired
    private CsvValidator csvValidator;
    @Override
    public BaseResponse<String> csvToJson(String csvString) throws IOException, CsvValidationException {
        if (!csvValidator.validateCsv(csvString)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CSVReader reader = new CSVReader(new StringReader(csvString));
        String[] headers = reader.readNext();
        String[] nextLine;
        List<ObjectNode> jsonList = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            ObjectNode json = new ObjectMapper().createObjectNode();
            for (int i = 0; i < headers.length; i++) {
                json.put(headers[i], nextLine[i]);
            }
            jsonList.add(json);
        }
        String json=new ObjectMapper().writeValueAsString(jsonList);
        return ResultUtils.success(json);
    }

    @Override
    public BaseResponse<String> csvToXml(String csvString) throws IOException, CsvValidationException{
        if (!csvValidator.validateCsv(csvString)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CSVReader reader = new CSVReader(new StringReader(csvString));
        String[] headers = reader.readNext();
        String[] nextLine;
        List<Map<String, String>> dataList = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Map<String, String> data = new LinkedHashMap<>();
            for (int i = 0; i < headers.length; i++) {
                data.put(headers[i].replace(" ","_"), nextLine[i]);
            }
            dataList.add(data);
        }
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        String xml=xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataList);
        return ResultUtils.success(xml);
    }

    @Override
    public BaseResponse<String> csvToYaml(String csvString)throws IOException, CsvValidationException {
        if (!csvValidator.validateCsv(csvString)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CSVReader reader = new CSVReader(new StringReader(csvString));
        String[] headers = reader.readNext();
        String[] nextLine;
        List<Map<String, String>> personList = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Map<String, String> person = new LinkedHashMap<>();
            for (int i = 0; i < headers.length; i++) {
                String value = nextLine[i];
                person.put(headers[i], value);
            }
            personList.add(person);
        }
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        StringWriter writer = new StringWriter();
        yaml.dump(personList, writer);
        return ResultUtils.success(writer.toString());
    }

    @Override
    public ResponseEntity fileCsvToJson(MultipartFile file) throws IOException, CsvValidationException {
        if (!csvValidator.fileValidateCsv(file)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
        String[] headers = reader.readNext();
        String[] nextLine;
        List<Map<String, String>> personList = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Map<String, String> person = new LinkedHashMap<>();
            for (int i = 0; i < headers.length; i++) {
                String value = nextLine[i];
                person.put(headers[i], value);
            }
            personList.add(person);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 将请求中的数据转换为字节数组
        byte[] fileData = mapper.writeValueAsString(personList).getBytes();

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
    public ResponseEntity fileCsvToXml(MultipartFile file) throws IOException, CsvValidationException {
        if (!csvValidator.fileValidateCsv(file)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
        String[] headers = reader.readNext();
        String[] nextLine;
        List<Map<String, String>> dataList = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Map<String, String> data = new LinkedHashMap<>();
            for (int i = 0; i < headers.length; i++) {
                data.put(headers[i].replace(" ","_"), nextLine[i]);
            }
            dataList.add(data);
        }
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        // 将请求中的数据转换为字节数组
        byte[] fileData = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataList).getBytes();

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
    public ResponseEntity fileCsvToYaml(MultipartFile file) throws IOException, CsvValidationException {
        if (!csvValidator.fileValidateCsv(file)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
        String[] headers = reader.readNext();
        String[] nextLine;
        List<Map<String, String>> personList = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Map<String, String> person = new LinkedHashMap<>();
            for (int i = 0; i < headers.length; i++) {
                String value = nextLine[i];
                person.put(headers[i], value);
            }
            personList.add(person);
        }
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        StringWriter writer = new StringWriter();
        yaml.dump(personList, writer);
        // 将请求中的数据转换为字节数组
        byte[] fileData = writer.toString().getBytes();

        // 指定下载文件的名称和类型
        String fileName = "file.yaml";
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
                .body(org.apache.commons.io.FileUtils.readFileToByteArray(tempFile));
    }

}
