package com.itheima.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <Description>
 *
 * @author hzb@itcast.cn
 * @version 1.0
 * @taskId: <br>
 * @createDate 2020/01/14 11:45
 * @see com.itheima.excel
 */
public class UploadExcel {
    /**
     * 1.上传文件
     * 2.解析文件
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //1.创建工作薄对象
        Workbook wb = new XSSFWorkbook("D:/demo.xlsx");

        //2.解析 getSheetAt获得索引的表
        Sheet sheet = wb.getSheetAt(0);
        //获得最后一行的索引
        int lastRowNum = sheet.getLastRowNum();
        for(int i = 0 ; i <= lastRowNum ; i ++){
            //获得每一行
            Row row = sheet.getRow(i);
            //获得最后一列数 (不是索引)
            short lastCellNum = row.getLastCellNum();
            //System.out.println(lastCellNum);
            for(int j = 0 ; j < lastCellNum ;  j ++) {
                //获得每一列 如果列上没有内容 获得的是空对象
                Cell cell = row.getCell(j);
                if(cell !=null){
                    //获得数据 必须根据指定的类型 通过指定的api获得 否则报错
                    //System.out.println(cell.getStringCellValue());
                    Object o = getCellVal(cell);
                    System.out.println(o);
                }
            }
        }
    }

    /**
     * 获得数据 必须根据指定的类型 通过指定的api获得 否则报错
     * POI不直接提供工具类
     * @param cell
     * @return
     */
    public static  Object getCellVal(Cell cell){
        //1.获得cell对象的类型
        CellType cellType = cell.getCellType();
        Object o = null;
        //2.根据指定类型获得数据
        switch (cellType){
            case BOOLEAN:
                o = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                //在excel中 日期是数字类型  必然会进入NUMERIC判断 但是如果要获得日期 必须使用指定api
                //判断是日期类型还是数字类型  POI提供工具类
                boolean flag = DateUtil.isCellDateFormatted(cell); //是不是日期类型
                if(flag){ //日期
                    o = cell.getDateCellValue();
                }else{//数字
                    o=cell.getNumericCellValue();
                }
                break;
            case STRING:
                o=cell.getStringCellValue();
                break;
             default:
                    break;
        }
        return o;
    }
}
