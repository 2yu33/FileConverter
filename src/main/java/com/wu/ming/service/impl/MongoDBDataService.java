package com.wu.ming.service.impl;

import com.wu.ming.dao.MongoDBDataRepository;
import com.wu.ming.pojo.MongoDBData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class MongoDBDataService {

    @Autowired
    private MongoDBDataRepository mongoDBDataRepository;
    @Autowired(required = false)
    private MongoTemplate mongoTemplate;

    /**
     * 存入json字符串
     * @param fileName
     * @param jsonData
     */
    public void saveJsonData(String fileName, String jsonData) {
        MongoDBData data = new MongoDBData();
        data.setFileName(fileName);
        data.setData(jsonData);
        mongoDBDataRepository.save(data);
    }

    /**
     * 根据id去查数据
     * @param fileName
     * @return
     */
    public String getJsonDataByFileName(String fileName) {
        Query query = new Query(Criteria.where("fileName").is(fileName));
        MongoDBData mongoDBData = mongoTemplate.findOne(query, MongoDBData.class);
        if (mongoDBData != null) {
            return mongoDBData.getData();
        } else {
            return "ID为："+fileName+" 的数据不存在！";
        }
    }

    // ...
}
