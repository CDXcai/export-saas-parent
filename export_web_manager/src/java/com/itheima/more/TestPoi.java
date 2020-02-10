package com.itheima.more;

/**
 * <Description>
 *
 * @author hzb@itcast.cn
 * @version 1.0
 * @taskId: <br>
 * @createDate 2020/01/15 10:14
 * @see com.itheima.more
 */
public class TestPoi {
    public static void main(String[] args) throws Exception {
        ExcelParse excelParse = new ExcelParse();
        excelParse.parse("D:/出货表.xlsx");
    }
}
