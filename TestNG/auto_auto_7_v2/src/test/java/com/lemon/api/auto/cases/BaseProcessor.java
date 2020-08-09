package com.lemon.api.auto.cases;

import com.alibaba.fastjson.JSONObject;
import com.lemon.api.auto.util.DBCheckUtil;
import com.lemon.api.auto.pojo.WriteBackData;
import com.lemon.api.auto.util.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.util.Map;

/**
 *
 */
public class BaseProcessor {
    public String[] cellNames = {"CaseId","ApiId","params","ExpectedResponseData","preValidataSql","afterValidateSql"};


    @Test(dataProvider = "datas")
    public void test(String caseId,String apiId,String parameter,String expectedResponseData,
                     String preValidataSql,String afterValidateSql ){
        //执行接口调用掐灭数据验证
        if(preValidataSql!=null&&preValidataSql.trim().length()>0){
            preValidataSql = VariableUtil.replaceVariables(preValidataSql);
            // 在接口调用前查询我们想要验证的字段
            String perValidataResult = DBCheckUtil.doQuery(preValidataSql);
            ExcelUtil.writeBackDatas.add(new WriteBackData(perValidataResult,"用例",caseId,"PerValidataResult"));

        }
        //根据apiId 接口编号获取接口地址
        String url  =  RestUtil.getUrlByApiId(apiId);
        //根据apiId 接口编号获取提交类型type
        String type  = RestUtil.getTypeByApiId(apiId);
        //替换测试数据中的所有变量
        parameter=VariableUtil.replaceVariables(parameter);

        //需要参数c
        Map<String,String> params  = (Map<String, String>) JSONObject.parse(parameter);
        //调用doserver方法完成接口调用，拿到接口响应报文
        String actualResponseData = HttpUtil.doService(url,type,params);
        //断言比较期望值和实际测试结果
        actualResponseData = AssertUtil.asserEquals(actualResponseData,expectedResponseData);

        //保存回写数据对像
        ExcelUtil.writeBackDatas.add(new WriteBackData(actualResponseData,"用例",caseId,"ActualResponseData"));
        if(afterValidateSql!=null&&afterValidateSql.trim().length()>0){
            afterValidateSql=VariableUtil.replaceVariables(afterValidateSql);
            //在接口调用后查询我们想要验证的字段
            String afterValidataResult = DBCheckUtil.doQuery(afterValidateSql);
            ExcelUtil.writeBackDatas.add(new WriteBackData(afterValidataResult,"用例",caseId,"AfterValidataResult"));
        }
    }

    /**
     * 批量回写数据
     */
    @AfterSuite
    public void batchWriteBackDatas(){
        ExcelUtil.batchWriteBackDatas(PropertiesUtil.getExcelPath());
    }
}
