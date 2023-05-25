package com.wu.ming.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opencsv.exceptions.CsvValidationException;
import com.wu.ming.service.JsonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class JsonController {
    @Resource
    private JsonService jsonService;
    /**
     * json 转换成其他格式
     * return 将转换的格式返回
     */
    @PostMapping("/json2xml")
    public String json2Xml(@RequestBody String jsonStr){
        return jsonService.json2Xml(jsonStr);
    }

    @PostMapping("/json2yaml")
    public String json2Yaml(@RequestBody String jsonStr) throws JsonProcessingException {
        return jsonService.json2Yaml(jsonStr);
    }
    @PostMapping("/json/2csv")
    public String json2Csv(@RequestBody String jsonStr) throws JsonProcessingException {
        return jsonService.json2Csv(jsonStr);
    }
    @PostMapping("/file/Json2xml")
    public ResponseEntity<byte[]> fileJson2Xml(@RequestPart("file") MultipartFile file) throws IOException,
            CsvValidationException{
        return jsonService.fileJson2Xml(file);
    }
    @PostMapping("/file/Json2yaml")
    public ResponseEntity<byte[]> fileJson2Yaml(@RequestPart("file") MultipartFile file) throws IOException,
            CsvValidationException{
        return jsonService.fileJson2Yaml(file);
    }
    @PostMapping("/file/Json2csv")
    public ResponseEntity<byte[]> fileJson2Csv(@RequestPart("file") MultipartFile file) throws IOException,
            CsvValidationException{
        return jsonService.fileJson2Csv(file);
    }
}
