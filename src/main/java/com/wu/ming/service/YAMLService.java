package com.example.ch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface YAMLService {
    String toJSON(String yamlString) throws JsonProcessingException;
    String toXML(String yamlString) throws JsonProcessingException;
    ResponseEntity<String> toCSV(String yamlString) throws JsonProcessingException;
}
