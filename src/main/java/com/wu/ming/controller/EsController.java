package com.wu.ming.controller;

import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ResultUtils;
import com.wu.ming.dao.FileEsDao;
import com.wu.ming.pojo.FileSearchDTO;
import com.wu.ming.service.impl.EsServiceImpl;
import com.wu.ming.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@RestController
public class EsController {

    @Autowired
    private FileEsDao fileEsDao;

    @Autowired
    private EsServiceImpl esService;

    /**
     * es文件保存
     * @param file
     * @return
     */
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
                    .id(Integer.parseInt(UUIDUtils.getUuidNum()))
                    .fileName(fileName)
                    .fileSuffix(fileSuffix)
                    .content(content)
                    .create_time(new Date())
                    .build();
            fileEsDao.save(fileSearchDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtils.success("上传文件成功");
    }

    /**
     * es分页查询
     */
    @GetMapping("/es/all")
    public BaseResponse<List<FileSearchDTO>> pageFileEs(int current, int pageSize){
        return ResultUtils.success(esService.pageFileSearch(current, pageSize));
    }

    /**
     * es删除数据
     */
    @PostMapping("/es/delete")
    public BaseResponse<?> delEsFile(FileSearchDTO fileSearchDTO){
        fileEsDao.deleteById(fileSearchDTO.getId());
        return ResultUtils.success("删除成功");
    }

    /**
     * 通过id下载文件
     */
    @PostMapping("/es/download")
    public ResponseEntity<byte[]> downloadFile(FileSearchDTO fileSearchDTO){
        FileSearchDTO downFileSearchDTO = fileEsDao.findById(fileSearchDTO.getId()).get();
        String content = fileSearchDTO.getContent();
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/"+fileSearchDTO.getFileSuffix()));
        headers.setContentDispositionFormData("attachment", fileSearchDTO.getFileName()+"."+fileSearchDTO.getFileSuffix());

        // Convert YAML content to byte array
        byte[] yamlBytes = content.getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/xml"))
                .body(content.getBytes(StandardCharsets.UTF_8));
    }
}
