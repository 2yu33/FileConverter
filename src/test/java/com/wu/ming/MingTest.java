package com.wu.ming;
import com.wu.ming.service.JsonService;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;


@SpringBootTest
public class MingTest {
    public static void main(String[] args) {
        MingTest mingTest = new MingTest();
        String s = new String();
        System.out.println(mingTest.getClass().getClassLoader().getParent().getParent());
        System.out.println(s.getClass().getClassLoader());
        new Thread().start();
    }

}
