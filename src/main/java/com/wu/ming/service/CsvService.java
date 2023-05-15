package com.wu.ming.service;

import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface CsvService {
    String csvToJson(String csvString) throws IOException, CsvValidationException;
    String csvToXml(String csvString) throws IOException, CsvValidationException;
    String csvToYaml(String csvString) throws IOException, CsvValidationException;
    String fileCsvToJson(MultipartFile file) throws IOException, CsvValidationException;
    String fileCsvToXml(MultipartFile file) throws IOException, CsvValidationException;
    String fileCsvToYaml(MultipartFile file) throws IOException, CsvValidationException;
}
