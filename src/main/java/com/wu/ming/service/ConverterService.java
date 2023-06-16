package com.wu.ming.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wu.ming.model.Converter;
import com.wu.ming.utils.PageUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
* @author 余某某
* @description 针对表【converter】的数据库操作Service
* @createDate 2023-06-08 14:16:05
*/
public interface ConverterService extends IService<Converter> {
      /**
       * 前端上传文件，接受文件并存储文件的内容
       * @param file
       * @return
       */
      long addConverter( MultipartFile file);

      /**
       * 通过id查找并删除相应的内容
       * @param id
       * @return
       */
      boolean removeConverter(int id);

      /**
       * 通过id查询单个内容
       * @param id
       * @return
       */
      Converter selectConverter(int id);

      /**
       * 查询所有结果并分页展示
       * @return
       */
      Page<Converter> selectConverters(PageUtils pageUtils);

      /**
       * 通过id下载指定的文件
       * @param id
       * @return
       */
      ResponseEntity<byte[]> downloadFile(Integer id) throws IOException;
}
