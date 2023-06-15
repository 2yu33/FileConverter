package com.wu.ming.controller;

import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ResultUtils;
import com.wu.ming.dao.FileEsDao;
import com.wu.ming.pojo.FileSearchDTO;
import com.wu.ming.service.impl.EsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
public class EsController {

    @Autowired
    private FileEsDao fileEsDao;

    @Autowired
    private EsServiceImpl esService;

    @PostMapping("/es")
    public BaseResponse<String> upload(@RequestPart("file") MultipartFile file){
        try {
            // 解析文件
            // 文件名称
            String originalFilename = file.getOriginalFilename();
            String[] split = originalFilename.split("\\.");
            // 文件名
            String fileName = split[split.length - 2];
            // 文件后缀
            String fileSuffix = split[split.length - 1];
            // 文件内容
            String content = new String(file.getBytes());
            // 文件大小
            long fileSize = file.getSize();
            FileSearchDTO fileSearchDTO = FileSearchDTO.builder()
                    .fileName(fileName)
                    .fileSuffix(fileSuffix)
                    .content(content)
                    .create_time(new Date())
                    .build();
            fileEsDao.save(fileSearchDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtils.success(null);
    }

    @GetMapping("/es")
    public BaseResponse<?> pageFileEs(int current, int pageSize, String keyword){
        return ResultUtils.success(esService.pageFileSearch(current, pageSize, keyword));
    }

}
