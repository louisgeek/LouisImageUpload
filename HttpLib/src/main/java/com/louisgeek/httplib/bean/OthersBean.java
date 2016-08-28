package com.louisgeek.httplib.bean;

/**
 * Created by louisgeek on 2016/7/11.
 */
public class OthersBean {


    /**
     * recordtime : 2016-06-22 00:00:00.0
     * inserttime : 2016-05-31 16:13:11.0
     * modifytime : 2016-07-07 11:22:23.0
     * otherimageslocation : http://192.168.1.67:8300//uploadfile/image/20160616/201661613303343333948.JPG|http://192.168.1.67:8300//uploadfile/image/20160616/201661610003115548352.JPG
     * recordtype : qw
     * plantid : 1
     * id : 1
     * otherimages : 352,337
     * checkstate : 1
     */

    private String recordtime;
    private String inserttime;
    private String modifytime;
    private String otherimageslocation;
    private String recordtype;
    private String plantid;
    private String id;
    private String otherimages;
    private String checkstate;

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getOtherimageslocation() {
        return otherimageslocation;
    }

    public void setOtherimageslocation(String otherimageslocation) {
        this.otherimageslocation = otherimageslocation;
    }

    public String getRecordtype() {
        return recordtype;
    }

    public void setRecordtype(String recordtype) {
        this.recordtype = recordtype;
    }

    public String getPlantid() {
        return plantid;
    }

    public void setPlantid(String plantid) {
        this.plantid = plantid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OthersListBean{" +
                "recordtime='" + recordtime + '\'' +
                ", inserttime='" + inserttime + '\'' +
                ", modifytime='" + modifytime + '\'' +
                ", otherimageslocation='" + otherimageslocation + '\'' +
                ", recordtype='" + recordtype + '\'' +
                ", plantid='" + plantid + '\'' +
                ", id='" + id + '\'' +
                ", otherimages='" + otherimages + '\'' +
                ", checkstate='" + checkstate + '\'' +
                '}';
    }

    public String getOtherimages() {
        return otherimages;
    }

    public void setOtherimages(String otherimages) {
        this.otherimages = otherimages;
    }

    public String getCheckstate() {
        return checkstate;
    }

    public void setCheckstate(String checkstate) {
        this.checkstate = checkstate;
    }
}
