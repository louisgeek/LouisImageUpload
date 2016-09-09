package com.louisgeek.httplib.bean;

import java.util.List;

/**
 * Created by louisgeek on 2016/7/15.
 */
public class UploadBase64ImgsWithWeatherBean {

    /**
     * userid : 82
     * plantimglong : 120.293327777778
     * plantimglat : 30.2125333333333
     * specificLocation : 具体地址
     * picturedescription : 图片描述
     * plantimgsstr : [{"plantImgStr":"123","plantextension":".jpg","shoottime":"2016-07-05 12:30:23","plantoldfilename":"123.jpg"},{"plantImgStr":"123","plantextension":".jpg","shoottime":"2016-07-05 12:30:23","plantoldfilename":"123.jpg"}]
     */

    private int userid;
    private String plantimglong;
    private String plantimglat;
    private String specificLocation;
    private String picturedescription;
    /**
     * plantImgStr : 123
     * plantextension : .jpg
     * shoottime : 2016-07-05 12:30:23
     * plantoldfilename : 123.jpg
     */

    private List<PlantimgsstrBean> plantimgsstr;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPlantimglong() {
        return plantimglong;
    }

    public void setPlantimglong(String plantimglong) {
        this.plantimglong = plantimglong;
    }

    public String getPlantimglat() {
        return plantimglat;
    }

    public void setPlantimglat(String plantimglat) {
        this.plantimglat = plantimglat;
    }

    public String getSpecificLocation() {
        return specificLocation;
    }

    public void setSpecificLocation(String specificLocation) {
        this.specificLocation = specificLocation;
    }

    public String getPicturedescription() {
        return picturedescription;
    }

    public void setPicturedescription(String picturedescription) {
        this.picturedescription = picturedescription;
    }

    public List<PlantimgsstrBean> getPlantimgsstr() {
        return plantimgsstr;
    }

    public void setPlantimgsstr(List<PlantimgsstrBean> plantimgsstr) {
        this.plantimgsstr = plantimgsstr;
    }

    public static class PlantimgsstrBean {
        private String plantImgStr;
        private String plantextension;
        private String shoottime;
        private String plantoldfilename;

        public String getPlantImgStr() {
            return plantImgStr;
        }

        public void setPlantImgStr(String plantImgStr) {
            this.plantImgStr = plantImgStr;
        }

        public String getPlantextension() {
            return plantextension;
        }

        public void setPlantextension(String plantextension) {
            this.plantextension = plantextension;
        }

        public String getShoottime() {
            return shoottime;
        }

        public void setShoottime(String shoottime) {
            this.shoottime = shoottime;
        }

        public String getPlantoldfilename() {
            return plantoldfilename;
        }

        public void setPlantoldfilename(String plantoldfilename) {
            this.plantoldfilename = plantoldfilename;
        }
    }
}
