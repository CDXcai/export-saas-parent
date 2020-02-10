package com.itheima.web.controller.export;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.common.utils.BeanMapUtils;
import com.itheima.domain.export.Export;
import com.itheima.domain.export.ExportProduct;
import com.itheima.domain.export.ExportProductExample;
import com.itheima.domain.system.User;
import com.itheima.service.export.ExportProductService;
import com.itheima.service.export.ExportService;
import com.itheima.web.controller.base.BaseController;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

@Controller
@RequestMapping("/cargo/export")
public class ExportPDFController extends BaseController {
    @Reference
    private ExportService exportService;
    @Reference
    private ExportProductService exportProductService;
    /**
     * 下载pdf
     */
    @RequestMapping("/exportPdf")
    public void exportPdf(String id){
        try {
            //1.有数据
            //1.1查询当前报运单的数据
            Export export = exportService.findById(id);
            //1.2 查询当前报运单下货物的数据
            ExportProductExample example = new ExportProductExample();
            ExportProductExample.Criteria criteria = example.createCriteria();
            criteria.andCompanyIdEqualTo(super.companyId);
            criteria.andExportIdEqualTo(id);
            List<ExportProduct> exportProductList = exportProductService.findAll(example);

            System.out.println(export);
            System.out.println(exportProductList);
            //将对象数据转换成map数据
            Map<String, Object> map = BeanMapUtils.beanToMap(export);

            //2.读取文件
            InputStream is = session.getServletContext().getResourceAsStream("/jasper/export.jasper");
            //将list类型   转换成 jasper所需要的 JRDataSource类型
            //JRBeanCollectionDataSource
            JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(exportProductList);
            //3.文件+数据部分
            //JRDataSource dataSource  我们的数据是 ArrayList
            JasperPrint jasperPrint = JasperFillManager.fillReport(is, map, beanDataSource);
            //4.下载 输出到浏览器控制台 修改DownLoadUtil 把文件下载
            JasperExportManager.exportReportToPdfStream(jasperPrint , response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 下载pdf
     */
    @RequestMapping("/exportPdf7")
    public void exportPdf7(String id){
        try {
            //1.有数据
            //2.读取文件
            InputStream is = session.getServletContext().getResourceAsStream("/jasper/test06.jasper");

            Map<String , Object> map = new HashMap<String , Object>();

            Random random = new Random();
            //查询数据库
            List<Map> list = new ArrayList();

            for(int i =0 ; i < 6 ; i ++){
                Map<String , Object > dataMap = new HashMap<>();
                dataMap.put("title" , "标题" + i);
                dataMap.put("value" ,random.nextInt(100) );
                list.add(dataMap);
            }


            //将list类型   转换成 jasper所需要的 JRDataSource类型
            //JRBeanCollectionDataSource
            JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(list);
            //3.文件+数据部分
            //JRDataSource dataSource  我们的数据是 ArrayList
            JasperPrint jasperPrint = JasperFillManager.fillReport(is, map, beanDataSource);
            //4.下载 输出到浏览器控制台 修改DownLoadUtil 把文件下载
            JasperExportManager.exportReportToPdfStream(jasperPrint , response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 下载pdf
     */
    @RequestMapping("/exportPdf6")
    public void exportPdf6(String id){
        try {
            //1.有数据
            //2.读取文件
            InputStream is = session.getServletContext().getResourceAsStream("/jasper/test05.jasper");

            Map<String , Object> map = new HashMap<String , Object>();

            //查询数据库
            List<User> list = new ArrayList<>();//模拟查询数据库
            for(int i = 0 ; i < 10 ; i ++){
                for(int j = 0 ; j < 10 ; j++){
                    User user = new User();
                    user.setUserName("张三"+ j);
                    user.setDeptName("部门"+j);
                    user.setEmail("123@qq.com");
                    user.setCompanyName("传智播客"+i);//保证  十个人一个公司
                    list.add(user);
                }
            }

            //将list类型   转换成 jasper所需要的 JRDataSource类型
            //JRBeanCollectionDataSource
            JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(list);
            //3.文件+数据部分
            //JRDataSource dataSource  我们的数据是 ArrayList
            JasperPrint jasperPrint = JasperFillManager.fillReport(is, map, beanDataSource);
            //4.下载 输出到浏览器控制台 修改DownLoadUtil 把文件下载
            JasperExportManager.exportReportToPdfStream(jasperPrint , response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 下载pdf
     */
    @RequestMapping("/exportPdf5")
    public void exportPdf5(String id){
        try {
            //1.有数据
            //2.读取文件
            InputStream is = session.getServletContext().getResourceAsStream("/jasper/test04.jasper");

            Map<String , Object> map = new HashMap<String , Object>();

            //查询数据库
            List<User> list = new ArrayList<>();//模拟查询数据库
            for(int i = 0 ; i < 10 ; i ++){
                User user = new User();
                user.setUserName("张三"+ i);
                user.setDeptName("部门"+i);
                user.setEmail("123@qq.com");
                user.setCompanyName("传智播客"+i);
                list.add(user);
            }

            //将list类型   转换成 jasper所需要的 JRDataSource类型
            //JRBeanCollectionDataSource
            JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(list);
            //3.文件+数据部分
            //JRDataSource dataSource  我们的数据是 ArrayList
            JasperPrint jasperPrint = JasperFillManager.fillReport(is, map, beanDataSource);
            //4.下载 输出到浏览器控制台 修改DownLoadUtil 把文件下载
            JasperExportManager.exportReportToPdfStream(jasperPrint , response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 下载pdf
     */
    @RequestMapping("/exportPdf4")
    public void exportPdf4(String id){
        try {
            //1.有数据
            //2.读取文件
            InputStream is = session.getServletContext().getResourceAsStream("/jasper/test03.jasper");

            Map<String , Object> map = new HashMap<String , Object>();

            //自己查询数据库 得到数据 并且往jasper文件中封装数据
            //第三个参数 只需要传入数据源 自动的下载数据库的数据 放入图形中
            //数据源在dao配置文件中  我们现在使用的是web项目  数据在此处出现 就不合理 不可以注入
            //自己构建连接对象
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/saas-export", "root", "root");
            //3.文件+数据部分
            JasperPrint jasperPrint = JasperFillManager.fillReport(is, map, connection);
            //4.下载 输出到浏览器控制台 修改DownLoadUtil 把文件下载
            JasperExportManager.exportReportToPdfStream(jasperPrint , response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 下载pdf
     */
    @RequestMapping("/exportPdf3")
    public void exportPdf3(String id){
        try {
            //1.有数据
            //2.读取文件
            InputStream is = session.getServletContext().getResourceAsStream("/jasper/test02rxml.jasper");

            Map<String , Object> map = new HashMap<String , Object>();
            //map的key 必须跟组件的名称一致
            map.put("username" , "张三");
            map.put("email" , "123@qq.com");
            map.put("deptName" , "研发部");
            map.put("companyName" , "传智播客");
            //3.文件+数据部分
            JasperPrint jasperPrint = JasperFillManager.fillReport(is, map, new JREmptyDataSource());
            //4.下载 输出到浏览器控制台 修改DownLoadUtil 把文件下载
            JasperExportManager.exportReportToPdfStream(jasperPrint , response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 下载pdf
     */
    @RequestMapping("/exportPdf2")
    public void exportPdf2(String id){
        try {
            //1.有数据
            //2.读取文件
            InputStream is = session.getServletContext().getResourceAsStream("/jasper/test01.jasper");
            //3.文件+数据部分
            //JasperFillManager  填充数据的工具类
            //public static JasperPrint fillReport(InputStream inputStream, Map<String,Object> parameters, JRDataSource dataSource
            //参数1: 文件流数据
            //参数2: Map类型数据
            //参数3: List类型数据   new JREmptyDataSource() 空数据集合
            JasperPrint jasperPrint = JasperFillManager.fillReport(is, new HashMap<>(), new JREmptyDataSource());
            //JasperExportManager 下载数据的工具类
            //4.下载 输出到浏览器控制台 修改DownLoadUtil 把文件下载
            JasperExportManager.exportReportToPdfStream(jasperPrint , response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
