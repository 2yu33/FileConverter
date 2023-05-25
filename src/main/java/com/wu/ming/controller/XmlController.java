package com.wu.ming.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wu.ming.service.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


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
    public String xml2yaml(String xmlStr) {
        return xmlService.xml2yaml(xmlStr);
    }

    @PostMapping("/xml2csv")
    public String xml2csv(String xmlStr) throws Exception {
        return xmlService.xml2csv(xmlStr);
    }

    @PostMapping("/xml2json")
    public String xml2json(String xmlStr){
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
    public ResponseEntity<byte[]> convertXmlToCsv(@RequestPart("file") MultipartFile file) {
        return xmlService.fileXml2Csv(file);
    }


    @PostMapping("/xmlValidate")
    public Boolean xmlValidate(String xmlStr){
        return xmlService.xmlValidate(xmlStr);
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
