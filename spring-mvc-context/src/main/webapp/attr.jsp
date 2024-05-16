<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="utf-8">
    <title></title>
</head>
<body>
<h2>测试数据</h2>
<h3>request的结果 : ${requestScope.name}</h3>

<h3>获取地址栏的数据:${param.name}</h3>

<h3>获取session的数据:${sessionScope.user}</h3>

<script>
    $(function () {
        alert();
    })
</script>
</body>
</html>