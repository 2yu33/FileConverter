package com.wu.ming;
import com.wu.ming.dao.FileEsDao;
import com.wu.ming.pojo.FileSearchDTO;
import com.wu.ming.service.JsonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;


@SpringBootTest
public class MingTest {
    @Resource
    JsonService jsonService;

    @Autowired
    private FileEsDao fileEsDao;


    @Test
    public void testEs(){
        FileSearchDTO fileSearchDTO = FileSearchDTO
                .builder()
                .fileName("测试文件名称")
                .content("测试文件数据的内容")
                .fileSuffix(".xml")
                .build();
        fileEsDao.save(fileSearchDTO);
    }

}
