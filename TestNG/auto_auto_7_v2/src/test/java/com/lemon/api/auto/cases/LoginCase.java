package com.lemon.api.auto.cases;

import com.lemon.api.auto.util.CaseUtil;
import org.testng.annotations.DataProvider;

/**
 * 登陆接口测试类
 * @author  Administrator
 */

public class LoginCase extends  BaseProcessor{
    @DataProvider
    public  Object[][] datas(){
        Object[][] datas  =  CaseUtil.getCaseDatasByAPiId("2",cellNames);
        return datas;
    }

}
