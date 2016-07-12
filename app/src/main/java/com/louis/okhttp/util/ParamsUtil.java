package com.louis.okhttp.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by louisgeek on 2016/7/12.
 */
public class ParamsUtil {
    /**
     * paramsStr  "id=1&qq=222"
     * @param paramsStr
     * @return
     */
    public static Map<String, String> paramsStrToMap(String paramsStr) {
        Map<String, String> map=null;
        if (paramsStr!=null&&!paramsStr.equals("")) {
            map = new HashMap<>();
            String[] paramsStrs = paramsStr.split("&");
            if (paramsStrs != null && paramsStrs.length > 0) {
                for (int i = 0; i <paramsStrs.length; i++) {
                    String paramsStrChild=paramsStrs[i];
                    if (paramsStrChild!=null&&!paramsStrChild.equals("")){
                        String[] paramsStrChilds = paramsStrChild.split("=");
                        if (paramsStrChilds!=null&&paramsStrChilds.length>1){
                            String key=paramsStrChilds[0];
                            String value=paramsStrChilds[1];
                            map.put(key,value);
                        }
                    }
                }
            }

        }
        return map;
    }
}
