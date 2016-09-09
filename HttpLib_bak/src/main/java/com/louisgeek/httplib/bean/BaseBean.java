package com.louisgeek.httplib.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by louisgeek on 2016/7/11.
 */
public  class BaseBean<T> {

    /**
     * code : 0
     * message : 获取成功！
     * info : {"recordtime":"2016-06-22 00:00:00.0","inserttime":"2016-05-31 16:13:11.0","modifytime":"2016-07-07 11:22:23.0","otherimageslocation":"http://192.168.1.67:8300//uploadfile/image/20160616/201661613303343333948.JPG|http://192.168.1.67:8300//uploadfile/image/20160616/201661610003115548352.JPG","recordtype":"qw","plantid":"1","id":"1","otherimages":"352,337","checkstate":"1"}
     */

    private int code;
    private String message;

    private T info;

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
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

    /**
     * 推荐
     *
     * 用法
     *  TypeToken<BaseBean<OthersBean>> typeToken=new TypeToken<BaseBean<OthersBean>>(){};
     BaseBean<OthersBean> baseBean=BaseBean.fromJsonOne(body,typeToken);
     * @param json
     * @param token
     * @param <T>
     * @return
     */
    public static <T> BaseBean fromJsonOne(String json,TypeToken<T> token) {
        Gson gson = new Gson();
        Type objectType = token.getType();
        return gson.fromJson(json, objectType);
    }

    public static BaseBean fromJsonTwo(String jsonStr,  Class cls){
        Gson gson = new Gson();
        Type objectType = dealParameterizedTypeInner(BaseBean.class,cls);
        return gson.fromJson(jsonStr, objectType);
    }


    //想用泛型的方式解析  但是不行  T会被直接忽略  解析不出OthersBean
    @Deprecated
    public  BaseBean fromJsonThree(String json) {
        Gson gson = new Gson();
        Type objectType = new TypeToken<BaseBean<T>>(){}.getType();
        return gson.fromJson(json, objectType);
    }
    //想用泛型的方式解析  但是不行  TT会被直接忽略  解析不出OthersBean
    @Deprecated
    public static <TT>  BaseBean fromJsonFour(String json,Class<TT> cls){
        Gson gson = new Gson();
        Type objectType = new TypeToken<BaseBean<TT>>(){}.getType();
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

    public  String toJson(Class<T> tClass){
        Gson gson=new Gson();
        return gson.toJson(this,tClass);
    }
}
