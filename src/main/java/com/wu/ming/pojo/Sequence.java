package com.wu.ming.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 创建一个单独的集合用于存储自增ID的文档。该文档将包含一个字段用于存储当前的自增ID值。
 */
@Document(collection = "sequence")
@Data
public class Sequence {

    @Id
    private String id;
    private long value;

    // Getter and Setter methods

    // ...
}
