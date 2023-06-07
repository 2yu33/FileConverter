package com.wu.ming.dao;

import com.wu.ming.pojo.MongoDBData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDBDataRepository extends MongoRepository<MongoDBData, String> {

    // ...

}
