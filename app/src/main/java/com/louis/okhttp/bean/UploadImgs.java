package com.louis.okhttp.bean;

/**
 * Created by louisgeek on 2016/7/12.
 */
public class UploadImgs {
    private int userID;
    private String oldFileName;

    public String getShoottime() {
        return shoottime;
    }

    public void setShoottime(String shoottime) {
        this.shoottime = shoottime;
    }

    private String shoottime;
    private String extension;
    private String base64ImgStr;

    public UploadImgs(int userID, String oldFileName, String shoottime, String extension, String base64ImgStr) {
        this.userID = userID;
        this.oldFileName = oldFileName;
        this.shoottime = shoottime;
        this.extension = extension;
        this.base64ImgStr = base64ImgStr;
    }

    public UploadImgs() {
        super();

    }


    public String getOldFileName() {
        return oldFileName;
    }
    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public String getBase64ImgStr() {
        return base64ImgStr;
    }
    public void setBase64ImgStr(String base64ImgStr) {
        this.base64ImgStr = base64ImgStr;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

}
