package com.wu.ming.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.exception.BusinessException;
import com.wu.ming.pojo.FileSearchDTO;
import com.wu.ming.service.RedisService;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.wu.ming.common.ErrorCode.SYSTEM_ERROR;

/**
 * @author CH
 * redis服务
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @PostMapping("/{name}")
    public BaseResponse updateRedisFile(@PathVariable String name,@RequestPart("file") MultipartFile file) throws IOException {
        String base64File = Base64.encode(file.getBytes());
        if(StringUtils.isNotBlank(base64File))
        {
            redisService.set(name, base64File);
            return new BaseResponse(200,null,"存储成功");
        }
        else{
            return new BaseResponse(SYSTEM_ERROR.getCode(),null,"文件为空");
        }
    }
    @GetMapping("/all")
    public BaseResponse<List<FileSearchDTO>> getRedisAllFile(){
        List<FileSearchDTO> list=new ArrayList<>();
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            FileSearchDTO fileSearchDTO = new FileSearchDTO();
            fileSearchDTO.setFileName(key);
            list.add(fileSearchDTO);
        }
        return new BaseResponse(200,list,"查询成功");
    }
    @PostMapping("/download")
    public ResponseEntity<byte[]> downRedisFile(@RequestBody String name) throws IOException {
        String base64File = (String) redisService.get(name);
        if (!StringUtils.isNotBlank(base64File)) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"文件不存在");
        }
        byte[] file = Base64.decode(base64File);
        String fileName ="file";
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
