package com.wu.ming.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.exception.BusinessException;
import com.wu.ming.mapper.ConverterMapper;
import com.wu.ming.model.Converter;
import com.wu.ming.service.ConverterService;
import com.wu.ming.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

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
    public Page<Converter> selectConverters(PageUtils pageUtils) {
    Page<Converter> pageList = new Page<>(pageUtils.getPageNum(),pageUtils.getPageSize());
    QueryWrapper<Converter> queryWrapper = new QueryWrapper<>();
    Page<Converter> resultPage = this.page(pageList,queryWrapper);
    return resultPage;
    }
}




