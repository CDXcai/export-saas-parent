<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../base.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${ctx}/img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p> ${sessionScope.loginUser.userName}</p>
                <a href="#">${sessionScope.loginUser.companyName}</a>
            </div>
        </div>

        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">菜单</li>

            <%--
            <c:forEach items="${sessionScope.modules}" var="item">
                <c:if test="${item.ctype==0}">
                    <li class="treeview">
                        <a href="#">
                            <i class="fa fa-cube"></i> <span>${item.name}</span>
                            <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
                        </a>
                        <ul class="treeview-menu">
                            <c:forEach items="${sessionScope.modules}" var="item2">
                                <c:if test="${item2.ctype==1 && item2.parentId == item.id}">
                                    <li id="${item2.id}">
                                        <a onclick="setSidebarActive(this)" href="${ctx}/${item2.curl}" target="iframe">
                                            <i class="fa fa-circle-o"></i>${item2.name}
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>
            </c:forEach>
--%>

            <%----%>

            <c:forEach items="${moduleList}" var="parentModule">
                <li class="treeview">

                        <%--一级标题--%>
                    <c:if test="${parentModule.ctype==0}">
                        <a href="#">
                            <i class="fa fa-cube"></i> <span>${parentModule.name}</span>
                            <span class="pull-right-container">
                               <i class="fa fa-angle-left pull-right"></i>
                           </span>
                        </a>
                    </c:if>
                        <%--二级标题--%>
                    <ul class="treeview-menu">

                        <c:forEach items="${moduleList}" var="childModule">
                            <%--二级菜单 : 属于某个一级菜单的子菜单--%>
                            <c:if test="${childModule.ctype==1 and childModule.parentId==parentModule.id}">
                                <li id="company-manager">
                                    <a href="${ctx}/${childModule.curl}" onclick="setSidebarActive(this)"
                                       target="iframe">
                                        <i class="fa fa-circle-o"></i>${childModule.name}
                                    </a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
            <%--聊天室页面--%>
            <li class="treeview">
                <a href="/chatRoom/toChatRoom.do" onclick="setSidebarActive(this)" target="iframe">
                    <i class="fa fa-circle-o"></i>聊天室
                </a>
            </li>

        </ul>

    </section>
    <!-- /.sidebar -->
</aside>
