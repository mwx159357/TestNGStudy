package com.lemon.api.auto.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties properties= new Properties();
    static{
        try {
            InputStream inputStream = new FileInputStream(new File("src/test/resources/config.properties"));
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 获取用例文件路径
     * @return
     */
    public static  String getExcelPath(){
        return null;


    }
}
