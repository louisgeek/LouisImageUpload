package com.louisgeek.httplib.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by louisgeek on 2016/7/11.
 */
public  class BaseListBean<T> {
    /**
     * code : 0
     * message : 获取成功！
     * info : [{"recordtime":"2016-06-22 00:00:00.0","inserttime":"2016-05-31 16:13:11.0","modifytime":"2016-07-07 11:22:23.0","otherimageslocation":"http://192.168.1.67:8300//uploadfile/image/20160616/201661613303343333948.JPG|http://192.168.1.67:8300//uploadfile/image/20160616/201661610003115548352.JPG","recordtype":"qw","plantid":"1","id":"1","otherimages":"352,337","checkstate":"1"}]
     */

    private int code;
    private String message;
    private List<T> info;

    public List<T> getInfo() {
        return info;
    }

    public void setInfo(List<T> info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //肯定  解析不出OthersBean
    @Deprecated
    public static BaseListBean fromJsonOne(String json) {
        Gson gson = new Gson();
        Type objectType = new TypeToken<BaseListBean>(){}.getType();
        return gson.fromJson(json, objectType);
    }
    //想用泛型的方式解析  但是不行  T会被直接忽略  解析不出OthersBean
    @Deprecated
    public  BaseListBean fromJsonTwo(String json) {
        Gson gson = new Gson();
        Type objectType = new TypeToken<BaseListBean<T>>(){}.getType();
        return gson.fromJson(json, objectType);
    }
    public static BaseListBean fromJsonThree(String json) {
        Gson gson = new Gson();
        Type objectType = new TypeToken<BaseListBean<OthersBean>>(){}.getType();
        return gson.fromJson(json, objectType);
    }
    //想用泛型的方式解析  但是不行  TT会被直接忽略  解析不出OthersBean
    @Deprecated
    public static <TT>  BaseListBean fromJsonFour(String json,Class<TT> cls){
        Gson gson = new Gson();
        Type objectType = new TypeToken<BaseListBean<TT>>(){}.getType();
        return gson.fromJson(json, objectType);
    }

    /**
     *
     * 可用 推荐方式  类似于gson.fromJson(jsonString, new TypeToken<List<String>>(){}.getType());
     * 好理解

     使用方式：
    TypeToken<BaseListBean<OthersBean>> typeToken=new TypeToken<BaseListBean<OthersBean>>(){};
    BaseListBean<OthersBean> baseListBean5=BaseListBean.fromJsonFive(body,typeToken);
     * @param json
     * @param token
     * @param <T>
     * @return
     */
    public static <T> BaseListBean fromJsonFive(String json,TypeToken<T> token) {
        Gson gson = new Gson();
        Type objectType = token.getType();
        return gson.fromJson(json, objectType);
    }

    public static BaseListBean fromJsonSix(String json,Class cls) {
        Gson gson = new Gson();
        Type objectType = dealParameterizedTypeInner(BaseListBean.class,cls);
        return gson.fromJson(json, objectType);
    }



    private  static ParameterizedType dealParameterizedTypeInner(final Class raw, final Type... args) {
        ParameterizedType parameterizedType= new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
        return parameterizedType;
    }



    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", info=" + info +
                '}';
    }
  /*  public String toJson(Class<T> tClass) {
        Gson gson = new Gson();
        Type objectType = type(CommonJson4List.class, clazz);
        return gson.toJson(this, objectType);
    }
    public  String toJson(Class<T> tClass){
        Gson gson=new Gson();
        return gson.toJson(this,tClass);
    }*/
}
