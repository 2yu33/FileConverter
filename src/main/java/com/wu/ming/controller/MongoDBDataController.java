package com.wu.ming.controller;

import com.opencsv.exceptions.CsvValidationException;
import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.common.ResultUtils;
import com.wu.ming.exception.BusinessException;
import com.wu.ming.pojo.FileSearchDTO;
import com.wu.ming.service.impl.MongoDBDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/mongodb")
public class MongoDBDataController {

    private final MongoDBDataService mongoDBDataService;

    @Autowired
    public MongoDBDataController(MongoDBDataService mongoDBDataService) {
        this.mongoDBDataService = mongoDBDataService;
    }

    @PostMapping("/upload")
    public BaseResponse<String> saveJsonData(@RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String fileName = file.getOriginalFilename();
        String data = new String(file.getBytes(), StandardCharsets.UTF_8);
        mongoDBDataService.saveJsonData(fileName,data);
        return ResultUtils.success("保存成功！");
    }

    @GetMapping("/all")
    public BaseResponse<List<FileSearchDTO>> getJsonDataAll() {
        return ResultUtils.success(mongoDBDataService.getAllData());
    }

    @DeleteMapping("/delete/{fileName}")
    public BaseResponse<String> deleteFile(@PathVariable String fileName) {
        mongoDBDataService.deleteByFileName(fileName);
        return ResultUtils.success("删除成功！");
    }
    @PostMapping("/download/{id}")
    public ResponseEntity downloadFile(@PathVariable("id") String fileName) throws CsvValidationException, IOException {
        return mongoDBDataService.downloadFile(fileName);
    }

    // ...
}
