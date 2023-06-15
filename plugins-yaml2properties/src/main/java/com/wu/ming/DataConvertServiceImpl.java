package com.wu.ming;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据转换规范
 */
public class DataConvertServiceImpl implements DataConvertService{


    // @Override
    public String dataConvert(String data) {
        return "成功调用:"+data;
    }

    // 文件转换
    // @Override
    public ResponseEntity<byte[]> fileDataConvert(MultipartFile file) throws IOException {
        String yamlString = new String(file.getBytes());
        if(yamlString == null)
            throw new RuntimeException("yaml字符串不能为空");
        Yaml yaml = new Yaml();
        List<Map<String, Object>> data = yaml.load(yamlString);

        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> item : data) {
            Map<String, Object> map = new HashMap<>();
            for (Map.Entry<String, Object> entry : item.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
            sb.append(map.toString().replace("{", "")
                    .replace("}", "")
                    .replace(", ", "\n")
                    .replace("=", " = "));
            sb.append("\n");
        }

        String output = sb.toString();

        // byte[] pro = output.getBytes();

        // 设置响应头信息
        // HttpHeaders headers = new HttpHeaders();
        // headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.xml");
        // 指定下载文件的名称和类型
        String fileName = "file.properties";
        String contentType = MediaType.APPLICATION_JSON_VALUE;

        // 创建临时文件
        // File tempFile = File.createTempFile("temp", null);
        // try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
        //     outputStream.write(pro);
        // }
        // 设置下载响应头
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(output.getBytes(StandardCharsets.UTF_8));
    }

    // @Override
    public String getConvertType() {
        return "Yaml2Properties";
    }
}
