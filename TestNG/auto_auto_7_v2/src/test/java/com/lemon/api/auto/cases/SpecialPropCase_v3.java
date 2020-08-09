package com.lemon.api.auto.cases;

import com.alibaba.fastjson.JSONObject;
import com.lemon.api.auto.util.ExcelUtil;
import com.lemon.api.auto.util.HttpUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class SpecialPropCase_v3 {

    String url  =  "http://h104.jzzhibo.com/q100/specialProp";
    String Excelpath = "src/test/resouces/cases_v1.xlsx";

    @Test(dataProvider = "datas")
    public void test1(String params) {
        Map<String,String> map  =(Map<String, String>) JSONObject.parse(params);
        System.out.println(map);
        HttpUtil.DoPost(url, map);
        }

    @DataProvider
    public Object[][] datas () {
        int[] rows = {2,3,4,5};
        int[] cells = {6};
        Object[][] datas = ExcelUtil.datas(Excelpath,rows,cells);
        return datas;
        }
}
