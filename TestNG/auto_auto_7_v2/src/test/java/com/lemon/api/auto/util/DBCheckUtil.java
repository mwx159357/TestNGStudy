package com.lemon.api.auto.util;

import com.alibaba.fastjson.JSONObject;
import com.lemon.api.auto.pojo.DBChecker;
import com.lemon.api.auto.pojo.DBQueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBCheckUtil {

    /**
     * 根据脚本执行查询并返回查询结果
     * @param sql 需要执行的查询语句
     * @return
     */
    public static String doQuery(String sql){
        //将脚本字符串封装成对象
        List<DBChecker> dbcheckers  = JSONObject.parseArray(sql, DBChecker.class);
        List<DBQueryResult> dbQueryResults = new ArrayList<DBQueryResult>();
        for (DBChecker dbchecker:dbcheckers) {
            //拿到sql的编号
            String no =dbchecker.getNo();
            String stringSql = dbchecker.getSql();
            //执行查询，获取到结果
            Map<String,Object> columnLabelAndValues = JDBCUtil.query(sql);
            DBQueryResult dbQueryResult = new DBQueryResult();
            dbQueryResult.setNo(no);
            dbQueryResult.setColumnLabelAndValues(columnLabelAndValues);
            dbQueryResults.add(dbQueryResult);

        }
        return null;
    }

    public static void main(String[] args) {
        String validataSql  = "[{\"no\": 1,\"sql\": \"selct count(*) from qx_user where uid ='2207270 ';\"}, {\"no\": 2,\"sql\": \"selct count(*) from qx_user where uid = '2207271';\"}]";
        List<DBChecker> decheckers  = JSONObject.parseArray(validataSql, DBChecker.class);
        for (DBChecker  dechecker:decheckers) {
            System.out.println(dechecker);
        }
    }
}
