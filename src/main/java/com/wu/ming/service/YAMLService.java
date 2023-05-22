package com.wu.ming.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface YAMLService {
    String toJSON(String yamlString) throws JsonProcessingException;
    String toXML(String yamlString) throws JsonProcessingException;
    String toCSV(String yamlString) throws JsonProcessingException;
    ResponseEntity<byte[]> fileYamlToJson(MultipartFile file) throws IOException;
    ResponseEntity<byte[]> fileYamlToXml(MultipartFile file) throws IOException;
    ResponseEntity<byte[]> fileYamlToCsv(MultipartFile file) throws FileNotFoundException, IOException;
}
