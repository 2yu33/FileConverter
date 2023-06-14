package com.wu.ming.service;

import com.wu.ming.pojo.FileSearchDTO;

import java.util.List;

public interface EsService {

    /**
     * 分页查询存储数据
     * @param current  分页下标
     * @param pageSize 页面大小
     * @param keyword  查询条件
     * @return
     */
    public List<FileSearchDTO> pageFileSearch(int current, int pageSize, String keyword);

}
