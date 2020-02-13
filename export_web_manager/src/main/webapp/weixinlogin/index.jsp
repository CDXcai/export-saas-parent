<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
</head>
<script type="text/javascript">
    var $url = "${pageContext.request.contextPath}/weixin/login.do?"
    var $code = "${pageContext.request.getParameter('code')}";
    console.log($code)
    window.location.href = $url + "code=" + $code;
</script>
<body>
</body>
</html>
