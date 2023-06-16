package com.wu.ming.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.common.ResultUtils;
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
public class RedisController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 文件保存
     * @param: file 文件
     * @return: String
     */
    @PostMapping("/redis")
    public BaseResponse updateRedisFile(@RequestPart("file") MultipartFile file) throws IOException {
       //解析文件
        String originalFilename = file.getOriginalFilename();

        String[] split = originalFilename.split("\\.");
        // 文件名
        String fileName = split[split.length - 2];
        //后缀名
        String fileSuffix = split[split.length - 1];
        String base64File = Base64.encode(file.getBytes());
        if(StringUtils.isNotBlank(base64File))
        {
            redisService.set(fileName+'.'+fileSuffix, base64File);
            return new BaseResponse(200,null,"存储成功");
        }
        else{
            return new BaseResponse(SYSTEM_ERROR.getCode(),null,"文件为空");
        }
    }
    /**
     * 获取所有文件
     */
    @GetMapping("/redis/all")
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
    /**
     * 文件下载
     * @param: fileSearchDTO 文件类型
     * @return: 二进制文件
     */
    @PostMapping("/redis/download")
    public ResponseEntity<byte[]> downRedisFile(@RequestBody FileSearchDTO fileSearchDTO) throws IOException {
        String base64File = (String) redisService.get(fileSearchDTO.getFileName());
        if (!StringUtils.isNotBlank(base64File)) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"文件不存在");
        }
        byte[] file = Base64.decode(base64File);
        String fileName =fileSearchDTO.getFileName();
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

    @PostMapping("/redis/delete")
    public BaseResponse<?> deleteRedisFile(@RequestBody FileSearchDTO fileSearchDTO ){
        Boolean del = redisService.del(fileSearchDTO.getFileName());
        if(del) {return ResultUtils.success("删除成功");}else {
            return ResultUtils.error(SYSTEM_ERROR,"删除失败");
        }
    }
}
