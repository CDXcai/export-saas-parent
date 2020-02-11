<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ include file="../base.jsp"%>
<header class="main-header">
    <a href="all-admin-index.html" class="logo">
        <span class="logo-mini"><img src="${ctx}/img/logo.png"></span>
        <span class="logo-lg">
                    <img src="${ctx}/img/export.png">
                    <i> SaaS外贸进出口平台</i>
                </span>
    </a>

    <nav class="navbar navbar-static-top">
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <li class="dropdown messages-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-envelope-o"></i>
                        <c:if test="${sessionScope.loginUser.degree==0}">
                        <span class="label label-success" id="feedbackNum"></span>
                        </c:if>
                    </a>


<%--------------------------------------------------%>













<%--------------------------------------------------%>

                    <script>
                        //动态修改上级模块的数据
                        $(function () {
                            //发送ajax
                            $.get("${ctx}/extend/feedback/list.do",function (data) {
                                if (data!=0){
                                    $("#feedbackNum1").html("有"+data+"个未解决的问题");
                                }else {
                                    $("#feedbackNum1").html("所有问题都已解决");
                                }
                                $("#feedbackNum").html(data)
                        });
                        });
                    </script>


                    <%----%>
                    <ul class="dropdown-menu">
                        <c:if test="${sessionScope.loginUser.degree==0}">
                        <li class="header" id="feedbackNum1">有个未解决的问题</li>
                        </c:if>
                        <li>
                            <ul class="menu">
                                <c:forEach items="${sessionScope.feedbackList}" var="feedback">
                                <li>
                                    <a href="${ctx}/extend/feedback/toUpdate.do?id=${feedback.feedbackId}" id="skip">
                                        <div class="pull-left">
                                            <img src="${ctx}/img/user3-128x128.jpg" class="img-circle" alt="User Image">
                                        </div>
                                        <h4>
                                            <p>${feedback.title}</p><c:if test="${feedback.state==0}"><p style="color: red">未解决</p></c:if>
                                                                     <c:if test="${feedback.state==1}"><p style="color: green">已解决</p></c:if>
                                        </h4>
                                            <c:if test="${feedback.createBy eq sessionScope.loginUser.id}"><p style="color: #3c8dbc;">我的反馈</p></c:if>

                                            <p>点击查看</p>
                                    </a>
                                </li>
                                </c:forEach>
                            </ul>
                        </li>
                        <c:if test="${sessionScope.loginUser.degree!=0}">
                        <li class="footer"><a href="${ctx}/extend/feedback/toAdd.do">发送反馈</a></li>
                        </c:if>
                    </ul>
                </li>




                <!-- Notifications: style can be found in dropdown.less -->


                <li class="dropdown notifications-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-bell-o"></i>
                        <span class="label label-warning">10</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="header">你有10个新消息</li>
                        <li>
                            <!-- inner menu: contains the actual data -->
                            <ul class="menu">
                                <li>
                                    <a href="#">
                                        <i class="fa fa-users text-aqua"></i> 5 new members joined today
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fa fa-warning text-yellow"></i> Very long description here that may not
                                        fit into the page and may cause design problems
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fa fa-users text-red"></i> 5 new members joined
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fa fa-shopping-cart text-green"></i> 25 sales made
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i class="fa fa-user text-red"></i> You changed your username
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="footer"><a href="#">View all</a></li>
                    </ul>
                </li>



                <!-- Tasks: style can be found in dropdown.less -->
                <li class="dropdown tasks-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-flag-o"></i>
                        <span class="label label-danger">9</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="header">你有9个新任务</li>
                        <li>
                            <!-- inner menu: contains the actual data -->
                            <ul class="menu">
                                <li>
                                    <!-- Task item -->
                                    <a href="#">
                                        <h3>
                                            Design some buttons
                                            <small class="pull-right">20%</small>
                                        </h3>
                                        <div class="progress xs">
                                            <div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                <span class="sr-only">20% Complete</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <!-- end task item -->
                                <li>
                                    <!-- Task item -->
                                    <a href="#">
                                        <h3>
                                            Create a nice theme
                                            <small class="pull-right">40%</small>
                                        </h3>
                                        <div class="progress xs">
                                            <div class="progress-bar progress-bar-green" style="width: 40%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                <span class="sr-only">40% Complete</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <!-- end task item -->
                                <li>
                                    <!-- Task item -->
                                    <a href="#">
                                        <h3>
                                            Some task I need to do
                                            <small class="pull-right">60%</small>
                                        </h3>
                                        <div class="progress xs">
                                            <div class="progress-bar progress-bar-red" style="width: 60%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                <span class="sr-only">60% Complete</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <!-- end task item -->
                                <li>
                                    <!-- Task item -->
                                    <a href="#">
                                        <h3>
                                            Make beautiful transitions
                                            <small class="pull-right">80%</small>
                                        </h3>
                                        <div class="progress xs">
                                            <div class="progress-bar progress-bar-yellow" style="width: 80%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                <span class="sr-only">80% Complete</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <!-- end task item -->
                            </ul>
                        </li>
                        <li class="footer">
                            <a href="#">View all tasks</a>
                        </li>
                    </ul>
                </li>
                <!-- User Account: style can be found in dropdown.less -->
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="${ctx}/img/user2-160x160.jpg" class="user-image" alt="User Image">
                        <span class="hidden-xs"> ${sessionScope.user.userName}</span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- User image -->
                        <li class="user-header">
                            <img src="${ctx}/img/user2-160x160.jpg" class="img-circle" alt="User Image">

                            <p>
                                ${sessionScope.user.userName}
                            </p>
                        </li>
                        <!-- Menu Body
                <li class="user-body">
                    <div class="row">
                        <div class="col-xs-4 text-center">
                            <a href="#">Followers</a>
                        </div>
                        <div class="col-xs-4 text-center">
                            <a href="#">Sales</a>
                        </div>
                        <div class="col-xs-4 text-center">
                            <a href="#">Friends</a>
                        </div>
                    </div>
                </li>-->
                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-left">
                                <a href="${ctx}/system/user/toChangePassword.do" class="btn btn-default btn-flat">修改密码</a>
                            </div>
                            <div class="pull-right">
                                <a href="/logout.do" class="btn btn-default btn-flat">注销</a>
                            </div>
                        </li>
                    </ul>
                </li>

            </ul>
        </div>
    </nav>
</header>
<!-- 页面头部 /-->

