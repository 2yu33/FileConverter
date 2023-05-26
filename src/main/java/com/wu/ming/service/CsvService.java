package com.wu.ming.service;

import com.opencsv.exceptions.CsvValidationException;
import com.wu.ming.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface CsvService {



    /**
     * 把csv字符串转json字符串
     * @param csvString
     * @return
     * @throws IOException
     * @throws CsvValidationException
     */
    BaseResponse<String> csvToJson(String csvString) throws IOException, CsvValidationException;

    /**
     * 把csv字符串转xml字符串
     * @param csvString
     * @return
     * @throws IOException
     * @throws CsvValidationException
     */
    BaseResponse<String> csvToXml(String csvString) throws IOException, CsvValidationException;

    /**
     * 把csv字符串转yaml字符串
     * @param csvString
     * @return
     * @throws IOException
     * @throws CsvValidationException
     */
    BaseResponse<String> csvToYaml(String csvString) throws IOException, CsvValidationException;

    /**
     * 把传入的csv文件转换为json格式并以文件方式下载
     * @param file
     * @return
     * @throws IOException
     * @throws CsvValidationException
     */
    ResponseEntity<byte[]> fileCsvToJson(MultipartFile file) throws IOException, CsvValidationException;

    /**
     * 把传入的csv文件转换为xml格式并以文件方式下载
     * @param file
     * @return
     * @throws IOException
     * @throws CsvValidationException
     */
    ResponseEntity<byte[]> fileCsvToXml(MultipartFile file) throws IOException, CsvValidationException;

    /**
     * 把传入的csv文件转换为yaml格式并以文件方式下载
     * @param file
     * @return
     * @throws IOException
     * @throws CsvValidationException
     */
    ResponseEntity<byte[]> fileCsvToYaml(MultipartFile file) throws IOException, CsvValidationException;
}
