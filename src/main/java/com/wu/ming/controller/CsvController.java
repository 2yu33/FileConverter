package com.wu.ming.controller;



import com.opencsv.exceptions.CsvValidationException;
import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.common.ResultUtils;
import com.wu.ming.service.CsvService;
import com.wu.ming.utils.CsvValidator;
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
    @Autowired
    private CsvValidator csvValidator;
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
    public ResponseEntity<byte[]> fileCsvToJson(@RequestPart("file") MultipartFile file) throws IOException, CsvValidationException {
        return csvService.fileCsvToJson(file);
    }
    @PostMapping(value = "/file/csv2xml")
    public ResponseEntity<byte[]> fileCsvToXml(@RequestPart("file") MultipartFile file) throws IOException, CsvValidationException {
        return csvService.fileCsvToXml(file);
    }
    @PostMapping(value = "/file/csv2yaml")
    public ResponseEntity<byte[]> fileCsvToYaml(@RequestPart("file") MultipartFile file) throws IOException, CsvValidationException {
        return csvService.fileCsvToYaml(file);
    }
    @PostMapping("/validate/csv")
    public BaseResponse validateCsv(@RequestBody String csvString) {
        boolean isValid = csvValidator.validateCsv(csvString);
        if (isValid) {
            return ResultUtils.error(ErrorCode.SUCCESS,"格式正确","CSV格式正确!");
        } else {
            return ResultUtils.error(201,"格式错误","csv格式错误！");
        }
    }
    @PostMapping("/file/validate/csv")
    public BaseResponse validateCsvFile(@RequestPart MultipartFile file){
        boolean isValid = csvValidator.fileValidateCsv(file);
        if (isValid) {
            return ResultUtils.error(ErrorCode.SUCCESS,"格式正确","CSV格式正确!");
        } else {
            return ResultUtils.error(201,"格式错误","csv格式错误！");
        }
    }
}
