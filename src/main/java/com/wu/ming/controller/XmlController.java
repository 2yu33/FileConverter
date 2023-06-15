package com.wu.ming.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.wu.ming.service.XmlService;
import com.wu.ming.utils.XMLFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


@RestController
public class XmlController {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private XmlService xmlService;

    /**
     * xml转yaml
     *
     * @param xmlStr 要转换的xml
     * @return 转换后的结果
     */
    @PostMapping("/xml2yaml")
    public String xml2yaml(@RequestBody String xmlStr) {
        return xmlService.xml2yaml(xmlStr);
    }

    @PostMapping("/xml2csv")
    public String xml2csv(@RequestBody String xmlStr) throws Exception {
        return xmlService.xml2csv(xmlStr);
    }

    @PostMapping("/xml2json")
    public String xml2json(@RequestBody String xmlStr){
        return xmlService.xml2json(xmlStr);
    }

    @PostMapping(value = "/file/xml2json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> uploadXmlFile(@RequestPart("file") MultipartFile xmlFile) throws IOException {
        return xmlService.fileXml2json(xmlFile);
    }

    @PostMapping("/file/xml2yaml")
    public ResponseEntity<byte[]> convertXmlToYaml(@RequestParam("file") MultipartFile file) throws IOException {
        return xmlService.fileXml2Yaml(file);
    }

    @PostMapping(value = "/file/xml2csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> convertXmlToCsv(@RequestPart("file") MultipartFile file) throws IOException {
        return xmlService.fileXml2Csv(file);
    }


    @PostMapping("/xmlValidate")
    public Boolean xmlValidate(String xmlStr){
        return xmlService.xmlValidate(xmlStr);
    }

    // xml文件排版
    @PostMapping("/xmlComposing")
    public ResponseEntity<byte[]> uploadXMLFile(@RequestParam("file") MultipartFile file) throws IOException {
        String xmlStr = new String(file.getBytes());
        String format = XMLFormatUtils.format(xmlStr);
        // 设置下载文件的响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "converted.xml");

        // 返回包含CSV文件内容的响应实体
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/xml"))
                .body(format.getBytes(StandardCharsets.UTF_8));
    }

}
