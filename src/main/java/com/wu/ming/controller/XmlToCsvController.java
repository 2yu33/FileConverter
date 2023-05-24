// package com.wu.ming.controller;
//
// import com.opencsv.CSVWriter;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestPart;
// import org.springframework.web.multipart.MultipartFile;
// import org.w3c.dom.*;
// import org.xml.sax.InputSource;
// import org.xml.sax.SAXException;
//
// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;
// import javax.xml.parsers.ParserConfigurationException;
// import java.io.*;
// import java.nio.charset.StandardCharsets;
// import java.util.*;
//
// @Controller
// public class XmlToCsvController {
//
//     @PostMapping(value = "/file/xml2csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//     public ResponseEntity<byte[]> convertXmlToCsv(@RequestPart("file") MultipartFile file) {
//         try {
//             // 解析XML文件为Document对象
//             Document xmlDocument = parseXmlDocument(file.getInputStream());
//
//             // 将Document对象转换为CSV格式
//             List<String[]> csvData = convertXmlDocumentToCsvData(xmlDocument);
//
//             // 创建临时文件来保存CSV数据
//             File tempFile = File.createTempFile("converted", ".csv");
//             FileWriter fileWriter = new FileWriter(tempFile);
//             CSVWriter csvWriter = new CSVWriter(fileWriter);
//             csvWriter.writeAll(csvData);
//             csvWriter.close();
//
//             // 读取CSV文件内容
//             byte[] fileContent = readFileContent(tempFile);
//
//             // 删除临时文件
//             tempFile.delete();
//
//             // 设置下载文件的响应头
//             HttpHeaders headers = new HttpHeaders();
//             headers.setContentDispositionFormData("attachment", "converted.csv");
//
//             // 返回包含CSV文件内容的响应实体
//             return ResponseEntity.ok()
//                     .headers(headers)
//                     .contentType(MediaType.parseMediaType("text/csv"))
//                     .body(fileContent);
//
//         } catch (Exception e) {
//             e.printStackTrace();
//             return ResponseEntity.badRequest().build();
//         }
//     }
//
//     private Document parseXmlDocument(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
//         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//         factory.setIgnoringElementContentWhitespace(true); // 忽略元素内容中的空格
//         DocumentBuilder builder = factory.newDocumentBuilder();
//         return builder.parse(inputStream);
//     }
//
//
//     private List<String[]> convertXmlDocumentToCsvData(Document xmlDocument) {
//         List<String[]> csvData = new ArrayList<>();
//
//         // 获取XML根元素
//         Element rootElement = xmlDocument.getDocumentElement();
//
//         // 获取CSV标题行
//         String[] csvHeader = getXmlElementNames(rootElement);
//         csvData.add(csvHeader);
//
//         // 递归处理XML树
//         if (rootElement != null) { // 添加空值检查
//             processXmlNode(rootElement, csvData, csvHeader);
//         }
//
//         return csvData;
//     }
//
//
//     private String[] getXmlElementNames(Element element) {
//         Set<String> elementNames = new LinkedHashSet<>();
//
//         // 递归遍历XML树，获取所有元素名称
//         traverseXmlElement(element, elementNames);
//
//         return elementNames.toArray(new String[0]);
//     }
//
//     private void traverseXmlElement(Element element, Set<String> elementNames) {
//         // 获取当前元素的名称
//         String elementName = element.getTagName();
//         elementNames.add(elementName);
//
//         // 遍历子元素
//         NodeList childNodes = element.getChildNodes();
//         for (int i = 0; i < childNodes.getLength(); i++) {
//             Node childNode = childNodes.item(i);
//             if (childNode.getNodeType() == Node.ELEMENT_NODE) {
//                 traverseXmlElement((Element) childNode, elementNames);
//             }
//         }
//     }
//     private void processXmlNode(Element element, List<String[]> csvData, String[] csvHeader) {
//         // 填充CSV行数据
//         String[] csvRow = new String[csvHeader.length];
//         boolean isRowEmpty = true; // 标记当前行是否为空行
//         for (int i = 0; i < csvHeader.length; i++) {
//             Node node = element.getElementsByTagName(csvHeader[i]).item(0);
//             String value = (node != null) ? node.getTextContent() : "";
//             csvRow[i] = value;
//             if (!value.isEmpty()) {
//                 isRowEmpty = false;
//             }
//         }
//
//         // 仅当行非空时添加到CSV数据
//         if (!isRowEmpty) {
//             csvData.add(csvRow);
//         }
//
//         // 递归处理子元素
//         NodeList childNodes = element.getChildNodes();
//         for (int i = 0; i < childNodes.getLength(); i++) {
//             Node childNode = childNodes.item(i);
//             if (childNode.getNodeType() == Node.ELEMENT_NODE) {
//                 processXmlNode((Element) childNode, csvData, csvHeader);
//             }
//         }
//     }
//
//
//
//     private byte[] readFileContent(File file) throws IOException {
//         FileInputStream fis = new FileInputStream(file);
//         ByteArrayOutputStream bos = new ByteArrayOutputStream();
//         byte[] buffer = new byte[1024];
//         int bytesRead;
//         while ((bytesRead = fis.read(buffer)) != -1) {
//             bos.write(buffer, 0, bytesRead);
//         }
//         fis.close();
//         bos.close();
//         return bos.toByteArray();
//     }
// }
