package com.wu.ming.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wu.ming.service.JsonService;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.json.CDL;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;
@Service
public class JsonServiceImpl implements JsonService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String json2Xml(String jsonString) {
//        将json字符串通过库解析称为json
        JSON json = JSONSerializer.toJSON(jsonString);
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setTypeHintsEnabled(false);
//        通过库将json转为xml字符串
        String xmlString = xmlSerializer.write(json);
        return xmlString;
    }

    @Override
    public String json2Yaml(String jsonString) throws JsonProcessingException {
        String yamlString;
//        如果是json数组则调用该方法，反之调用下面的方法
        if (jsonString.charAt(0) == '[') {
            // 使用Jackson库将JSON字符串解析为List<Map<String, Object>>对象
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> jsonList = objectMapper.readValue(jsonString, List.class);

            // 使用SnakeYAML库将List<Map<String, Object>>对象转换为YAML格式字符串
            Yaml yaml = new Yaml();
            String yamlStr = yaml.dump(jsonList);
            // 使用Jackson库将List<Map<String, Object>>对象转换为YAML格式字符串
            YAMLFactory yamlFactory = new YAMLFactory();
            ObjectMapper yamlMapper = new ObjectMapper(yamlFactory);
            yamlString = yamlMapper.writeValueAsString(jsonList);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            Yaml yaml = new Yaml();
            Map<String, Object> map = (Map<String, Object>) jsonObject;
            yamlString = yaml.dump(map);
        }
        return yamlString;
    }

    @Override
    public String json2Csv(String jsonStr) throws JsonProcessingException {
        String csv;

        if(jsonStr.charAt(0)=='['){
            //        调库将jsonArray转为csv字符串
            JSONArray jsonArray = new JSONArray(jsonStr);
            csv = CDL.toString(jsonArray);

        }
        else {
            JsonNode jsonNode = objectMapper.readTree(jsonStr);

            // Get all field names as CSV header
            StringBuilder header = new StringBuilder();
            jsonNode.fieldNames().forEachRemaining(fieldName -> header.append(fieldName).append(","));
            header.deleteCharAt(header.length() - 1);

            // Get all field values as CSV row
            StringBuilder row = new StringBuilder();
            jsonNode.elements().forEachRemaining(jsonValue -> row.append(jsonValue.asText()).append(","));
            row.deleteCharAt(row.length() - 1);
            csv =header.toString()+"\n"+row.toString();
        }
        return csv;
    }
}
