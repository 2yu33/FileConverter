package com.wu.ming.controller;



import com.opencsv.exceptions.CsvValidationException;
import com.wu.ming.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class CsvController {
    @Autowired
    private CsvService csvService;
    @PostMapping("/csv2json")
    public String csvToJson(@RequestBody String csvString) throws IOException, CsvValidationException {
        return csvService.csvToJson(csvString);
    }
    @PostMapping("/csv2xml")
    public String csvToXml(@RequestBody String csvString) throws IOException, CsvValidationException {
        return csvService.csvToXml(csvString);
    }
    @PostMapping("/csv2yaml")
    public String csvToYaml(@RequestBody String csvString) throws IOException, CsvValidationException {
        return csvService.csvToYaml(csvString);
    }
    @PostMapping(value = "/file/csv2json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String csvToJson(@RequestPart("file") MultipartFile file) throws IOException, CsvValidationException {
        return csvService.fileCsvToJson(file);
    }
    @PostMapping(value = "/file/csv2xml", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String csvToXml(@RequestPart("file") MultipartFile file) throws IOException, CsvValidationException {
        return csvService.fileCsvToXml(file);
    }
    @PostMapping(value = "/file/csv2yaml", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String csvToYaml(@RequestPart("file") MultipartFile file) throws IOException, CsvValidationException {
        return csvService.fileCsvToYaml(file);
    }

}

