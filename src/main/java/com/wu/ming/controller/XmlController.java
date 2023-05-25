package com.wu.ming.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wu.ming.service.JsonService;
import com.wu.ming.service.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class XmlController {

    @Autowired
    private XmlService xmlService;

    /**
     * xml转yaml
     *
     * @param xmlStr 要转换的xml
     * @return 转换后的结果
     */
    @PostMapping("/xml2yaml")
    public String xml2yaml(String xmlStr) {
        return xmlService.xml2yaml(xmlStr);
    }

    @PostMapping("/xml2csv")
    public String xml2csv(String xmlStr) throws JsonProcessingException {
        return xmlService.xml2csv(xmlStr);
    }

    @PostMapping("/xml2json")
    public String xml2json(String jsonStr) {
        return "";
    }

}
