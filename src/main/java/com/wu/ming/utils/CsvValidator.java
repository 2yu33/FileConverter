package com.wu.ming.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

@Component
public class CsvValidator {
    public boolean validateCsv(String csvString) {
        try {
            StringReader reader = new StringReader(csvString);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());

            int size = csvParser.getHeaderNames().size();

            for (CSVRecord csvRecord : csvParser) {

                // 验证逻辑
                //判断每一行的列数是否等于列名的个数
                if (csvRecord.size() != size) {
                    return false;
                }
                //检查记录是否为空行
                boolean isEmptyRow = true;
                for (String field : csvRecord) {
                    if (field.trim().isEmpty()) {
                        isEmptyRow = false;
                        break;
                    }
                }
                if (!isEmptyRow){
                    return false;
                }
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean fileValidateCsv(MultipartFile file) {
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
            //获取列头的个数
            int size = csvParser.getHeaderNames().size();
            for (CSVRecord csvRecord : csvParser) {
                // 验证逻辑
                //判断每一行的列数是否等于列名的个数
                if (csvRecord.size() != size) {
                    return false;
                }
                //检查记录是否为空行
                boolean isEmptyRow = true;
                for (String field : csvRecord) {
                    if (field.trim().isEmpty()) {
                        isEmptyRow = false;
                        break;
                    }
                }
                if (!isEmptyRow){
                    return false;
                }
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
