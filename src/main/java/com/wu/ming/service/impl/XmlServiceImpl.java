package com.wu.ming.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.opencsv.CSVWriter;
import com.wu.ming.service.XmlService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class XmlServiceImpl implements XmlService {


    // xml转yaml
    @Override
    public String xml2yaml(String xmlStr) {
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

    // xml转csv
    @Override
    public String xml2csv(String xmlStr) throws Exception {
        try {
            // 解析XML字符串为JsonNode对象
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode rootNode = xmlMapper.readTree(xmlStr);

            // 创建CSVWriter对象
            StringWriter stringWriter = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(stringWriter);

            // 动态生成CSV标题行
            List<String> headers = new ArrayList<>();
            extractFieldNames(rootNode, headers, "");
            csvWriter.writeNext(headers.toArray(new String[0]));

            // 递归处理XML节点，写入CSV数据行
            processXmlNode(rootNode, csvWriter);

            // 关闭CSVWriter
            csvWriter.close();

            // 返回CSV字符串
            return stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常情况，返回错误信息
            return "XML 到 CSV 转换期间出错。";
        }
    }


    // xml转JSON
    @Override
    public String xml2json(String xmlStr) {
        String jsonStr = null;
        try {
            XmlMapper xmlMapper = new XmlMapper();
            // 将xml转换成JsonNode对象，JsonNode为Jackson中表示JSON的树形结构
            JsonNode jsonNode = xmlMapper.readTree(xmlStr.getBytes(StandardCharsets.UTF_8));
            System.out.println(jsonNode.textValue());
            ObjectMapper jsonMapper = new ObjectMapper();
            // 设置格式化输出
            jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

            jsonStr = jsonMapper.writeValueAsString(jsonNode);
            System.out.println(jsonStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonStr;
    }

    // xml文件转json文件
    @Override
    public ResponseEntity<byte[]> fileXml2json(MultipartFile xmlFile) throws IOException {
        // 使用 XmlMapper 将 XML 转换为 JSON
        XmlMapper xmlMapper = new XmlMapper();
        Object json = xmlMapper.readValue(xmlFile.getBytes(), Object.class);

        // 创建临时 JSON 文件
        File tempFile = File.createTempFile("converted", ".json");
        tempFile.deleteOnExit();

        // 将 JSON 写入临时文件
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.writeValue(tempFile, json);

        // 构建响应
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted.json");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(tempFile.length())
                .contentType(MediaType.APPLICATION_JSON)
                .body(org.apache.commons.io.FileUtils.readFileToByteArray(tempFile));
    }

    // xml文件转yaml文件
    @Override
    public ResponseEntity<byte[]> fileXml2Yaml(MultipartFile xmlFile) throws IOException {
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

    // xml文件转csv文件
    @Override
    public ResponseEntity<byte[]> fileXml2Csv(MultipartFile xmlFile) {
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

    // xml格式校验
    @Override
    public boolean xmlValidate(String xmlStr) {
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            return true;
        } catch (DocumentException e) {
            return false;
        }
    }

    // xml文件格式校验
    @Override
    public boolean xmlFileValidate(MultipartFile file) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            return true; // XML 格式正确
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return false; // XML 格式错误
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
        if (element.getNodeName().equals("item")) { // 只处理直接子元素为"item"的情况
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
        }

        // 递归处理直接子元素
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                if (childNode.getNodeName().equals("item")) {
                    processXmlNode((Element) childNode, csvData, csvHeader);
                }
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


    private void extractFieldNames(JsonNode node, List<String> headers, String parentPath) {
        if (node.isObject()) {
            // 如果节点是对象，则处理其子节点
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String fieldName = field.getKey();
                extractFieldNames(field.getValue(), headers, parentPath + fieldName + ".");
            }
        } else if (node.isArray()) {
            // 如果节点是数组，则递归处理数组元素
            for (JsonNode arrayNode : node) {
                extractFieldNames(arrayNode, headers, parentPath);
            }
        } else {
            // 提取节点名称作为标题，仅添加不存在的字段名称
            String[] pathParts = parentPath.split("\\.");
            String fieldName = pathParts[pathParts.length - 1];
            if (!headers.contains(fieldName)) {
                headers.add(fieldName);
            }
        }
    }

    private void processXmlNode(JsonNode node, CSVWriter csvWriter) {
        if (node.isObject()) {
            // 如果节点是对象，则处理其子节点
            List<String> rowData = new ArrayList<>();
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String fieldName = field.getKey();
                JsonNode valueNode = field.getValue();
                if (valueNode.isTextual()) {
                    rowData.add(valueNode.asText());
                } else if (valueNode.isObject() || valueNode.isArray()) {
                    int initialSize = rowData.size(); // 记录初始大小
                    processXmlNode(valueNode, csvWriter);
                    if (rowData.size() == initialSize) {
                        rowData.add(""); // 只在没有添加新数据时添加占位符
                    }
                }
            }
            csvWriter.writeNext(rowData.toArray(new String[0]));
        } else if (node.isArray()) {
            // 如果节点是数组，则递归处理数组元素
            for (JsonNode arrayNode : node) {
                processXmlNode(arrayNode, csvWriter);
            }
        }
    }

}
