package com.wu.ming.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wu.ming.service.XmlService;

import java.io.*;
import java.util.*;

import com.opencsv.CSVWriter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.nio.charset.StandardCharsets;

@Service
public class XmlServiceImpl implements XmlService {


    @Override
    public String xml2yaml(String xmlStr) {
        if (!xmlValidate(xmlStr)){
            return "转换失败,格式错误";
        }
        String reYaml = "";
        ObjectMapper xmlMapper = new XmlMapper();
        Object xmlObject = null;
        try {
            xmlObject = xmlMapper.readValue(xmlStr, Object.class);
            YAMLMapper yamlMapper = new YAMLMapper();
            reYaml = yamlMapper.writeValueAsString(xmlObject);
        } catch (JsonProcessingException e) {
            reYaml = "转换失败,格式错误";
            e.printStackTrace();
        }
        System.out.println(reYaml);
        return reYaml;
    }

    @Override
    public String xml2csv(String xmlStr) throws Exception {
        if (!xmlValidate(xmlStr)){
            return "转换失败,格式错误";
        }
        // Parse the XML string
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(xmlStr));

        // Get the root element
        Element root = document.getRootElement();

        // Create a StringWriter to hold the CSV content
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer);

        // Convert XML elements to CSV rows
        convertElementToCSV(root, csvWriter);

        // Flush and close the CSV writer
        csvWriter.flush();
        csvWriter.close();

        // Return the CSV string
        return writer.toString();
    }


    private static void convertElementToCSV(Element element, CSVWriter csvWriter) {
        // Get the element's name
        String[] row = new String[]{element.getName(), ""};
        csvWriter.writeNext(row);

        // Get the element's attributes
        List attributes = element.attributes();
        for (Object attribute : attributes) {
            if (attribute instanceof org.dom4j.Attribute) {
                org.dom4j.Attribute attr = (org.dom4j.Attribute) attribute;
                String[] attrRow = new String[]{attr.getName(), attr.getValue()};
                csvWriter.writeNext(attrRow);
            }
        }

        // Get the element's text value
        String text = element.getText().trim();
        if (!text.isEmpty()) {
            String[] textRow = new String[]{"", text};
            csvWriter.writeNext(textRow);
        }

        // Process the element's child elements recursively
        List elements = element.elements();
        for (Object obj : elements) {
            if (obj instanceof Element) {
                Element childElement = (Element) obj;
                convertElementToCSV(childElement, csvWriter);
            }
        }
    }

    // xml转JSON
    @Override
    public String xml2json(String xmlStr) {
        if (!xmlValidate(xmlStr)){
            return "转换失败,格式错误";
        }
        try {
            XmlMapper xmlMapper = new XmlMapper();
            List<Map<String, String>> javaObject = xmlMapper.readValue(xmlStr, new TypeReference<List<Map<String, String>>>() {});

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(javaObject);

            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
            return "转换失败,格式错误";
        }
    }

    // xml文件转json文件
    @Override
    public ResponseEntity<byte[]> fileXml2json(MultipartFile xmlFile) throws IOException {
        if (!xmlValidate(new String(xmlFile.getBytes()))){
            // 构建响应
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted.json");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("转换失败,格式错误".getBytes(StandardCharsets.UTF_8));
        }
        String jsonStr = xml2json(new String(xmlFile.getBytes()));

        // 构建响应
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted.json");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonStr.getBytes(StandardCharsets.UTF_8));
    }

    // xml文件转yaml文件
    @Override
    public ResponseEntity<byte[]> fileXml2Yaml(MultipartFile xmlFile) throws IOException {
        if (!xmlValidate(new String(xmlFile.getBytes()))){
            // 构建响应
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted.json");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("转换失败,格式错误".getBytes(StandardCharsets.UTF_8));
        }
        // Read XML file content
        InputStream inputStream = xmlFile.getInputStream();
        String xmlContent = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        // Convert XML to YAML
        ObjectMapper xmlMapper = new XmlMapper();
        Object xmlObject = xmlMapper.readValue(xmlContent, Object.class);
        YAMLMapper yamlMapper = new YAMLMapper();
        String yamlContent = yamlMapper.writeValueAsString(xmlObject);

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/yaml"));
        headers.setContentDispositionFormData("attachment", "output.yaml");

        // Convert YAML content to byte array
        byte[] yamlBytes = yamlContent.getBytes(StandardCharsets.UTF_8);

        return new ResponseEntity<>(yamlBytes, headers, HttpStatus.OK);
    }

    // xml验证
    @Override
    public boolean xmlValidate(String xmlStr) {
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static String fileXml2Json(File xmlFile) throws DocumentException, IOException {
        // 使用dom4j库解析XML文件
        SAXReader reader = new SAXReader();
        Document document = reader.read(xmlFile);

        // 使用Jackson库将XML转换为JSON字符串
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(document);

        return json;
    }



    // xml文件转csv文件
    @Override
    public ResponseEntity<byte[]> fileXml2Csv(MultipartFile xmlFile) throws IOException {
        if (!xmlValidate(new String(xmlFile.getBytes()))){
            // 构建响应
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted.json");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("转换失败,格式错误".getBytes(StandardCharsets.UTF_8));
        }
        try {
            // 解析XML文件为Document对象
            org.w3c.dom.Document xmlDocument = parseXmlDocument(xmlFile.getInputStream());

            // 将Document对象转换为CSV格式
            List<String[]> csvData = convertXmlDocumentToCsvData(xmlDocument);

            // 创建临时文件来保存CSV数据
            File tempFile = File.createTempFile("converted", ".csv");
            FileWriter fileWriter = new FileWriter(tempFile);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            csvWriter.writeAll(csvData);
            csvWriter.close();

            // 读取CSV文件内容
            byte[] fileContent = readFileContent(tempFile);

            // 删除临时文件
            tempFile.delete();

            // 设置下载文件的响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "converted.csv");

            // 返回包含CSV文件内容的响应实体
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(fileContent);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    private org.w3c.dom.Document parseXmlDocument(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true); // 忽略元素内容中的空格
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(inputStream);
    }


    private List<String[]> convertXmlDocumentToCsvData(org.w3c.dom.Document xmlDocument) {
        List<String[]> csvData = new ArrayList<>();

        // 获取XML根元素
        org.w3c.dom.Element rootElement = xmlDocument.getDocumentElement();

        // 获取CSV标题行
        String[] csvHeader = getXmlElementNames(rootElement);
        csvData.add(csvHeader);

        // 递归处理XML树
        if (rootElement != null) { // 添加空值检查
            processXmlNode(rootElement, csvData, csvHeader);
        }

        return csvData;
    }

    private String[] getXmlElementNames(org.w3c.dom.Element element) {
        NodeList itemList = element.getElementsByTagName("item");
        org.w3c.dom.Element firstItem = (org.w3c.dom.Element) itemList.item(0);
        return getXmlItemChildElementNames(firstItem);
    }

    private String[] getXmlItemChildElementNames(org.w3c.dom.Element itemElement) {
        List<String> childElementNames = new ArrayList<>();

        // 遍历子元素
        NodeList childNodes = itemElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                String elementName = childNode.getNodeName();
                childElementNames.add(elementName);
            }
        }

        return childElementNames.toArray(new String[0]);
    }


    private void processXmlNode(org.w3c.dom.Element element, List<String[]> csvData, String[] csvHeader) {
        // 填充CSV行数据
        String[] csvRow = new String[csvHeader.length];
        for (int i = 0; i < csvHeader.length; i++) {
            Node node = element.getElementsByTagName(csvHeader[i]).item(0);
            String value = (node != null) ? node.getTextContent() : "";
            csvRow[i] = value;
        }
        if (!Arrays.equals(csvRow, csvHeader)) { // 不添加与标题行相同的行
            csvData.add(csvRow);
        }

        // 递归处理子元素
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals("item")) {
                processXmlNode((org.w3c.dom.Element) childNode, csvData, csvHeader);
            }
        }
    }

    private byte[] readFileContent(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        fis.close();
        bos.close();
        return bos.toByteArray();
    }

}
