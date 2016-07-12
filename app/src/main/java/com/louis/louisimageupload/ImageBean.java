package com.louis.louisimageupload;

/**
 * Created by louis on 2016/7/10.
 */
public class ImageBean {
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageResID() {
        return imageResID;
    }

    public void setImageResID(String imageResID) {
        this.imageResID = imageResID;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    private String imageResID;
    private String imageTitle;
}
