package com.wu.ming.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opencsv.exceptions.CsvValidationException;
import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.common.ResultUtils;
import com.wu.ming.exception.BusinessException;
import com.wu.ming.service.JsonService;
import com.wu.ming.utils.jsonValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class JsonController {
    @Resource
    private JsonService jsonService;
    /**
     * json 转换成其他格式
     * return 将转换后的xml格式返回
     */
    @PostMapping("/json2xml")
    public BaseResponse<String> json2Xml(@RequestBody String jsonStr) throws JsonProcessingException {
        if (jsonStr == null)
            throw new BusinessException(ErrorCode.NULL_ERROR);
//        json格式校验，不符合则抛出异常
        jsonValidation.isJson(jsonStr);
        return ResultUtils.success(jsonService.json2Xml(jsonStr));
    }
    /**
     * json 转换成其他格式
     * return 将转换后的yaml格式返回
     */
    @PostMapping("/json2yaml")
    public BaseResponse<String> json2Yaml(@RequestBody String jsonStr) throws JsonProcessingException {
        if (jsonStr == null)
            throw new BusinessException(ErrorCode.NULL_ERROR);
//        json格式校验，不符合则抛出异常
        jsonValidation.isJson(jsonStr);
        return ResultUtils.success(jsonService.json2Yaml(jsonStr));
    }
    /**
     * json 转换成其他格式
     * return 将转换的csv格式返回
     */
    @PostMapping("/json2csv")
    public BaseResponse<String>json2Csv(@RequestBody String jsonStr) throws JsonProcessingException {
        if (jsonStr == null)
            throw new BusinessException(ErrorCode.NULL_ERROR);
        return ResultUtils.success(jsonService.json2Csv(jsonStr));
    }
    @PostMapping("/file/json2xml")
    public ResponseEntity<byte[]> fileJson2Xml(@RequestPart("file") MultipartFile file) throws IOException,
            CsvValidationException{
        return jsonService.fileJson2Xml(file);
    }
    @PostMapping("/file/json2yaml")
    public ResponseEntity<byte[]> fileJson2Yaml(@RequestPart("file") MultipartFile file) throws IOException,
            CsvValidationException{
        return jsonService.fileJson2Yaml(file);
    }
    @PostMapping("/file/json2csv")
    public ResponseEntity<byte[]> fileJson2Csv(@RequestPart("file") MultipartFile file) throws IOException,
            CsvValidationException{
        return jsonService.fileJson2Csv(file);
    }
    @PostMapping("/file/json2xml/Compress")
    public ResponseEntity<byte[]> fileJson2YamlCompress(@RequestPart("file") MultipartFile file) throws IOException,
            CsvValidationException{
        return jsonService.fileJson2XmlCompress(file);
    }
}
