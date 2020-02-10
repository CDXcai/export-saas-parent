package com.itheima.more;

import com.itheima.domain.vo.ContractProductVo;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;


/**
 * 如何执行解析器
 */
public class SheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
    /**
     * 在解析数据行前执行的方法  每一行都会执行
     *  每一行数据其实就是 ContractProductVo
     *  解析完每一行数据 构建一个ContractProductVo
     * @param rowNum : 表示解析的行数
     */
    private  ContractProductVo vo = null;
    @Override
    public void startRow(int rowNum) {
        if(rowNum>2){ //跳过前两行
            vo = new ContractProductVo();
        }
    }
    /**
     * 在解析数据行后执行的方法  每一行都会执行
     * @param rowNum
     */
    @Override
    public void endRow(int rowNum) {
        System.out.println(vo);
    }

    /**
     * 每一行执行的方法, 每一行会执行八次(取决于有多少个单元格)这个方法
     * @param cellReference     单元格的列 ,哪一个单元格的多少格  A1 A2 A3  B1 B2 B3  B13 C156....
     * @param value    单元格的值
     * @param comment           注释(没用)
     */
    @Override
    public void cell(String cellReference, String value, XSSFComment comment) {
        //从第三行开始解析数据即可

        if(vo != null){ //肯定不是前两行
            String substring = cellReference.substring(0, 1);//截取第一个列名
            switch (substring){
                case "B"://客户
                    vo.setCustomName( value );
                    break;
                case "C"://合同号
                    vo.setContractNo( value );
                    break;
                case "D"://货号...
                    vo.setProductNo( value );
                    break;
                    ///....
            }
        }
    }
}
