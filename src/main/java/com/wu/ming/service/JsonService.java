package com.wu.ming.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonService {
    /**
     * json转xml
     * @param json
     * @return
     */
    public String json2Xml(String json);

    /**
     * json转yaml
     * @param json
     * @return
     */
    public String json2Yaml(String json) throws JsonProcessingException;

    /**
     * json转xml
     * @param json
     * @return
     */
    public String json2Csv(String json) throws JsonProcessingException;

}
