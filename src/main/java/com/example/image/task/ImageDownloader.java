package com.example.image.task;

import com.example.image.container.Image;


import com.example.image.db.DBHandler;
import com.example.image.db.dao.lg.lg.LgegDao;
import com.example.image.db.dao.lg.lg2.Lgeg2Dao;
import com.example.image.db.dao.lg.lg3.Lgeg3Dao;
import com.example.image.db.dao.lg.lg4.Lgeg4Dao;
import com.example.image.db.dao.lg.lg5.Lgeg5Dao;
import com.example.image.db.dao.lg.lg6.Lgeg6Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * logback.xml 차후 추가 예정
 */
@Component
public class ImageDownloader implements ApplicationRunner {
    private static final int retryCount = 5;
    @Autowired
    private DBHandler dbHandler;

    @Autowired
    private ApplicationContext context;

    @Value("${spring.datasource-lg-eg.use}")
    private boolean DB1;

    @Value("${spring.datasource-lg-eg2.use}")
    private boolean DB2;

    @Value("${spring.datasource-lg-eg3.use}")
    private boolean DB3;

    @Value("${spring.datasource-lg-eg4.use}")
    private boolean DB4;

    @Value("${spring.datasource-lg-eg5.use}")
    private boolean DB5;

    @Value("${spring.datasource-lg-eg6.use}")
    private boolean DB6;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            List<Boolean> dbList = Arrays.asList(DB1, DB2, DB3, DB4, DB5, DB6);
            for (int index = 0; index < dbList.size(); index++) {
                if (dbList.get(index)) {
                    switch (index) {
                        case 0 -> dbHandler.setImageDao(context.getBean(LgegDao.class));
                        case 1 -> dbHandler.setImageDao(context.getBean(Lgeg2Dao.class));
                        case 2 -> dbHandler.setImageDao(context.getBean(Lgeg3Dao.class));
                        case 3 -> dbHandler.setImageDao(context.getBean(Lgeg4Dao.class));
                        case 4 -> dbHandler.setImageDao(context.getBean(Lgeg5Dao.class));
                        case 5 -> dbHandler.setImageDao(context.getBean(Lgeg6Dao.class));
                    }
                    //Set<Image> todayBannerImageList = getBannerImagesFromDB(); //getTodayImagesFromDB(); 교체 예정
                    Set<Image> todayBannerImageList = getTodayImagesFromDB(); // 교체 예정
                    List<Image> downloadImageList = getDownloadImageList(todayBannerImageList);
                    fileDownLoad(downloadImageList);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //error control and logging
        }
    }

    public Set<Image> getBannerImagesFromDB() throws Exception {
        return dbHandler.getAllBannerImage();
    }


    public Set<Image> getTodayImagesFromDB() throws Exception {
        return dbHandler.getTodayBannerImage();
    }

    public List<Image> getDownloadImageList(Set<Image> todayBannerImageList) throws Exception {
        return todayBannerImageList.stream()
                .filter(image -> dbHandler.checkNewImage(image.getCollect_site(), image.getHash()) <= 1)
                .collect(Collectors.toList());
    }

    public void fileDownLoad(List<Image> duplicatedImageRemoveList) throws Exception {
        RequestService request = new RequestService();
        request.setRetryCount(retryCount);

        /**
         * 이미지 업로드할때 db에 인서트
         * 이미지  업로드 이름 url이름
         * 이미지 hash
         * collect_site
         * hash, collect_site, url, reg_dt
         **/

        for (Image image : duplicatedImageRemoveList) {
            if (request.request(image)) {
                System.out.println("success");
                // logging sucess
            } else {
                System.out.printf("download fail %s%n", image.getImage());
                // logging
            }
        }
    }


}
