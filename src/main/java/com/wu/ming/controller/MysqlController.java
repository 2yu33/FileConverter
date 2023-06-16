package com.wu.ming.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ResultUtils;
import com.wu.ming.model.Converter;
import com.wu.ming.pojo.FileSearchDTO;
import com.wu.ming.service.ConverterService;
import com.wu.ming.utils.PageUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/mysql")
public class MysqlController {
    @Resource
    ConverterService converterService;
    @PostMapping()
     public BaseResponse<Long> mysqlAdd(@RequestPart("file") MultipartFile file){
        return  ResultUtils.success(converterService.addConverter(file));
    }
    @GetMapping("/all")
    public BaseResponse<List<FileSearchDTO>> listPage(int current, int pageSize) {
        PageUtils pageUtils = new PageUtils();
        pageUtils.setPageNum(current);
        pageUtils.setPageSize(pageSize);
        return ResultUtils.success(converterService.selectConverters(pageUtils));
    }
        @GetMapping("/get/one")
    public BaseResponse<Converter> getOne(@Param("id") Integer id){
        return ResultUtils.success(converterService.selectConverter(id));
    }
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteOne(@RequestBody FileSearchDTO fileSearchDTO ){
        return ResultUtils.success(converterService.removeConverter(fileSearchDTO.getId()));
    }
    @PostMapping("/download")
    public ResponseEntity<byte[]> mysqlDownload(@RequestBody FileSearchDTO fileSearchDTO) throws IOException {
        return converterService.downloadFile(fileSearchDTO.getId());
    }
}
