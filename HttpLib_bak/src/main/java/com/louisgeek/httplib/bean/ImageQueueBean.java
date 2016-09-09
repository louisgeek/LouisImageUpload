package com.louisgeek.httplib.bean;

/**
 * Created by louisgeek on 2016/7/13.
 */
public class ImageQueueBean {
    private int imageQueueID;

    public int getImageQueueID() {
        return imageQueueID;
    }

    public void setImageQueueID(int imageQueueID) {
        this.imageQueueID = imageQueueID;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageIdStr() {
        return imageIdStr;
    }

    public void setImageIdStr(String imageIdStr) {
        this.imageIdStr = imageIdStr;
    }

    private  String imageIdStr;
    private  String imageName;
    private  String imagePath;
    private  String imageUrl;

    public String getImageTime() {
        return imageTime;
    }

    public void setImageTime(String imageTime) {
        this.imageTime = imageTime;
    }

    private  String imageTime;
}
