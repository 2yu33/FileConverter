package com.wu.ming.service;

import com.opencsv.exceptions.CsvValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface CsvService {

    String csvToJson(String csvString) throws IOException, CsvValidationException;
    String csvToXml(String csvString) throws IOException, CsvValidationException;
    String csvToYaml(String csvString) throws IOException, CsvValidationException;
    ResponseEntity<byte[]> fileCsvToJson(MultipartFile file) throws IOException, CsvValidationException;
    ResponseEntity<byte[]> fileCsvToXml(MultipartFile file) throws IOException, CsvValidationException;
    ResponseEntity<byte[]> fileCsvToYaml(MultipartFile file) throws IOException, CsvValidationException;
}