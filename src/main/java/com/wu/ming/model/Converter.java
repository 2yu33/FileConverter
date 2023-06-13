package com.wu.ming.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName converter
 */

@Data
public class Converter implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *  存储的内容
     */
    private String content;

    /**
     * 存储文件的类型
     */
    private String type;

    /**
     * 存储的大小
     */
    private long size;

    private static final long serialVersionUID = 1L;
}