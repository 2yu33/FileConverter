package com.wu.ming.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.exception.BusinessException;
import com.wu.ming.mapper.ConverterMapper;
import com.wu.ming.model.Converter;
import com.wu.ming.pojo.FileSearchDTO;
import com.wu.ming.service.ConverterService;
import com.wu.ming.utils.PageUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* @author 余某某
* @description 针对表【converter】的数据库操作Service实现
* @createDate 2023-06-08 14:16:05
*/
@Service
public class ConverterServiceImpl extends ServiceImpl<ConverterMapper, Converter>
    implements ConverterService{
@Resource
ConverterMapper converterMapper;
    @Override
    public long addConverter(MultipartFile file) {
        Converter converter = new Converter();
        if(file == null)
            throw new BusinessException(ErrorCode.NULL_ERROR);
        try {
            converter.setContent(new String(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        converter.setSize(file.getSize());
        String[] split = file.getOriginalFilename().split("\\.");
        converter.setType(split[split.length-1]);
        if(converter == null)
            throw new BusinessException(ErrorCode.NULL_ERROR);

        int insert = converterMapper.insert(converter);
        if(insert == 0)
            throw new BusinessException("插入失败",50000,"");
        return insert;
    }

    @Override
    public boolean removeConverter(int id) {
        if(id<=0)
            throw new BusinessException("请求数据错误",50000,"");
        Converter converter = this.getById(id);
        if (converter == null)
            throw new BusinessException(ErrorCode.NULL_ERROR);
        return   this.removeById(id);

    }

    @Override
    public Converter selectConverter(int id) {
        if(id<=0)
            throw new BusinessException("请求数据错误",50000,"");
        Converter converter = this.getById(id);
        if(converter == null)
            throw new BusinessException(ErrorCode.NULL_ERROR);

        return converter;
    }

    @Override
    public List<FileSearchDTO> selectConverters(PageUtils pageUtils) {
    Page<Converter> pageList = new Page<>(pageUtils.getPageNum(),pageUtils.getPageSize());
    QueryWrapper<Converter> queryWrapper = new QueryWrapper<>();
    Page<Converter> resultPage = this.page(pageList,queryWrapper);
        List<Converter> records = resultPage.getRecords();
        List<FileSearchDTO> fileSearchDTOS = new ArrayList<>();
        for (Converter record : records) {
            FileSearchDTO fileSearchDTO = new FileSearchDTO();
            fileSearchDTO.setId(record.getId());
            fileSearchDTOS.add(fileSearchDTO);
        }
        return fileSearchDTOS;
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(Integer id) throws IOException {
        Converter converter = selectConverter(id);

        byte[] fileContent = converter.getContent().getBytes();

        // 设置响应头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.xml");
        // 指定下载文件的名称和类型
        String fileName = "file."+converter.getType();
        String contentType = MediaType.APPLICATION_JSON_VALUE;

        // 创建临时文件
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(fileContent);
        }
        // 设置下载响应头
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(org.apache.commons.io.FileUtils.readFileToByteArray(tempFile));

    }
}




