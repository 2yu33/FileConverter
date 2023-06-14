package com.wu.ming.controller;



import com.opencsv.exceptions.CsvValidationException;
import com.wu.ming.common.BaseResponse;
import com.wu.ming.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public BaseResponse<String> csvToJson(@RequestBody String csvString) throws IOException, CsvValidationException {
        return csvService.csvToJson(csvString);
    }
    @PostMapping("/csv2xml")
    public BaseResponse<String> csvToXml(@RequestBody String csvString) throws IOException, CsvValidationException {
        return csvService.csvToXml(csvString);
    }
    @PostMapping("/csv2yaml")
    public BaseResponse<String> csvToYaml(@RequestBody String csvString) throws IOException, CsvValidationException {
        return csvService.csvToYaml(csvString);
    }
    @PostMapping(value = "/file/csv2json")
    public ResponseEntity fileCsvToJson(@RequestPart("file") MultipartFile file) throws IOException, CsvValidationException {
        return csvService.fileCsvToJson(file);
    }
    @PostMapping(value = "/file/csv2xml")
    public ResponseEntity fileCsvToXml(@RequestPart("file") MultipartFile file) throws IOException, CsvValidationException {
        return csvService.fileCsvToXml(file);
    }
    @PostMapping(value = "/file/csv2yaml")
    public ResponseEntity fileCsvToYaml(@RequestPart("file") MultipartFile file) throws IOException, CsvValidationException {
        return csvService.fileCsvToYaml(file);
    }

}
