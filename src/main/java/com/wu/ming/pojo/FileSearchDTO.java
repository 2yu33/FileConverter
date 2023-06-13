package com.wu.ming.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * es搜索存储数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "upload_file_content")
public class FileSearchDTO {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * 文章id
     */
    @Id
    private String id;

    /**
     * 文件名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String fileName;

    /**
     * 文件数据内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;


    /**
     * 文件后缀(不分词)
     */
    @Field(type = FieldType.Text, analyzer = "keyword")
    private String fileSuffix;

    /**
     * 添加时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date create_time;

}
