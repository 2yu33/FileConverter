package com.wu.ming.controller;



import com.opencsv.exceptions.CsvValidationException;
import com.wu.ming.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class CsvController {

    @Autowired
    private CsvService csvService;
    @PostMapping("/csv2json")
    public String csvToJson(@RequestBody String csvString) throws IOException, CsvValidationException {
        return csvService.csvToJson(csvString);
    }
    @PostMapping("/csv2xml")
    public String csvToXml(@RequestBody String csvString) throws IOException, CsvValidationException {
        return csvService.csvToXml(csvString);
    }
    @PostMapping("/csv2yaml")
    public String csvToYaml(@RequestBody String csvString) throws IOException, CsvValidationException {
        return csvService.csvToYaml(csvString);
    }
    @PostMapping(value = "/file/csv2json")
    public ResponseEntity<byte[]> fileCsvToJson(@RequestPart("file") MultipartFile file) throws IOException, CsvValidationException {
        return csvService.fileCsvToJson(file);
    }
    @PostMapping(value = "/file/csv2xml")
    public ResponseEntity<byte[]> fileCsvToXml(@RequestPart("file") MultipartFile file) throws IOException, CsvValidationException {
        return csvService.fileCsvToXml(file);
    }
    @PostMapping(value = "/file/csv2yaml")
    public ResponseEntity<byte[]> fileCsvToYaml(@RequestPart("file") MultipartFile file) throws IOException, CsvValidationException {
        return csvService.fileCsvToYaml(file);
    }
    @PostMapping(value = "/download")
    public ResponseEntity<byte[]> downloadFile(@RequestBody String  requestData) throws IOException, CsvValidationException {
        // 将请求中的数据转换为字节数组
        byte[] fileData = requestData.getBytes();

        // 指定下载文件的名称和类型
        String fileName = "file.json";
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
