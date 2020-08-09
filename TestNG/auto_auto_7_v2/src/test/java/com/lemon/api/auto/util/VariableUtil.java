package com.lemon.api.auto.util;

import com.lemon.api.auto.pojo.Variable;
import com.lemon.api.auto.pojo.WriteBackData;

import java.lang.reflect.Method;
import java.util.*;

/**变量工具类
 *
 */
public class VariableUtil {
    public  static  Map<String,String> variableNameAndValuesMap  = new HashMap<String, String>();
    public static  List<Variable> variables = new ArrayList<Variable>();

    static {
        //第一步加载表单里的数据 一次将每行封装成对象，然后统一添加到集合
        List<Variable> list = ExcelUtil.load(PropertiesUtil.getExcelPath(),"变量",Variable.class);
        variables.addAll(list);
        //将变量及变量的值加入集合
        loadVariablesToMap();
        ExcelUtil.loadRownumAndCellnumMapping(PropertiesUtil.getExcelPath(),"变量");
   }

    private static void loadVariablesToMap() {
        for(Variable variable:variables){
            //获取到变量名
            String variableName = variable.getName();
            //获取到变量的value值
            String variableValue = variable.getValue();
            //判断是否走反射
            if(variableValue==null||variableValue.trim().length()==0){
                //反射类
                String reflectClass = variable.getReflectClass();
                //反射方法
                String reflectMethod = variable.getReflectMethod();
                try {
                    //通过反射获取类型字节码
                    Class clazz = Class.forName(reflectClass);
                    //通过反射创件对象
                    Object object = clazz.newInstance();
                    //获取要反射调用的方法对象method
                    Method method = clazz.getMethod(reflectMethod);
                    //反射调用方法，获取到方法的返回值
                    variableValue = (String)method.invoke(object);
                    //保存要回写的数据到集合
                    ExcelUtil.writeBackDatas.add(new WriteBackData(variableValue,"变量",variableName,"ReflectValue"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            variableNameAndValuesMap.put(variableName,variableValue);


        }
    }


    /**
     * 遍历变量集合，将变量名和对应的变量值保存
     * @param parameter
     * @return
     */
    public static  String replaceVariables(String parameter ){
        Set<String> variableNames = variableNameAndValuesMap.keySet();
        for (String variableName:variableNames
             ) {
            if(parameter.contains(variableName)){
                parameter  = parameter.replace(variableName,variableNameAndValuesMap.get(variableName));
            }

        }

        return parameter;

    }
}
