package com.louisgeek.httplib.util;

import java.util.Map;

/**
 * Created by louisgeek on 2016/7/12.
 */
public class UrlUtil {
    public static String dealUrlAndQueryStr(String url,String queryStr) {
        if (url.contains("?")){
            if (url.contains("&")){
                if (url.endsWith("&")){
                    url=url+queryStr;
                }else{
                    url=url+"&"+queryStr;
                }
            }else{
                if (url.endsWith("?")){
                    url=url+queryStr;
                }else{
                    url=url+"?"+queryStr;
                }
            }
        }else {
            url=url+"?"+queryStr;
        }
        return  url;
    }
    public static String mapToQueryStr(Map<String, String> map) {
        StringBuilder string = new StringBuilder();
        for(Map.Entry<String, String> entry : map.entrySet()) {
            string.append(entry.getKey());
            string.append("=");
            string.append(entry.getValue());
            string.append("&");
        }
        return string.toString();
    }
}
