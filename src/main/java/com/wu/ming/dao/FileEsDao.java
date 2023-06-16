package com.wu.ming.dao;

import com.wu.ming.pojo.FileSearchDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 文件Es存储Dao
 */
public interface FileEsDao extends ElasticsearchRepository<FileSearchDTO, Integer> {

    /**
     * 通过id删除信息
     * @param id
     */
    void deleteById(String id);

}
