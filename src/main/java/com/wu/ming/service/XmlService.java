package com.wu.ming.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.dom4j.DocumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public String xml2csv(String xml) throws Exception;

    /**
     * xml转换JSON
     * @param xmlStr xml字符串
     * @return       转换结果
     */
    String xml2json(String xmlStr);

    /**
     * xml文件转换成JSON文件
     * @param xmlFile xml文件
     * @return 返回结果
     */
    ResponseEntity<byte[]> fileXml2json(MultipartFile xmlFile) throws IOException;

    /**
     * xml文件转换成yaml文件
     * @param xmlFile xml文件
     * @return        返回结果
     */
    ResponseEntity<byte[]> fileXml2Yaml(MultipartFile xmlFile) throws IOException;

    /**
     * xml文件转csv文件
     * @param xmlFile xml文件
     * @return        转换结果
     */
    ResponseEntity<byte[]> fileXml2Csv(MultipartFile xmlFile);

    /**
     * xml验证是否符合规范
     * @param xmlStr xml字符串
     * @return       是否符合规范
     */
    boolean xmlValidate(String xmlStr);

}
