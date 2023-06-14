package com.wu.ming.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wu.ming.common.BaseResponse;
import com.wu.ming.common.ResultUtils;
import com.wu.ming.model.Converter;
import com.wu.ming.service.ConverterService;
import com.wu.ming.utils.PageUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mysql")
public class MysqlController {
    @Resource
    ConverterService converterService;
    @PostMapping("/file/add")
     public BaseResponse<Long> mysqlAdd(@RequestPart("file") MultipartFile file){
        return  ResultUtils.success(converterService.addConverter(file));
    }
    @PostMapping("/list/page")
    public BaseResponse<Page<Converter>> listPage(@RequestBody PageUtils pageUtils){
        return ResultUtils.success(converterService.selectConverters(pageUtils));
    }
    @GetMapping("/get/one")
    public BaseResponse<Converter> getOne(@Param("id") Integer id){
        return ResultUtils.success(converterService.selectConverter(id));
    }
    @PostMapping("/delete/id")
    public BaseResponse<Boolean> deleteOne(Integer id){
        return ResultUtils.success(converterService.removeConverter(id));
    }
}
