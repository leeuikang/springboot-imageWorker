package com.example.image.db;

import com.example.image.container.Image;
import com.example.image.db.dao.ImageDao;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class DBHandler {
    private ImageDao imageDao;
    public void setImageDao(final ImageDao imageDao){
        this.imageDao = imageDao;
    }

    public Set<Image> getAllBannerImage() {

        Map<String,Object> map = new HashMap<>();
        String collectDay = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        map.put("collectDay", collectDay);

        return imageDao.getAllBannerImage(map);
    }

    public Set<Image> getTodayBannerImage() {

        Map<String,Object> map = new HashMap<>();
        String collectStartDay = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String collectEndDay = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        map.put("collectStartDay", collectStartDay);
        map.put("collectEndDay", collectEndDay);

        return imageDao.getTodayBannerImage(map);
    }

    public int checkNewImage(String collectSite,String hash){

        Map<String,Object> map = new HashMap<>();
        map.put("collectSite", collectSite);
        map.put("hash", hash);

        return imageDao.checkNewImage(map);
    }
}
