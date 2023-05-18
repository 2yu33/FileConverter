package com.wu.ming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wu.ming.service.JsonService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MingApplicationTests {
    @Resource
    private JsonService jsonService;
    @Test
    void contextLoads() throws JsonProcessingException {
//        String jsonString = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";
//        System.out.println(jsonString.charAt(0));
//        System.out.println(jsonService.json2Xml(jsonString));
//        System.out.println(jsonService.json2Yaml(jsonString));
//        System.out.println(jsonService.json2Csv(jsonString));
    }

}
