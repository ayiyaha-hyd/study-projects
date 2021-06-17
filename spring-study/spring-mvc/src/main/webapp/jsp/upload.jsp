<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/user/upload" method="post" enctype="multipart/form-data">
    名称<input type="text" name="username"><br/>
    文件1<input type="file" name="f1"><br/>
<%--    文件2<input type="file" name="uploadFile"><br/>--%>
    <input type="submit" value="提交">
</form>

<form action="${pageContext.request.contextPath}/user/upload2" method="post" enctype="multipart/form-data">
    名称<input type="text" name="username"><br/>
    文件1<input type="file" name="file1"><br/>
    文件2<input type="file" name="file2"><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
