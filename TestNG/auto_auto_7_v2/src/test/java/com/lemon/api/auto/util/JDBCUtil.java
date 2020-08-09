package com.lemon.api.auto.util;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JDBCUtil {

    public static Properties properties  = new Properties();
    static{
        System.out.println("静态代码块解析properties数据");
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(new File("src/test/resources/jdbc.properties"));
            properties.load(inputStream);
        } catch (Exception e) {
            System.out.println("发生了异常");
            e.printStackTrace();
        }

    }

    public static Map<String,Object> query(String sql,Object ... values){
        Map<String,Object> columnLabelAndValues = null;
        try {
            //1、根据连接信息，获得数据库连接（连上数据库）
            Connection connection  = getConnection();
            //2、获取PreparedStatement对象（此类型的对象有提供数据库操作方法）
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //3、设置条件字段的值（实时绑定）
            for (int i = 0; i <values.length ; i++) {
                preparedStatement.setObject(i+1,values[i]); }
            //4、调用查询方法，执行查询，返回ResultSet(结果集)
            ResultSet resultSet  = preparedStatement.executeQuery();
            //获取查询相关的信息
            ResultSetMetaData metaData = resultSet.getMetaData();
            //得到查询字段的数目
            int columnCount  = metaData.getColumnCount();
            //5、从结果集取查询数据
            columnLabelAndValues = new HashMap<String, Object>();
            while(resultSet.next()){
                for (int i = 0; i < columnCount; i++) {
                    String columLable  =metaData.getColumnLabel(i);
                    Object columValue  =  resultSet.getObject(columLable);
                    columnLabelAndValues.put(columLable,columValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnLabelAndValues;
    }


    /**
     * 获取数据库的连接
     * @return
     * @throws SQLException
     */
    public static  Connection getConnection() throws SQLException {
        //从properties取url
        String url = properties.getProperty("jdbc.url");
        //从properties 取user
        String user = properties.getProperty("jdbc.username");
        //从properties取password
        String password  = properties.getProperty("jdbc.password");
        Connection connection = DriverManager.getConnection(url,user,password);
        return connection;

    }



}
