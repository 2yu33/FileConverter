package com.wu.ming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wu.ming.service.XmlService;
import com.wu.ming.service.YAMLService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MingApplicationTests {

    @Autowired
    private XmlService xmlService;

    @Autowired
    private YAMLService yamlService;

    @Test
    void contextLoads() {
    }

    @Test
    void xml2json(){
        String xmlString = "<root>" +
                "<person>" +
                "<name>John</name>" +
                "<age>30</age>" +
                "</person>" +
                "<person>" +
                "<name>Jane</name>" +
                "<age>25</age>" +
                "</person>" +
                "</root>";
        System.out.println(xmlService.xml2json(xmlString));
    }

    @Test
    void xml2csv() throws Exception {
        String xmlStr =  "<root>" +
                "<person>" +
                "<name>John Smith</name>" +
                "<age>30</age>" +
                "<address>江西南昌</address>" +
                "</person>" +
                "</root>";

        System.out.println(xmlService.xml2csv(xmlStr));
    }

    @Test
    void yamlValidate(){
        String yamlStr = "address:\n" +
                "  street: 123 Main Street\n" +
                "  city: Anytown\n" +
                "  state: AnyState\n" +
                "  country: AnyCountry";

        System.out.println(yamlService.yamlValidate(yamlStr));
    }

    @Test
    void xmlValidate(){
        String yamlStr = "<root>" +
                "<person>" +
                "<name>John Smith</name>" +
                "<age>30</age>" +
                "<address>江西南昌</address>" +
                "</person>" +
                "</root>";

        System.out.println(xmlService.xmlValidate(yamlStr));
    }
}
