package com.lemon.api.auto.util;

import com.lemon.api.auto.pojo.Case;

import java.util.ArrayList;
import java.util.List;

public class CaseUtil {
    /**
     * 保存所有的用例对象
     */
    public static List<Case> cases =  new ArrayList<Case>();
    static {
        List<Case> list = ExcelUtil.load(PropertiesUtil.getExcelPath(),"用例",Case.class);
        cases.addAll(list);
    }

    /**
     * 根据接口编号拿对应接口的测试数据
     * @param apiId 指定接口编号
     * @param cellNames 要获取的数据对用的列名
     * @return
     */
    public static Object[][] getCaseDatasByAPiId(String apiId,String[] cellNames){
        Class<Case> clazz = Case.class;
        //保存指定接口编号的case对象的集合
        ArrayList<Case>  cslist = new ArrayList<Case>();
        //通过循环找出指定接口编号对应的这些用例数据
        for (Case cs :cases) {
            if(cs.getApiId().equals("apiID")){
                cslist.add(cs);
            }
        }
        Object[][] datas = new Object[cslist.size()][cellNames.length];
        for (int i = 0; i <cslist.size() ; i++) {
            Case cs  = cslist.get(i);
            for (int j = 0; j <cellNames.length ; j++) {


            }

        }
        return datas;
    }
}
