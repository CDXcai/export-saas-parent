<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            装箱管理
            <small>装箱的信息</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">装箱管理</a></li>
            <li class="active">装箱列表添加</li>
        </ol>
    </section>
    <!-- 内容头部 /-->
    <form id="editForm" action="${ctx}/actualCombat/packingList/edit.do" method="post"  enctype="multipart/form-data" >
    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">新增装箱单</div>
            <%--

            --%>

               <%-- <input type="text" name="contractId" value="${contractId}">--%>
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">卖方</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="卖方" name="seller" value="${packingList.seller}">
                    </div>

                    <div class="col-md-2 title">买方</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="买方" name="buyer" value="${packingList.buyer}">
                    </div>

                    <div class="col-md-2 title">发票号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" readonly placeholder="发票号"  name="invoiceNo" value="${packingList.invoiceNo}">
                    </div>

                    <div class="col-md-2 title">发票日期</div>
                    <div class="col-md-4 data">

                        <input type="text" placeholder="发票日期"  name="invoiceDate" readonly class="form-control pull-right"
                               value="<fmt:formatDate value="${packingList.invoiceDate}" pattern="yyyy-MM-dd"/>" id="signingDate">
                    </div>

                    <div class="col-md-2 title">商标</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="商标" name="marks" value="${packingList.marks}">
                    </div>

                    <div class="col-md-2 title">描述</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="描述" name="descriptions" value="${packingList.descriptions}">
                    </div>

                </div>

        </div>
        <!--订单信息/-->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" onclick='document.getElementById("editForm").submit()' class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->

    </section>
    <!-- 正文区域 /-->

    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">报运列表(只有报运成功的才可以显示出来)</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
                            <td class="tableHeader">序号</td>
                            <td class="tableHeader">合同及确认书号</td>
                            <td class="tableHeader">信用证号</td>
                            <td class="tableHeader">收货人及地址</td>
                            <td class="tableHeader">唛头</td>
                            <td class="tableHeader">装船港</td>
                            <td class="tableHeader">目的港</td>
                            <td class="tableHeader">状态</td>
                        </tr>
                        </thead>
                        <tbody class="tableBody" >
                        ${links }
                        <c:forEach items="${exports}" var="o" varStatus="status">
                            <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
                                <%-- ${o.state==3 ?"checked" :""}--%>

                                <td>
                                    <c:if test="${fn:contains(packingList.exportIds,o.id)}">
                                        <input type="checkbox" disabled name="exportIds" value="${o.id}" checked  />
                                    </c:if>
                                    <c:if test="${not fn:contains(packingList.exportIds,o.id)}">
                                        <input type="checkbox"  disabled name="exportIds" value="${o.id}"   />
                                    </c:if>
                                </td>
                                <td>${status.index+1}</td>
                                <td>${o.customerContract}</td>
                                <td>${o.lcno}</td>
                                <td>${o.consignee}</td>
                                <td>${o.marks}</td>
                                <td>${o.shipmentPort}</td>
                                <td>${o.destinationPort}</td>
                                <td>
                                    <c:if test="${o.state==2}">
                                        已报运
                                    </c:if>
                                    <c:if test="${o.state==3}">
                                        <font color="red">已装箱<font>
                                    </c:if>
                                </td>
                                <td>

                                </td>
                            </tr>


                        </c:forEach>
                        </tbody>
                    </table>
                    <!--数据列表/-->
                    <!--工具栏/-->
                </div>
                <!-- 数据表格 /-->
            </div>
            <!-- /.box-body -->


        </div>

    </section>
    </form>
</div>
<!-- 内容区域 /-->
</body>

</html>