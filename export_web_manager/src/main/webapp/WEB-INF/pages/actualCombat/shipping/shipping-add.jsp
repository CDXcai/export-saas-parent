<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp" %>
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
            委托单管理
            <small>委托单的信息</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">委托单管理</a></li>
            <li class="active">委托单添加</li>
        </ol>
    </section>
    <!-- 内容头部 /-->
    <form id="editForm" action="${ctx}/actualCombat/shipping/edit.do" method="post" enctype="multipart/form-data">
        <!-- 正文区域 -->
        <section class="content">

            <!--订单信息-->
            <div class="panel panel-default">
                <div class="panel-heading">新增委托单</div>
                <%--

                --%>


                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">海运/空运</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio" name="orderType" value="0">海运</label></div>
                            <div class="radio"><label><input type="radio" name="orderType" value="1">空运</label></div>
                        </div>
                    </div>

                    <div class="col-md-2 title">货主</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="货主" name="shipper"
                               value="${shipping.shipper}">
                    </div>

                    <div class="col-md-2 title">收件人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="收件人" name="consignee"
                               value="${shipping.consignee}">
                    </div>

                    <div class="col-md-2 title">装运港</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="装运港" name="portOfTrans"
                               value="${shipping.portOfTrans}">
                    </div>
                    <div class="col-md-2 title">卸货港</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="卸货港" name="portOfDischarge"
                               value="${shipping.portOfDischarge}">
                    </div>
                    <div class="col-md-2 title">备注</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="备注" name="remark"
                               value="${shipping.remark}">
                    </div>
                    <div class="col-md-2 title">装货日期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="装货日期" name="loadingDate" class="form-control pull-right"
                                   value="<fmt:formatDate value="${shipping.loadingDate}" pattern="yyyy-MM-dd"/>"
                                   id="datepicker">
                        </div>
                    </div>

                    <div class="col-md-2 title">限定日期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="船期" name="limitDate" class="form-control pull-right"
                                   value="${shipping.limitDate}" id="datepicker1">
                        </div>
                    </div>
                </div>

            </div>
            <!--订单信息/-->

            <!--工具栏-->
            <div class="box-tools text-center">

                <button type="button" onclick='' class="btn bg-maroon" id="btnTest">保存</button>
                <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
            </div>
            <script>
                $(function () {
                    $("#btnTest").on("click",function () {
                        // alert("1");
                        var id = $("input[name='packingListId']:checked").val();;
                        if(id==null){
                            alert("请选择装箱单");
                        }else {
                            //alert(id)
                            document.getElementById("editForm").submit();
                           // alert("提交");
                        }

                    })
                })
            </script>
            <!--工具栏/-->

        </section>
        <!-- 正文区域 /-->

        <section class="content">

            <!-- .box-body -->
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">装箱单列表（已上报）</h3>
                </div>

                <div class="box-body">
                    <%--<input type="text" name="shippingOrderId" value="${shippingOrderId}">--%>
                    <!-- 数据表格 -->
                    <div class="table-box">
                        <!--数据列表-->
                        <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                            <thead>
                            <tr>
                                <td></td>
                                <td class="tableHeader">序号</td>
                                <td class="tableHeader">买方</td>
                                <td class="tableHeader">卖方</td>
                                <td class="tableHeader">发票号</td>
                                <td class="tableHeader">发票日期</td>
                                <td class="tableHeader">唛头</td>
                                <td class="tableHeader">状态</td>
                            </tr>
                            </thead>
                            <tbody class="tableBody">
                            ${links }

                            <c:forEach items="${packingLists}" var="o" varStatus="status">
                                <tr class="odd" onmouseover="this.className='highlight'"
                                    onmouseout="this.className='odd'">
                                    <td><input type="radio" id="radioBtn" name="packingListId" value="${o.packingListId}"/></td>
                                    <td>${status.index+1}</td>
                                    <td>${o.buyer}</td>
                                    <td>${o.seller}</td>
                                    <td>${o.invoiceNo}</td>
                                    <td>${o.invoiceDate}</td>
                                    <td>${o.marks}</td>
                                    <td>
                                        <c:if test="${o.state==1}">
                                            已上报
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
<script src="${ctx}/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${ctx}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="${ctx}/css/style.css">
<script>
    $('#datepicker').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#datepicker1').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>