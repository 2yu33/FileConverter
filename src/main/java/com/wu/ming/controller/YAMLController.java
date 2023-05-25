package com.wu.ming.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.wu.ming.service.YAMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author CH
 * @date 2023/5/11 18:56
 */
@RestController
public class YAMLController {
    @Autowired
    private YAMLService yamlService;
    @GetMapping("/yaml2json")
    public String ToJson(String yamlString) throws JsonProcessingException {
        return yamlService.toJSON(yamlString);
    }

    @GetMapping("/yaml2xml")
    public String ToXml(String yamlString) throws JsonProcessingException {
        return yamlService.toXML(yamlString);
    }

    @GetMapping("/yaml2csv")
    public ResponseEntity<String> convertYamlToCsv(String yamlString) throws IOException {
        return yamlService.toCSV(yamlString);
    }

    @PostMapping(value = "/file/yaml2json")
    public ResponseEntity<byte[]> yamlToJson(@RequestPart("file") MultipartFile file)  {
        return yamlService.fileYamlToJson(file);
    }

    @PostMapping(value = "/file/yaml2xml")
    public ResponseEntity<byte[]> yamlToXml(@RequestPart("file") MultipartFile file) {
        return yamlService.fileYamlToXml(file);
    }

    @PostMapping(value = "/file/yaml2csv")
    public ResponseEntity<byte[]> yamlToCsv(@RequestPart("file") MultipartFile file) {
        return yamlService.fileYamlToCsv(file);
    }
}