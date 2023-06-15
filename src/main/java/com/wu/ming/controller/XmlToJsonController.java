package com.wu.ming.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.List;
import java.util.Map;

@RestController
public class XmlToJsonController {

    @PostMapping(value = "/convert")
    public String convertXmlToJson(@RequestBody String xmlString) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            List<Map<String, String>> javaObject = xmlMapper.readValue(xmlString, new TypeReference<List<Map<String, String>>>() {});

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(javaObject);

            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
            return "转换失败";
        }
    }
}
