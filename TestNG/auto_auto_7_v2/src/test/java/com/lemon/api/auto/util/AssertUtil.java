package com.lemon.api.auto.util;

import org.testng.Assert;

public class AssertUtil {

    /**
     * 自定义类库：断言比较实际测试结果跟期望值是否一样
     * @param actualResponseData
     * @param expectedResponse
     * @return
     */
    public static  String asserEquals(String actualResponseData,String expectedResponse){
        String result  = "通过";
        try{
            Assert.assertEquals(actualResponseData,expectedResponse);
        }catch(Throwable e){
            result =actualResponseData;
        }
        return result;
    }
}
