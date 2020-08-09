package com.lemon.api.auto.util;

import com.lemon.api.auto.pojo.Case;
import com.lemon.api.auto.pojo.Rest;
import com.lemon.api.auto.pojo.Variable;
import com.lemon.api.auto.pojo.WriteBackData;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.collections4.IterableUtils.isEmpty;

public class ExcelUtil {

    //行号和用例编号对应的集合
    public static Map<String,Integer> rowIdentifierRownumMapping = new HashMap<String, Integer>();
    //列名和列号对应的集合
    public static Map<String,Integer> cellNameCellnumMapping = new HashMap<String, Integer>();
    //回写集合对象
    public static List<WriteBackData> writeBackDatas = new ArrayList<WriteBackData>();
    //将数据写入内存
    static {
        loadRownumAndCellnumMapping(PropertiesUtil.getExcelPath(),"用例");
    }

    /**获取caseid以及它对应的行索引
     *获取cellname 以及他对应的列索引
     * @param excelPath
     * @param sheetName
     */
    public static  void loadRownumAndCellnumMapping(String excelPath,String sheetName) {
        InputStream inputStream = null;

        try {
            inputStream  =new FileInputStream(new File(excelPath));
            Workbook  workbook =WorkbookFactory.create(inputStream);
            Sheet sheet  =workbook.getSheet(sheetName);
            Row titleRow =sheet.getRow(0);
            if(titleRow!=null&&!isEmpty(titleRow)){
                int lastCellnum  = titleRow.getLastCellNum();
                for (int i = 0; i < lastCellnum; i++) {
                    Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String title  = cell.getStringCellValue();
                    title  = title.substring(0,title.indexOf("("));
                    int cellnum  = cell.getAddress().getColumn();
                    cellNameCellnumMapping.put(title,cellnum);
                }
            }
            //从第二行开始，获取所有的数据行
            int lastRownum  = sheet.getLastRowNum();
            for (int i = 0; i < lastRownum; i++) {
                Row dataRow = sheet.getRow(i);
                Cell firstCellOfRow =dataRow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String caseId  =  firstCellOfRow.getStringCellValue();
                int rownum = dataRow.getRowNum();
                rowIdentifierRownumMapping.put(caseId,rownum);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

        }

    }



    /**
     *
     * @param Excelpath  表格路径
     * @param startRow 开始行号
     * @param endRow  结束行号
     * @param startCell  开始列号
     * @param endCell 结束列号
     * @return
     */

    public static Object[][] datasDemo(String Excelpath,int startRow,int endRow,int startCell,int endCell){

        Object [] [] datas = new Object[endRow-startRow+1][endCell-startCell+1];
        try {
            Workbook workbook = WorkbookFactory.create(new File(Excelpath));
            Sheet sheet  = workbook.getSheet("用例");
            for (int i = startRow; i <=endRow ; i++) {
                Row row = sheet.getRow(i-1);
                row.getCell(5);
                for (int j = startCell; j <=endCell ; j++) {
                    Cell cell =row.getCell(j-1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    datas[i-startRow][j-startCell] =value;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }


    public static void writeBackData(String excelPath,String caseId,String sheetName,String cellName,String result){
        System.out.println("读写excel");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try{
            inputStream  = new FileInputStream(new File(excelPath));
            Workbook workbook =  WorkbookFactory.create(inputStream);
            Sheet sheet  =  workbook.getSheet(sheetName);
            int rowNum  = rowIdentifierRownumMapping.get(caseId);
            Row row = sheet.getRow(rowNum);
            int cellNum = cellNameCellnumMapping.get(cellName);

            Cell cell = row.getCell(cellNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(result);
            outputStream= new FileOutputStream(new File(excelPath));
            workbook.write(outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(outputStream!= null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
      * @param Excelpath 表格路径
     * @param Rows 行号数组
     * @param Cells 列号数组
     * @return 二维数组
     */
    public static Object[][] datas(String Excelpath,int[] Rows,int[] Cells){

        Object [] [] datas = new Object[Rows.length][Cells.length];
        try {
            Workbook workbook = WorkbookFactory.create(new File(Excelpath));
            Sheet sheet  = workbook.getSheet("用例");
            for (int i = 0; i <=Rows.length ; i++) {
                Row row = sheet.getRow(Rows[i]-1);
                for (int j = 0; j <=Cells.length ; j++) {
                    Cell cell =row.getCell(Cells[j]-1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    System.out.println(value);
                    datas[i][j] =value;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }




    /**
     * 批量回写数据的方法
     * @param excelPath
     */
    public  static void batchWriteBackDatas(String excelPath){
        InputStream inputStream = null;
        OutputStream outputStream  =null;
        try {
            inputStream = new FileInputStream(new File(excelPath));
            Workbook workbook  = WorkbookFactory.create(inputStream);
            for (WriteBackData writeBackData:writeBackDatas) {
                String sheetName = writeBackData.getSheetName();
                Sheet sheet  =  workbook.getSheet(sheetName);
                String rowIdentifier  =  writeBackData.getrowIdentifier();
                int rownum  =rowIdentifierRownumMapping.get(rowIdentifier);
                Row row =sheet.getRow(rownum);
                String cellName  =writeBackData.getCellName();
                int cellnum = cellNameCellnumMapping.get(cellName);
                Cell cell  = row.getCell(cellnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String result  =  writeBackData.getResult();
                cell.setCellValue(result);

            }
            outputStream =  new FileOutputStream(new File(excelPath));
            workbook.write(outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            try {
                if(outputStream!=null){
                    outputStream.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    public static <T> List<T>   load(String excepath,String sheetName,Class<T> clazz){
        List<T> list  = new ArrayList<T>();
        //创建WorkBook对象
        try{
            Workbook workbook = WorkbookFactory.create(new File(excepath));
            Sheet sheet = workbook.getSheet(sheetName);
            //获取第一行
            Row titleRow  = sheet.getRow(0);
            //获取最后一列的列号
            int lastCellNum = titleRow.getLastCellNum();
            String[] fields = new String[lastCellNum];
            //循环处理每一列，列出每一列里面的字段名，保存到数组
            for (int i = 0; i <lastCellNum ; i++) {
                //设置列索引获取对应的列
                Cell cell= titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                //设置列的类型为字符串;
                cell.setCellType(CellType.STRING);
                //获取列的值
                String title = cell.getStringCellValue();
                title =title.substring(0,title.indexOf("("));
                fields[i] = title;
            }
            int lastRowIndex = sheet.getLastRowNum();
            //循环处理每一个数据行
            for (int i = 1; i <lastRowIndex ; i++) {
                T obj = clazz.newInstance();
                //拿到一个数据行
                Row dataRow =sheet.getRow(i);
                if (dataRow==null  && isEmpty(dataRow)){
                    continue;
                }
                for (int j = 0; j <lastCellNum ; j++) {
                    Cell  cell = dataRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value  = cell.getStringCellValue();
                    //获取要反射的方法名
                    String methodName = "set"+fields[j];
                    Method method = clazz.getMethod(methodName,String.class);
                     //完成反射调用
                    method.invoke(obj,value);
                }
                list.add(obj);
//                if(obj instanceof Case){// instancof 是判断对象类型的语法
//                    Case cs =(Case) obj;
//                    CaseUtil.cases.add(cs);
//                }else if(obj instanceof  Rest){
//                    Rest rest = (Rest) obj;
//                    RestUtil.rests.add(rest);
//                }else if(obj instanceof Variable){
//                    Variable variable = (Variable) obj;
//                    VariableUtil.variables.add(variable);
//                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
