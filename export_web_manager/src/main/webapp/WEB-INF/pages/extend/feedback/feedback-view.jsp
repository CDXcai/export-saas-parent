<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
            反馈
            <small>注意:反馈可能被公开</small>
        </h1>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">反馈(已经解决的反馈只能保存30天)</div>
            <form id="editForm" action="${ctx}/extend/feedback/edit.do" method="post">
                <div class="row data-type" style="margin: 0px">
                    <input type="hidden" name="feedbackId" value="${feedback.feedbackId}">

                    <div class="col-md-2 title">提出人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="提出人" name="inputBy" value="${feedback.inputBy}" readonly>
                    </div>

                    <div class="col-md-2 title">提出时间</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="提出时间" name="inputTime" value="<fmt:formatDate value="${feedback.inputTime}" pattern="yyyy-MM-dd"/>" readonly>
                    </div>

                    <div class="col-md-2 title">标题</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="标题" name="title" value="${feedback.title}" readonly>
                    </div>


                    <div class="col-md-2 title">分类</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline" readonly="">
                            <div class="radio"><label><input type="radio" ${feedback.classType==1?'checked':''} name="classType" value="1" >管理</label></div>
                            <div class="radio"><label><input type="radio" ${feedback.classType==2?'checked':''} name="classType" value="2" >安全</label></div>
                            <div class="radio"><label><input type="radio" ${feedback.classType==3?'checked':''} name="classType" value="3" >建议</label></div>
                            <div class="radio"><label><input type="radio" ${feedback.classType==4?'checked':''} name="classType" value="4" >其他</label></div>
                        </div>
                    </div>

                    <div class="col-md-2 title">解决方式</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio" ${feedback.resolution==1?'checked':''} name="resolution" value="1" >已修改</label></div>
                            <div class="radio"><label><input type="radio" ${feedback.resolution==2?'checked':''} name="resolution" value="2" >无需修改</label></div>
                            <div class="radio"><label><input type="radio" ${feedback.resolution==3?'checked':''} name="resolution" value="3" >重复问题</label></div>
                            <div class="radio"><label><input type="radio" ${feedback.resolution==4?'checked':''} name="resolution" value="4" >描述不完整</label></div>
                            <div class="radio"><label><input type="radio" ${feedback.resolution==5?'checked':''} name="resolution" value="5" >无法实现</label></div>
                            <div class="radio"><label><input type="radio" ${feedback.resolution==6?'checked':''} name="resolution" value="6" >其他</label></div>
                        </div>
                    </div>

                    <div class="col-md-2 title">解决难度</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio" ${feedback.difficulty==1?'checked':''} name="difficulty" value="1" >极难</label></div>
                            <div class="radio"><label><input type="radio" ${feedback.difficulty==2?'checked':''} name="difficulty" value="2" >比较难</label></div>
                            <div class="radio"><label><input type="radio" ${feedback.difficulty==3?'checked':''} name="difficulty" value="3" >有难度</label></div>
                            <div class="radio"><label><input type="radio" ${feedback.difficulty==4?'checked':''} name="difficulty" value="4" >一般</label></div>
                        </div>
                    </div>

                    <div class="col-md-2 title rowHeight2x">反馈内容</div>
                    <div class="col-md-10 data rowHeight2x">
                        <textarea class="form-control" rows="5" name="content" readonly>${feedback.content}</textarea>
                    </div>

                    <div class="col-md-2 title rowHeight2x">解决方式</div>
                    <div class="col-md-10 data rowHeight2x">
                        <textarea class="form-control" rows="5" name="solveMethod" readonly>${feedback.solveMethod}</textarea>
                    </div>
                </div>
            </form>
        </div>
        <!--订单信息/-->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" class="btn btn-github" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->

    </section>
    <!-- 正文区域 /-->

</div>
<!-- 内容区域 /-->
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#signingDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#deliveryPeriod').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#shipTime').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>