package com.wu.ming.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface XmlService {

    /**
     * xml转换yaml
     * @param xml 要转换的xml
     * @return    转换后的结果
     */
    public String xml2yaml(String xml);

    /**
     * xml转换csv
     * @param xml 要转换的xml
     * @return    转换后的结果
     */
    public String xml2csv(String xml) throws JsonProcessingException;

}
