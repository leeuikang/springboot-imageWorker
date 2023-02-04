package com.example.image.task;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ImageDownloadTest {



    @Test
    public void test(){
        LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
       // System.out.println(date.format(DateTimeFormatter.BASIC_ISO_DATE));
    }
}
