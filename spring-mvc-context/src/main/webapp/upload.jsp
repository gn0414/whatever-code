<%--
  Created by IntelliJ IDEA.
  User: Simon
  Date: 2024/5/15
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/file/upload" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <p></p>
    <input type="submit" value="上传文件"/>
</form>

<a href="${pageContext.request.contextPath}/file/download?fileName=aa.txt">aa.txt</a>
<a href="${pageContext.request.contextPath}/file/download?fileName=自我介绍.txt">自我介绍.txt</a>
</body>
</html>
