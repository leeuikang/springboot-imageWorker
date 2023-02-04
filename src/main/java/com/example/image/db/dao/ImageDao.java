package com.example.image.db.dao;

import com.example.image.container.Image;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * 2022-12-29 write by leeuikang
 *
 *
 */
@Component
public interface ImageDao {
     Set<Image> getAllBannerImage(Map<String, Object> param);
     Set<Image> getTodayBannerImage(Map<String, Object> param);

     int checkNewImage(Map<String, Object> param);

}
