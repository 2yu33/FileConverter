package com.wu.ming.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.wu.ming.service.CsvService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvServiceImpl implements CsvService {

    @Override
    public String csvToJson(String csvString) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new StringReader(csvString));
        String[] headers = reader.readNext();
        String[] nextLine;
        List<ObjectNode> jsonList = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            ObjectNode json = new ObjectMapper().createObjectNode();
            for (int i = 0; i < headers.length; i++) {
                json.put(headers[i], nextLine[i]);
            }
            jsonList.add(json);
        }
        return new ObjectMapper().writeValueAsString(jsonList);
    }

    @Override
    public String csvToXml(String csvString) throws IOException, CsvValidationException{
        CSVReader reader = new CSVReader(new StringReader(csvString));
        String[] headers = reader.readNext();
        String[] nextLine;
        List<Map<String, String>> dataList = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Map<String, String> data = new LinkedHashMap<>();
            for (int i = 0; i < headers.length; i++) {
                data.put(headers[i], nextLine[i]);
            }
            dataList.add(data);
        }
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataList);
    }

    @Override
    public String csvToYaml(String csvString)throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new StringReader(csvString));
        String[] headers = reader.readNext();
        String[] nextLine;
        List<Map<String, String>> personList = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Map<String, String> person = new LinkedHashMap<>();
            for (int i = 0; i < headers.length; i++) {
                String value = nextLine[i];
                person.put(headers[i], value);
            }
            personList.add(person);
        }
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        StringWriter writer = new StringWriter();
        yaml.dump(personList, writer);
        return writer.toString();
    }

    @Override
    public String fileCsvToJson(MultipartFile file) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
        String[] headers = reader.readNext();
        String[] nextLine;
        List<Map<String, String>> personList = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Map<String, String> person = new LinkedHashMap<>();
            for (int i = 0; i < headers.length; i++) {
                String value = nextLine[i];
                person.put(headers[i], value);
            }
            personList.add(person);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(personList);
    }

    @Override
    public String fileCsvToXml(MultipartFile file) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
        String[] headers = reader.readNext();
        String[] nextLine;
        List<Map<String, String>> dataList = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Map<String, String> data = new LinkedHashMap<>();
            for (int i = 0; i < headers.length; i++) {
                data.put(headers[i], nextLine[i]);
            }
            dataList.add(data);
        }
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataList);
    }

    @Override
    public String fileCsvToYaml(MultipartFile file) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
        String[] headers = reader.readNext();
        String[] nextLine;
        List<Map<String, String>> personList = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Map<String, String> person = new LinkedHashMap<>();
            for (int i = 0; i < headers.length; i++) {
                String value = nextLine[i];
                person.put(headers[i], value);
            }
            personList.add(person);
        }
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        StringWriter writer = new StringWriter();
        yaml.dump(personList, writer);
        return writer.toString();
    }

}
