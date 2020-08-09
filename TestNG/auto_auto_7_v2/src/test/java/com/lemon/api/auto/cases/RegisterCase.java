package com.lemon.api.auto.cases;
import com.lemon.api.auto.util.*;

import org.testng.annotations.DataProvider;


public class RegisterCase {

    @DataProvider
    public Object[][] datas(){
        String[] cellNames  = {"CaseId","ApiId","Params","ExpecttedResponseData"};
        Object[][] datas = CaseUtil.getCaseDatasByAPiId("1",cellNames);
        return datas;
    }


}


