package com.wu.ming.service.impl;

import com.opencsv.exceptions.CsvValidationException;
import com.wu.ming.common.ErrorCode;
import com.wu.ming.dao.MongoDBDataRepository;
import com.wu.ming.exception.BusinessException;
import com.wu.ming.pojo.MongoDBData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public ResponseEntity downloadFile(String fileName) throws IOException, CsvValidationException {
        Query query = new Query(Criteria.where("_id").is(fileName));
        String data = mongoTemplate.findOne(query, MongoDBData.class).getData();
        // 将请求中的数据转换为字节数组
        byte[] fileData = data.getBytes();

        // 指定下载文件的名称和类型
        String contentType = MediaType.TEXT_PLAIN_VALUE;

        // 创建临时文件
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(fileData);
        }
        // 设置下载响应头
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(org.apache.commons.io.FileUtils.readFileToByteArray(tempFile));

    }

}
