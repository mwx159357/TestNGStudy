package com.lemon.api.auto.util;

import com.lemon.api.auto.pojo.Rest;

import java.util.ArrayList;
import java.util.List;

public class RestUtil {
    public static List<Rest> rests = new ArrayList<Rest>();
    static {
       List<Rest> list =ExcelUtil.load(PropertiesUtil.getExcelPath(),"接口信息",Rest.class);
       rests.addAll(list);
    }

    /**
     * 根据apiId获取接口地址
     * @param apiId
     * @return
     */
    public static String getUrlByApiId(String apiId){
        for (Rest rest:rests){
            if(rest.getApiId().equals(apiId)){
                return rest.getUrl();
            }
        }
        return null;
    }

    public static  String getTypeByApiId(String apiId){
        for (Rest rest:rests){
            if(rest.getApiId().equals(apiId)){
                return rest.getType();
            }
        }
        return  null;

    }


}
