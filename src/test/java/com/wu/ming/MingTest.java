package com.wu.ming;
import com.wu.ming.dao.FileEsDao;
import com.wu.ming.pojo.FileSearchDTO;
import com.wu.ming.service.EsService;
import com.wu.ming.service.JsonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@SpringBootTest
public class MingTest {
    @Resource
    JsonService jsonService;

    @Autowired
    private FileEsDao fileEsDao;

    @Autowired
    private EsService esService;


    @Test
    public void testEs(){
        FileSearchDTO fileSearchDTO = FileSearchDTO
                .builder()
                .fileName("测试文件名称3")
                .content("测试文件数据的内容3")
                .fileSuffix(".xml")
                .create_time(new Date())
                .build();
        fileEsDao.save(fileSearchDTO);
    }

    @Test
    public void testFind(){
        // fileEsDao.findAll().forEach(System.out::println);
        List<FileSearchDTO> fileSearchDTOS = esService.pageFileSearch(0, 5, "");
        fileSearchDTOS.forEach(System.out::println);
    }

    @Test
    public void testFind2(){
        fileEsDao.findAll().forEach(System.out::println);
    }

    @Test
    public void testDelete(){
        fileEsDao.deleteAll();
    }

}
