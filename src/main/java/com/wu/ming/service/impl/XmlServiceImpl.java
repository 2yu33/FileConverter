package com.wu.ming.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wu.ming.service.XmlService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;

@Service
public class XmlServiceImpl implements XmlService {

    @Override
    public String xml2yaml(String xmlStr) {
        String reYaml = "";
        ObjectMapper xmlMapper = new XmlMapper();
        Object xmlObject = null;
        try {
            xmlObject = xmlMapper.readValue(xmlStr, Object.class);
            YAMLMapper yamlMapper = new YAMLMapper();
            reYaml = yamlMapper.writeValueAsString(xmlObject);
        } catch (JsonProcessingException e) {
            reYaml = "转换失败,格式错误";
            e.printStackTrace();
        }
        System.out.println(reYaml);
        return reYaml;
    }

    @Override
    public String xml2csv(String xmlStr) throws JsonProcessingException {
        String csvStr = "";

        // Parse XML input into JsonNode
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode rootNode = xmlMapper.readTree(xmlStr);

        // Convert JsonNode to CSV string
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();

        if (rootNode.isArray()) {
            // If the root node is an array, use the first element as the schema
            ArrayNode arrayNode = (ArrayNode) rootNode;
            ObjectNode firstObjectNode = (ObjectNode) arrayNode.get(0);

            // Add the field names to the CSV schema
            Iterator<String> fieldNames = firstObjectNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                csvSchemaBuilder.addColumn(fieldName);
            }

            // Write each object in the array to a row in the CSV string
            String csvString = csvMapper.writer(csvSchemaBuilder.build()).writeValueAsString(arrayNode);
            System.out.println(csvString);
        } else if (rootNode.isObject()) {
            // If the root node is an object, use its fields as the schema
            ObjectNode objectNode = (ObjectNode) rootNode;

            // Add the field names to the CSV schema
            Iterator<String> fieldNames = objectNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                csvSchemaBuilder.addColumn(fieldName);
            }

            // Write the object to a row in the CSV string
            String csvString = csvMapper.writer(csvSchemaBuilder.build()).writeValueAsString(objectNode);
            System.out.println(csvString);
        }

        return csvStr;
    }


    public static void main(String[] args) throws IOException {
        // Read XML input from user
        String xmlInput = "<root><person><name>John</name><age>30</age><email>john@example.com</email></person><person><name>Jane</name><age>25</age><email>jane@example.com</email></person></root>";

        // Parse XML input into JsonNode
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode rootNode = xmlMapper.readTree(xmlInput);

        // Convert JsonNode to CSV string
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();

        if (rootNode.isArray()) {
            // If the root node is an array, use the first element as the schema
            ArrayNode arrayNode = (ArrayNode) rootNode;
            ObjectNode firstObjectNode = (ObjectNode) arrayNode.get(0);

            // Add the field names to the CSV schema
            Iterator<String> fieldNames = firstObjectNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                csvSchemaBuilder.addColumn(fieldName);
            }

            // Write each object in the array to a row in the CSV string
            StringBuilder csvStringBuilder = new StringBuilder();
            for (JsonNode node : arrayNode) {
                ObjectNode objectNode = (ObjectNode) node;
                Iterator<JsonNode> fields = objectNode.elements();
                while (fields.hasNext()) {
                    JsonNode field = fields.next();
                    if (field.isObject()) {
                        csvStringBuilder.append(field.toString());
                    } else {
                        csvStringBuilder.append(field.asText());
                    }
                    csvStringBuilder.append(",");
                }
                csvStringBuilder.deleteCharAt(csvStringBuilder.length() - 1);
                csvStringBuilder.append("\n");
            }
            System.out.println(csvStringBuilder.toString());
        } else if (rootNode.isObject()) {
            // If the root node is an object, use its fields as the schema
            ObjectNode objectNode = (ObjectNode) rootNode;

            // Add the field names to the CSV schema
            Iterator<String> fieldNames = objectNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                csvSchemaBuilder.addColumn(fieldName);
            }

            // Write the object to a row in the CSV string
            StringBuilder csvStringBuilder = new StringBuilder();
            Iterator<JsonNode> fields = objectNode.elements();
            while (fields.hasNext()) {
                JsonNode field = fields.next();
                if (field.isObject()) {
                    csvStringBuilder.append(field.toString());
                } else {
                    csvStringBuilder.append(field.asText());
                }
                csvStringBuilder.append(",");
            }
            csvStringBuilder.deleteCharAt(csvStringBuilder.length() - 1);
            csvStringBuilder.append("\n");
            System.out.println(csvStringBuilder.toString());
        }
    }


}
