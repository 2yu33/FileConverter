package com.example.ch.controller;


import com.alibaba.fastjson2.JSON;
import com.example.ch.service.YAMLService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
