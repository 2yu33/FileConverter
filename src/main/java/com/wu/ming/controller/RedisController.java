package com.wu.ming.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.exception.BusinessException;
import com.wu.ming.service.RedisService;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author CH
 * redis服务
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;
@PostMapping("/{id}")
    public BaseResponse updateRedisFile(@PathVariable String id,@RequestPart("file") MultipartFile file) throws IOException {
        String base64File = Base64.encode(file.getBytes());
        if(StringUtils.isNotBlank(base64File)) redisService.set(id, base64File);
        return new BaseResponse(200,null,"存储成功");
    }
@GetMapping("/{id}")
    public ResponseEntity<byte[]> getRedisFile(@PathVariable String id) throws IOException {
    String base64File = (String) redisService.get(id);
    if (!StringUtils.isNotBlank(base64File)) {
        throw new BusinessException(ErrorCode.NULL_ERROR,"文件不存在");
    }
    byte[] file = Base64.decode(base64File);
    String fileName = "file";
    String contentType = MediaType.TEXT_PLAIN_VALUE;

    // 创建临时文件
    File tempFile = File.createTempFile("temp", null);
    try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
        outputStream.write(file);
    }
    // 设置下载响应头
    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
            .body(org.apache.commons.io.FileUtils.readFileToByteArray(tempFile));
}
}
