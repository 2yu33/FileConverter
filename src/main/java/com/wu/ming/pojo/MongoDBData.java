package com.wu.ming.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "mongoDB_data")
public class MongoDBData {

    @Id
    private String id;
    private String data;

    // Getter and Setter methods

    // ...
}
