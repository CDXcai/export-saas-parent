<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../base.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <base href="${ctx}/">
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
    <link rel="stylesheet" href="plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="plugins/ztree/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="plugins/ztree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="plugins/ztree/js/jquery.ztree.excheck-3.5.js"></script>

    <SCRIPT type="text/javascript">
        <!--
        //设置
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        //显示数据
        var zNodes ;

        var code;
        var treeObj;

        //页面加载  查询所有的模块数据 显示为json的数据 将json放入树形菜单中
        //   $.fn.zTree.init($("#treeDemo"), setting, zNodes); 构建树形菜单的代码
        $(function(){
            $.get("/system/role/initTree.do?roleId=${role.id}" , function(data){
                //将数据赋值给了zNodes
                zNodes = data;
                //初始化树
                treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                treeObj.expandAll(true);//展开所有节点  等效后台设置 map.put("open" ,true);
            });
        })



        /*//$(document).ready(function(){ } 页面加载函数 页面加载完以后马上执行
        $(document).ready(function(){
            //  初始化树结构
            //参数1: 某个元素 动态的渲染ul成为树结构
            //参数2: 树结构的一些参数设置
            //参数3: 显示树结构的数据部分
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        });*/
        //-->
    </SCRIPT>

    <script>
        //提交表单
        function submitCheckedNodes(){
            //<input type="text" id="moduleIds" name="moduleIds" value=""/>
            //获得树形菜单中已经勾选的复选框
            var ckb = treeObj.getCheckedNodes(true);
            //console.log(ckb);

            var moduleIds = "";
            //循环
            for(var i = 0 ; i < ckb.length;i++ ){
                //console.log(ckb[i].id);
                moduleIds+=ckb[i].id + ",";
            }
            //判断
            if(moduleIds.length>0){
                //substring 截取字符串 从0开始 到 最后一位结束 不含
                moduleIds = moduleIds.substring(0,moduleIds.length-1);
                //console.log(moduleIds);
            }
            //alert(ckb);
            //通过js的方式动态修改表单中属性值
            document.getElementById("moduleIds").value=moduleIds;


            //1.原生js
            //document.getElementById("icform").submit();
            //2.jquery方式
            //$("#icform").submit();
            //3.document表单提交  document是一个文档(整个html) html中的表单形成一个数组[0] ,数组中的第一个表单提交i
            //document.forms[0].submit();
            //4.通过表单的name属性
            document.icform.submit();
        }
    </script>
</head>

<body style="overflow: visible;">
<div id="frameContent" class="content-wrapper" style="margin-left:0px;height: 1200px" >
    <section class="content-header">
        <h1>
            菜单管理
            <small>菜单列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">角色 [${role.name}] 权限列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--工具栏-->
                    <div class="box-tools text-left">
                        <button type="button" class="btn bg-maroon" onclick="submitCheckedNodes();">保存</button>
                        <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
                    </div>
                    <!--工具栏/-->
                    <!-- 树菜单 -->
                    <form id="icform" name="icform" method="post" action="${ctx}/system/role/updateRoleModule.do">
                        <input type="text" name="roleid" value="${role.id}"/>
                        <input type="text" id="moduleIds" name="moduleIds" value=""/>
                        <div class="content_wrap">
                            <div class="zTreeDemoBackground left" style="overflow: visible">
                                <ul id="treeDemo" class="ztree">

                                </ul>
                            </div>
                        </div>
                    </form>
                    <!-- 树菜单 /-->

                </div>
                <!-- /数据表格 -->

            </div>
            <!-- /.box-body -->
        </div>
    </section>
</div>
</body>
</html>