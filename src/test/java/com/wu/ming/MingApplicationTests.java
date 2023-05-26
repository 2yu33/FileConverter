package com.wu.ming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wu.ming.service.JsonService;
import com.wu.ming.service.XmlService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MingApplicationTests {

    @Autowired
    private XmlService xmlService;


    @Resource
    private JsonService jsonService;
    @Test
    void contextLoads() throws JsonProcessingException, JSONException {
        String jsonStr ="{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"email\": \"johndoe@example.com\",\n" +
                "  \"phone\": \"+1 (555) 123-4567\",\n" +
                "  \"address\": {\n" +
                "    \"street\": \"123 Main St\",\n" +
                "    \"city\": \"Anytown\",\n" +
                "    \"state\": \"CA\",\n" +
                "    \"zip\": \"12345\",\n" +
                "    \"country\": \"USA\"\n" +
                "  },\n" +
                "  \"orders\": [\n" +
                "    {\n" +
                "      \"id\": \"001\",\n" +
                "      \"date\": \"2022-01-01\",\n" +
                "      \"items\": [\n" +
                "        {\n" +
                "          \"name\": \"Widget\",\n" +
                "          \"quantity\": 3,\n" +
                "          \"price\": 9.99\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Gizmo\",\n" +
                "          \"quantity\": 2,\n" +
                "          \"price\": 19.99\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"002\",\n" +
                "      \"date\": \"2022-02-01\",\n" +
                "      \"items\": [\n" +
                "        {\n" +
                "          \"name\": \"Thingamajig\",\n" +
                "          \"quantity\": 1,\n" +
                "          \"price\": 29.99\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        System.out.println(jsonService.json2Xml(jsonStr));
        System.out.println(111);
        System.out.println(jsonService.json2Yaml(jsonStr));
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
