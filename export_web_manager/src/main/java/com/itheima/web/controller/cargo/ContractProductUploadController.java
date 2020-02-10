package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domain.cargo.ContractProduct;
import com.itheima.service.cargo.ContractProductService;
import com.itheima.web.controller.base.BaseController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductUploadController extends BaseController {
    /**
     * 跳转上传页面
     * @return
     */
    @RequestMapping("/toImport")
    public String toImport(String contractId){
        //合同的id 隐藏在用户上传的页面中的
        request.setAttribute("contractId" , contractId);
        return "cargo/product/product-import";
    }

    @Reference
    private ContractProductService contractProductService;
    /**
     * 上传货物的方法
     * @param contractId 购销合同di
     * @param  file     excel的对象
     * @return
     */
    @RequestMapping("/import")
    public String importExcel(String contractId , MultipartFile file) throws Exception {
        //1.解析excel文件 -构建一堆的 货物对象 ContractProduct
        List<ContractProduct> list = new ArrayList<>();

        //--------------------------------解析excel start
        //1.创建工作薄对象
        Workbook wb = new XSSFWorkbook(file.getInputStream()); //将内容转换成流数据

        //2.获得表
        Sheet sheet = wb.getSheetAt(0);
        //3.获得行的索引
        int lastRowNum = sheet.getLastRowNum(); //索引开始
        Row row = null;

        //准备数组
        Object [] data = new Object[10];
        for(int i = 1 ; i <= lastRowNum ; i ++){
            //4. 获得行
            row = sheet.getRow(i);

            //5.获得单元格
            //contractProduct.setFactoryName( row.getCell(1).getStringCellValue() );
            //准备一个数组 将excel的每一行数据读取到数组中 , 每一个数组 相当于每一行 每一行相当于一个对象
            short lastCellNum = row.getLastCellNum(); //lastCellNum 等于 10
            for(int j = 1 ; j < lastCellNum ; j ++ ){//循环从1开始 数组的后九个有值 但是第一个没有值
                //获得每一行的每一个单元格
                Cell cell = row.getCell(j);
                //获得每一个单元格的值
                Object cellVal = this.getCellVal(cell);
                data[j] = cellVal; //赋值给数组
                //System.out.println(Arrays.toString(data));
            }

            //定义货物对象
            ContractProduct contractProduct = new ContractProduct(data , super.companyId , super.companyName , contractId );
            //System.out.println(contractProduct);
            list.add(contractProduct);
        }



        //--------------------------------解析excel end
        //2.保存数据 service必须增加一个方法(service层控制事务)
        contractProductService.saveList(list);

        return "cargo/product/product-import";
    }



    /**
     * 获得数据 必须根据指定的类型 通过指定的api获得 否则报错
     * POI不直接提供工具类
     * @param cell
     * @return
     */
    public  Object getCellVal(Cell cell){
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
