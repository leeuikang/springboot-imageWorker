package com.example.image.task;

import com.example.image.config.JasyptConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptTest {

    @Autowired
    JasyptConfiguration jasyptConfiguration;
    @Test
    public void test(){
        var method =jasyptConfiguration.stringEncryptor(jasyptConfiguration.stringPBEConfig());
        String s6 = method.encrypt("wspider");
        String s7 = method.encrypt("wspider00!q");
        System.out.println(s6);
        System.out.println("-------------------");


    }
}
