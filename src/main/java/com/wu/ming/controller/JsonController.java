package com.wu.ming.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wu.ming.service.JsonService;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

public class JsonController {
    @Resource
    private JsonService jsonService;
    /**
     * json 转换成其他格式
     * return 将转换的格式返回
     */
    @PostMapping("/json2xml")
    public String json2Xml(String jsonStr){
        return jsonService.json2Xml(jsonStr);
    }

    @PostMapping("/json2yaml")
    public String json2Yaml(String jsonStr) throws JsonProcessingException {
        return jsonService.json2Yaml(jsonStr);
    }
    @PostMapping("/json2csv")
    public String json2Csv(String jsonStr) throws JsonProcessingException {
        return jsonService.json2Csv(jsonStr);
    }

}
