package com.example.image.container;

import lombok.Data;

import java.util.Objects;

/**
 *  2022-12-29 write by leeuikang
 *  equals method check hash & collectSite
 */
@Data
public class Image {
    private String image;
    private String hash;
    private String collect_site;
    private String reg_dt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image1 = (Image) o;
        return Objects.equals(image, image1.image) && Objects.equals(collect_site, image1.collect_site);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, hash, collect_site);
    }

    public boolean compareTo(String date){
        return this.reg_dt.compareTo(date) >= 0;
    }
}
