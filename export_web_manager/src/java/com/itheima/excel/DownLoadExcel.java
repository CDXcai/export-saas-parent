package com.itheima.excel;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

public class DownLoadExcel {
    /**
     * 操作excel
     * 1.创建excel
     * 2.填充excel的数据
     * 3.下载excel 导出到本地
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //1.创建excel  使用WorkBook对象 工作薄对象
        Workbook wb = new XSSFWorkbook();

        //2.填充数据
        //2.1 创建表
        Sheet sheet = wb.createSheet("abc");
        //2.2 确定行(row)  使用的是索引
        Row row = sheet.createRow(1);
        //2.3 确定列(column , cell 单元格)  使用的索引
        Cell cell = row.createCell(1);

        //  wb 工作薄  sheet  表  row 行  cell 列
        //--------------样式-了解
        //设置行高
        //row.setHeight((short)(46 * 20));  底层除以20
        row.setHeightInPoints(46); //自动乘以20
        //sheet.setDefaultRowHeight(); 设置所有行的高度
        //设置宽度
        //参数1: 哪一列  参数2: 宽度
        sheet.setColumnWidth(1 , 24 * 256); //底层除了256

        //居中
        //创建样式对象  align = "center"
        CellStyle cellStyle = wb.createCellStyle();
        //上下和左右 控制
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置字体
        Font font = wb.createFont();
        font.setBold(true);//加粗
        font.setFontName("楷体");//字体样式
        font.setFontHeightInPoints((short)30);
        cellStyle.setFont(font);

        //操作边框 没有一次性全设置的api
        cellStyle.setBorderLeft(BorderStyle.THIN);//THIN实线
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        //设置样式
        cell.setCellStyle(cellStyle);


        //--------------样式-了解

        //2.4 写入数据
        cell.setCellValue("传智播客");



        //3.下载到本地
        FileOutputStream os = new FileOutputStream("D:/test.xlsx");
        //将文件写入到输出流中
        wb.write(os);
        os.close();
    }



    /*
    * /**
     * 操作excel
     * 1.创建excel
     * 2.填充excel的数据
     * 3.下载excel 导出到本地
     * @param args

    public static void main(String[] args) throws Exception {
        //1.创建excel  使用WorkBook对象 工作薄对象
        Workbook wb = new XSSFWorkbook();

        //2.填充数据
        //2.1 创建表
        Sheet sheet = wb.createSheet("abc");
        //2.2 确定行(row)  使用的是索引
        Row row = sheet.createRow(1);
        //2.3 确定列(column , cell 单元格)  使用的索引
        Cell cell = row.createCell(1);
        //2.3 写入数据
        cell.setCellValue("传智播客");


        //3.下载到本地
        FileOutputStream os = new FileOutputStream("D:/test.xlsx");
        //将文件写入到输出流中
        wb.write(os);
        os.close();
    }
    *
    * */
}
