package com.wu.ming.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface YAMLService {
    String toJSON(String yamlString) throws JsonProcessingException;
    String toXML(String yamlString) throws JsonProcessingException;
    ResponseEntity<String> toCSV(String yamlString) throws JsonProcessingException;
    ResponseEntity<byte[]> fileYamlToJson(MultipartFile file);
    ResponseEntity<byte[]> fileYamlToXml(MultipartFile file);
    ResponseEntity<byte[]> fileYamlToCsv(MultipartFile file);
}