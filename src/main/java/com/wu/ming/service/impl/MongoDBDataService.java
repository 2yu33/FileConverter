package com.wu.ming.service.impl;

import com.wu.ming.dao.MongoDBDataRepository;
import com.wu.ming.pojo.MongoDBData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
     * 查询所有的数据
     * @return
     */
    public List<String> getAllData() {
//        List<String> list = mongoTemplate.findAll(MongoDBData.class).stream()
//                .map(mongodb -> mongodb.getFileName())
//                .collect(Collectors.toList());
        Query query = new Query();
        query.fields().include("_id");

        List<String> fileNames = mongoTemplate.findDistinct(query, "_id",MongoDBData.class, String.class);
        return fileNames;
    }

    /**
     * 通过文件名删除文件
     * @param fileName
     */
    public void deleteByFileName(String fileName) {
        Criteria criteria = Criteria.where("fileName").is(fileName);
        Query query = Query.query(criteria);
        mongoTemplate.remove(query, MongoDBData.class);
    }

    // ...
}
