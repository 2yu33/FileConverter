package com.wu.ming.service.impl;

import com.wu.ming.pojo.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * 在MongoDB中，自增ID的功能是默认不提供的，因为它与MongoDB的分布式特性不太兼容。不过，您可以通过使用自定义逻辑来实现类似的自增ID功能。
 * 以下是一种实现自增ID的方式：
 * 1.创建一个单独的集合用于存储自增ID的文档。该文档将包含一个字段用于存储当前的自增ID值。
 * 2.创建一个用于管理自增ID的服务类，通过查询和更新Sequence集合来实现自增ID的获取和更新。
 */
@Service
public class SequenceService {

    @Autowired(required = false)
    private MongoTemplate mongoTemplate;

    /**
     * 创建一个用于管理自增ID的服务类，通过查询和更新Sequence集合来实现自增ID的获取和更新。
     * @param sequenceName
     * @return
     */
    public long getNextSequence(String sequenceName) {
        Query query = new Query(Criteria.where("_id").is(sequenceName));
        Update update = new Update().inc("value", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
        Sequence sequence = mongoTemplate.findAndModify(query, update, options, Sequence.class);
        if (sequence == null) {
            sequence = new Sequence();
            sequence.setId(sequenceName);
            sequence.setValue(1);
            sequence = mongoTemplate.insert(sequence);
        }
        return sequence.getValue();
    }
}
