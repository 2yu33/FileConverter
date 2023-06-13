package com.wu.ming.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageUtils implements Serializable {
    private static final long serialVersionUID = 3191241716373120332L;
    /**
     * 一页数据的数量
     */
    private int pageSize;
    /**
     * 当前页
     */
    private int pageNum;
}
