package com.wu.ming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.wu.ming.service.JsonService;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MingApplicationTests {
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

}
