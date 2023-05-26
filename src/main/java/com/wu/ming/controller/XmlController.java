package com.wu.ming.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wu.ming.exception.BusinessException;
import com.wu.ming.service.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@RestController
public class XmlController {


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
        if (!xmlService.xmlValidate(xmlStr)) {
            return "xml格式错误";
        }
        return xmlService.xml2yaml(xmlStr);
    }

    @PostMapping(value="/xml2csv", produces = "text/csv")
    public String xml2csv(@RequestBody String xmlStr) throws Exception {
        if (!xmlService.xmlValidate(xmlStr)) {
            return "xml格式错误";
        }
        return xmlService.xml2csv(xmlStr);
    }

    @PostMapping("/xml2json")
    public String xml2json(@RequestBody String xmlStr){
        if (!xmlService.xmlValidate(xmlStr)) {
            return "xml格式错误";
        }
        return xmlService.xml2json(xmlStr);
    }

    @PostMapping(value = "/file/xml2json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> uploadXmlFile(@RequestPart("file") MultipartFile xmlFile) throws IOException {
        if (!xmlService.xmlFileValidate(xmlFile)) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted.json");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("xml文件格式错误".getBytes(StandardCharsets.UTF_8));

        }
        return xmlService.fileXml2json(xmlFile);
    }

    @PostMapping("/file/xml2yaml")
    public ResponseEntity<byte[]> convertXmlToYaml(@RequestParam("file") MultipartFile file) throws IOException {
        if (!xmlService.xmlFileValidate(file)) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted.json");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("xml文件格式错误".getBytes(StandardCharsets.UTF_8));

        }
        return xmlService.fileXml2Yaml(file);
    }

    @PostMapping(value = "/file/xml2csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> convertXmlToCsv(@RequestPart("file") MultipartFile file) {
        if (!xmlService.xmlFileValidate(file)) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted.json");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("xml文件格式错误".getBytes(StandardCharsets.UTF_8));

        }
        return xmlService.fileXml2Csv(file);
    }


    @PostMapping("/xmlValidate")
    public Boolean xmlValidate(@RequestBody String xmlStr){
        return xmlService.xmlValidate(xmlStr);
    }

    @PostMapping("/file/xmlValidate")
    public Boolean xmlFileValidate(@RequestBody MultipartFile file){
        return xmlService.xmlFileValidate(file);
    }

    // xml文件排版
    // @PostMapping("/xmlComposing")
    // public ResponseEntity<byte[]> uploadXMLFile(@RequestParam("file") MultipartFile file) {
    //     if (!file.isEmpty()) {
    //         try {
    //             // 解析上传的 XML 文件
    //             Document document = Dom4jUtils.parseXML(file.getInputStream());
    //
    //             // 规范化 XML 文件
    //             Document formattedDocument = Dom4jUtils.formatXML(document);
    //
    //             // 将规范化后的 XML 文件转换为字节数组
    //             byte[] fileBytes = Dom4jUtils.getDocumentBytes(formattedDocument);
    //
    //             // 设置响应头，指定下载的文件名
    //             HttpHeaders headers = new HttpHeaders();
    //             headers.setContentDispositionFormData("attachment", "formatted_file.xml");
    //             headers.setContentType(MediaType.APPLICATION_XML);
    //
    //             // 返回响应实体，包含字节数组和响应头
    //             return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    //         } catch (IOException | DocumentException e) {
    //             e.printStackTrace();
    //             // 处理文件上传失败的情况
    //         }
    //     }
    //
    //     // 处理文件为空的情况
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    // }
}
