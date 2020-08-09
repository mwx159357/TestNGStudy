package com.lemon.api.auto.variable;

import com.lemon.api.auto.util.JDBCUtil;

import java.util.Map;

public class MobileGenerator {
    /**
     * 生成用于注册的手机号
     * @return
     */
    public String generateToBeRegisteredMobile(){
        String sql =  "select concat(max(mobilephone)+1,'') as toBeRegisterMobile from member";
        Map<String,Object> columnLabelAndValues = JDBCUtil.query(sql);
        return columnLabelAndValues.get("toBeRegisterMobile").toString();

    }

    /**
     * 生成系统中还未注册的手机号
     * @return
     */
    public String generateSystemNotExistedMobile(){

        String sql  =  "select concat(max(mobilephone)+2,'') as toBeRegisterMobile from member";
        Map<String,Object> columnLabelAndValues = JDBCUtil.query(sql);
        return columnLabelAndValues.get("systemNotExistMobile").toString();

    }
}
