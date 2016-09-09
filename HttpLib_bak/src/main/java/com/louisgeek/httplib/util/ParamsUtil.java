package com.louisgeek.httplib.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by louisgeek on 2016/7/12.
 */
public class ParamsUtil {
    private static final String TAG = "ParamsUtil";
    /**
     * paramsStr  "id=1&qq=222"  baseid=27&identitycardimages=755&businesslicenceimages=
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
                        Log.d(TAG, "upxxx paramsStrToMap: paramsStrChilds.length:"+paramsStrChilds.length);
                        if (paramsStrChilds!=null&&paramsStrChilds.length>0){
                            String key=paramsStrChilds[0];
                            String value="";
                            if (paramsStrChilds.length>1) {
                                value=paramsStrChilds[1];
                            }
                            map.put(key,value);
                            Log.d(TAG, "upxxx paramsStrToMap: key:"+key+",value:"+value);
                        }
                    }
                }
            }

        }
        return map;
    }
}
