package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.utils.DownloadUtil;
import com.itheima.domain.vo.ContractProductVo;
import com.itheima.service.cargo.ContractService;
import com.itheima.web.controller.base.BaseController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
@RequestMapping("/cargo/contract")
public class ContractPrintController  extends BaseController {

    /**
     * 出货表打印
     * @return
     */
    @RequestMapping("/print")
    public String print(){
        return "cargo/print/contract-print";
    }

    @Reference
    private ContractService contractService;


    /**
     * 百万级数据打印 - 模版打印(不能使用)
     * 百万级数据打印 - 原生的poi打印(手动创建方式)
     * @param inputDate
     * @throws Exception
     */
    @RequestMapping("/printExcel")
    public void printExcel(String inputDate) throws Exception {
        //1.根据日期查询数据
        List<ContractProductVo> list = contractService.findByShipTime(inputDate , super.companyId);
        //System.out.println(list);
        //2.根据已经查询到的数据构建excel
        //2.1 工作薄对象
        Workbook wb = new SXSSFWorkbook();


        //--------------------------------------------------excel数据填充 start
        //2.2 创建表
        Sheet sheet = wb.createSheet();
        //2.3 创建大标题
        Row row = sheet.createRow(0);
        //row.getLastCellNum();//从列开始的 从1开始
        //row.createCell();从0开始
        //合并单元格操作  Merge 合并   Region 区域
        //public CellRangeAddress(int firstRow, int lastRow, int firstCol 从索引开始 , int lastCol 索引结束) {
        sheet.addMergedRegion( new CellRangeAddress(0 , 0  , 1 , 8));

        Cell cell = row.createCell(1);//第一列

        //inputDate 2015-01
        inputDate = inputDate.replace("-","年");
        cell.setCellValue(inputDate + "月份出货表");
        sheet.setDefaultRowHeightInPoints(36);//设置默认高度
        //2.4 创建小标题
        //客户	合同号	货号	数量	工厂	工厂交期	船期	贸易条款
        String [] headerNames = {"","客户","合同号","货号","数量","工厂","工厂交期","船期","贸易条款"};
        //创建小标题 行
        row = sheet.createRow(1);
        for(int i = 1 ; i < headerNames.length ; i ++){
            //创建单元格
            cell = row.createCell(i);
            cell.setCellValue( headerNames[i] );//赋值标题
            CellStyle title = title(wb);//设置样式
            cell.setCellStyle(title);
        }

        //2.5 构建数据 (重点)
        //每一个对象 相当于excel表格中的一行数据
        int index = 2; //定义行号
        for (ContractProductVo productVo : list) {
            for(int i = 0 ; i < 3000; i ++){
                row = sheet.createRow(index);//创建行

                // 合同表: 客户 customName
                cell = row.createCell(1);
                cell.setCellValue( productVo.getCustomName() );
                // 合同表: 合同号 contractNo
                cell = row.createCell(2);
                cell.setCellValue( productVo.getContractNo() );
                // 货物表: 货号 productNo
                cell = row.createCell(3);
                cell.setCellValue( productVo.getProductNo());
                // 货物表: 数量 cnumber
                cell = row.createCell(4);
                cell.setCellValue( productVo.getCnumber() );
                // 货物表: 工厂 factoryName
                cell = row.createCell(5);
                cell.setCellValue( productVo.getFactoryName() );
                // 合同表: 工厂交期  deliveryPeriod
                cell = row.createCell(6);
                cell.setCellValue( productVo.getDeliveryPeriod());
                // 合同表: 船期 shipTime
                cell = row.createCell(7);
                cell.setCellValue( productVo.getShipTime() );
                //合同表: 贸易条款 tradeTerms
                cell = row.createCell(8);
                cell.setCellValue( productVo.getTradeTerms() );
                index++;//行号递增
            }
        }






        //--------------------------------------------------excel数据填充 end

        //3.下载 http协议传输
        DownloadUtil downloadUtil = new DownloadUtil();
        //文件的输出流 -含有所有的数据
        ByteArrayOutputStream  os = new ByteArrayOutputStream();
        wb.write(os);//将文件写入输出流中
        //参数1: 下载的输出流
        //参数2: response对象
        //参数3: 下载的文件名称
        downloadUtil.download( os ,response , "出货表.xlsx" );

    }


    /**
     * 模版打印:
     * 1.准备数据
     * 2.读取excel
     * 3.填充数据
     * 4.下载
     * 下载excel -> 返回值必须是void的 -> 字节流(下载)和字符流(响应页面)
     *
     */
    /*@RequestMapping("/printExcel")
    public void printExcel(String inputDate) throws Exception {
        //1.根据日期查询数据
        List<ContractProductVo> list = contractService.findByShipTime(inputDate , super.companyId);


        //--------------------------------------------------excel数据填充 start
        //读取本地的excel文件 填充数据
        //读取项目资源两种方式:
        // 1.读取resource下的资源 :  使用的是类加载器 当前类.class.getClassLoader().getResourceAsSteam()
        // 2.读取webapp下的资源 : 项目的根对象.getResourceAsStream()
        InputStream is = session.getServletContext().getResourceAsStream("/make/xlsprint/tOUTPRODUCT.xlsx");
        //System.out.println(is);

        //2.根据已经查询到的数据构建excel
        //2.1 获得工作薄对象
        Workbook wb = new XSSFWorkbook(is);
        //2.2 获得表
        Sheet sheet = wb.getSheetAt(0);
        //2.3 获得大标题
        Row row = sheet.getRow(0);

        Cell cell = row.getCell(1);//第一列

        //inputDate 2015-01
        inputDate = inputDate.replace("-","年");
        cell.setCellValue(inputDate + "月份出货表");


        //2.4 先将原来excel中的样式读取出来 重新赋值给每一行即可
        row = sheet.getRow(2);

        //获得每一个cell对象
        short lastCellNum = row.getLastCellNum(); //最大的列数
        //定义数组存放样式
        CellStyle[] styles = new CellStyle[lastCellNum];
        for(int i = 1 ; i < lastCellNum ; i ++){
            //获得每一个cell对象
            cell = row.getCell(i);
            //将每一个单元格样式赋值给数组中的具体位置
            styles[i] = cell.getCellStyle();
        }

        //2.5 构建数据 (重点)
        //每一个对象 相当于excel表格中的一行数据
        int index = 2; //定义行号
        for (ContractProductVo productVo : list) {
            row = sheet.createRow(index);//创建行  从第三行开始都是创建

            // 合同表: 客户 customName
            cell = row.createCell(1);
            cell.setCellStyle(styles[1]);
            cell.setCellValue( productVo.getCustomName() );
            // 合同表: 合同号 contractNo
            cell = row.createCell(2);
            cell.setCellStyle(styles[2]);
            cell.setCellValue( productVo.getContractNo() );
            // 货物表: 货号 productNo
            cell = row.createCell(3);
            cell.setCellStyle(styles[3]);
            cell.setCellValue( productVo.getProductNo());
            // 货物表: 数量 cnumber
            cell = row.createCell(4);
            cell.setCellStyle(styles[4]);
            cell.setCellValue( productVo.getCnumber() );
            // 货物表: 工厂 factoryName
            cell = row.createCell(5);
            cell.setCellStyle(styles[5]);
            cell.setCellValue( productVo.getFactoryName() );
            // 合同表: 工厂交期  deliveryPeriod
            cell = row.createCell(6);
            cell.setCellStyle(styles[6]);
            cell.setCellValue( productVo.getDeliveryPeriod());
            // 合同表: 船期 shipTime
            cell = row.createCell(7);
            cell.setCellStyle(styles[7]);
            cell.setCellValue( productVo.getShipTime() );
            //合同表: 贸易条款 tradeTerms
            cell = row.createCell(8);
            cell.setCellStyle(styles[8]);
            cell.setCellValue( productVo.getTradeTerms() );
            index++;//行号递增
        }






        //--------------------------------------------------excel数据填充 end

        //3.下载 http协议传输
        DownloadUtil downloadUtil = new DownloadUtil();
        //文件的输出流 -含有所有的数据
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);//将文件写入输出流中
        //参数1: 下载的输出
        //参数2: response对象
        //参数3: 下载的文件名称
        downloadUtil.download( os ,response , "出货表.xlsx" );

    }*/








   /**
     * 出货表打印  原始 方式
     * 下载excel -> 返回值必须是void的 -> 字节流(下载)和字符流(响应页面)
     *//*
    @RequestMapping("/printExcel")
    public void printExcel(String inputDate) throws Exception {
        //1.根据日期查询数据
        List<ContractProductVo> list = contractService.findByShipTime(inputDate , super.companyId);
        //System.out.println(list);
        //2.根据已经查询到的数据构建excel
        //2.1 工作薄对象
        Workbook wb = new XSSFWorkbook();


        //--------------------------------------------------excel数据填充 start
        //2.2 创建表
        Sheet sheet = wb.createSheet();
        //2.3 创建大标题
        Row row = sheet.createRow(0);
        //row.getLastCellNum();//从列开始的 从1开始
        //row.createCell();从0开始
        //合并单元格操作  Merge 合并   Region 区域
        //public CellRangeAddress(int firstRow, int lastRow, int firstCol 从索引开始 , int lastCol 索引结束) {
        sheet.addMergedRegion( new CellRangeAddress(0 , 0  , 1 , 8));

        Cell cell = row.createCell(1);//第一列
        //设置标题的基本样式
        CellStyle cellStyle = bigTitle(wb);
        cell.setCellStyle( cellStyle );

        //inputDate 2015-01
        inputDate = inputDate.replace("-","年");
        cell.setCellValue(inputDate + "月份出货表");
        //设置宽度
        sheet.setColumnWidth(1 , 26 * 256);
        sheet.setColumnWidth(2 , 12 * 256);
        sheet.setColumnWidth(3 , 30 * 256);
        sheet.setColumnWidth(4 , 12 * 256);
        sheet.setColumnWidth(5 , 15 * 256);
        sheet.setColumnWidth(6 , 12 * 256);
        sheet.setColumnWidth(7 , 12 * 256);
        sheet.setColumnWidth(8 , 12 * 256);
        sheet.setDefaultRowHeightInPoints(36);//设置默认高度
        //2.4 创建小标题
        //客户	合同号	货号	数量	工厂	工厂交期	船期	贸易条款
        String [] headerNames = {"","客户","合同号","货号","数量","工厂","工厂交期","船期","贸易条款"};
        //创建小标题 行
        row = sheet.createRow(1);
        for(int i = 1 ; i < headerNames.length ; i ++){
            //创建单元格
            cell = row.createCell(i);
            cell.setCellValue( headerNames[i] );//赋值标题
            CellStyle title = title(wb);//设置样式
            cell.setCellStyle(title);
        }

        //2.5 构建数据 (重点)
        //每一个对象 相当于excel表格中的一行数据
        CellStyle style =null;
        int index = 2; //定义行号
        for (ContractProductVo productVo : list) {
            row = sheet.createRow(index);//创建行

            // 合同表: 客户 customName
            cell = row.createCell(1);
            style = text(wb);
            cell.setCellStyle(style);
            cell.setCellValue( productVo.getCustomName() );
            // 合同表: 合同号 contractNo
            cell = row.createCell(2);
            style = text(wb);
            cell.setCellStyle(style);
            cell.setCellValue( productVo.getContractNo() );
            // 货物表: 货号 productNo
            cell = row.createCell(3);
            style = text(wb);
            cell.setCellStyle(style);
            cell.setCellValue( productVo.getProductNo());
            // 货物表: 数量 cnumber
            cell = row.createCell(4);
            style = text(wb);
            cell.setCellStyle(style);
            cell.setCellValue( productVo.getCnumber() );
            // 货物表: 工厂 factoryName
            cell = row.createCell(5);
            style = text(wb);
            cell.setCellStyle(style);
            cell.setCellValue( productVo.getFactoryName() );
            // 合同表: 工厂交期  deliveryPeriod
            cell = row.createCell(6);
            style = text(wb);
            cell.setCellStyle(style);
            cell.setCellValue( productVo.getDeliveryPeriod());
            // 合同表: 船期 shipTime
            cell = row.createCell(7);
            style = text(wb);
            cell.setCellStyle(style);
            cell.setCellValue( productVo.getShipTime() );
            //合同表: 贸易条款 tradeTerms
            cell = row.createCell(8);
            style = text(wb);
            cell.setCellStyle(style);
            cell.setCellValue( productVo.getTradeTerms() );
            index++;//行号递增
        }






        //--------------------------------------------------excel数据填充 end

        //3.下载 http协议传输
        DownloadUtil downloadUtil = new DownloadUtil();
        //文件的输出流 -含有所有的数据
        ByteArrayOutputStream  os = new ByteArrayOutputStream();
        wb.write(os);//将文件写入输出流中
        //参数1: 下载的输出流
        //参数2: response对象
        //参数3: 下载的文件名称
        downloadUtil.download( os ,response , "出货表.xlsx" );

    }*/










    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)20);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线

        return style;
    }
}
