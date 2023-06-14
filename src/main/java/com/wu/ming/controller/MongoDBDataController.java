package com.wu.ming.controller;

import com.wu.ming.service.impl.MongoDBDataService;
import com.wu.ming.service.impl.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mongodb")
public class MongoDBDataController {

    private final MongoDBDataService mongoDBDataService;

    @Autowired
    public MongoDBDataController(MongoDBDataService mongoDBDataService, SequenceService sequenceService) {
        this.mongoDBDataService = mongoDBDataService;
    }

    @PostMapping
    public void saveJsonData(@RequestBody String jsonData) {
        mongoDBDataService.saveJsonData(jsonData);
    }

    @GetMapping("/{id}")
    public String getJsonDataById(@PathVariable String id) {
        return mongoDBDataService.getJsonDataById(id);
    }

    // ...
}