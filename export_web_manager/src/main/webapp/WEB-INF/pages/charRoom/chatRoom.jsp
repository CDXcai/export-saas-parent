<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>chat</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chatRoom.css">
    <script src="${pageContext.request.contextPath}/js/chatRoom.js"></script>
</head>
<body>
<style>
    .wai {
        width: 100%;
        height: 500px;

    }

</style>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <div class="wai">

        <div class="context alert alert-light" style="overflow:auto" role="alert">
            <ul>

                <li>

                    <div class="receive">
                        <font>2019-1-1 10:10:10</font><br>
                        <div>
                            收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息
                        </div>
                    </div>
                </li>
                <li>

                    <div class="send">
                        <font>2019-1-1 10:10:10</font><br>
                        <div>
                            发送消息发送消息发送消息发送消息发送消息发送消息发送消息发送消息发送消息发送消息发送消息发送消息
                        </div>
                    </div>
                </li>

                <li>

                    <div class="receive">
                        <font>2019-1-1 10:10:10</font><br>
                        <div>
                            收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息
                        </div>
                    </div>
                </li>
                <li>

                    <div class="receive">
                        <font>2019-1-1 10:10:10</font><br>
                        <div>
                            收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息
                        </div>
                    </div>
                </li>


                <li>

                    <div class="receive">
                        <font>2019-1-1 10:10:10</font><br>
                        <div>
                            收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息收到消息
                        </div>
                    </div>
                </li>

            </ul>
        </div>
        <div class="botton">
            <input type="text" class="botton_text form-control">
            <input type="button" value="发送消息" class="botton_button btn btn-info">
        </div>
    </div>
</div>
</body>
</html>
