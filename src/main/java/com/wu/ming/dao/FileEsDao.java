package com.wu.ming.dao;

import com.wu.ming.pojo.FileSearchDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 文件Es存储Dao
 */
public interface FileEsDao extends ElasticsearchRepository<FileSearchDTO, Integer> {



}
