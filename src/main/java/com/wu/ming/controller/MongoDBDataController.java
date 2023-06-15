package com.wu.ming.controller;

import com.wu.ming.service.impl.MongoDBDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mongodb")
public class MongoDBDataController {

    private final MongoDBDataService mongoDBDataService;

    @Autowired
    public MongoDBDataController(MongoDBDataService mongoDBDataService) {
        this.mongoDBDataService = mongoDBDataService;
    }

    @PostMapping("/{name}")
    public void saveJsonData(@PathVariable("name") String name,@RequestBody String jsonData) {
        mongoDBDataService.saveJsonData(name,jsonData);
    }

    @GetMapping("/{name}")
    public String getJsonDataById(@PathVariable String name) {
        return mongoDBDataService.getJsonDataByFileName(name);
    }

    // ...
}
