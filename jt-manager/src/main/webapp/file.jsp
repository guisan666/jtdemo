<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>实现文件长传</h1>
	<form action="http://localhost:8091/file" method="post" enctype="multipart/form-data">
		<input name="fileImage" type="file" />
		<input type="submit" value="提交"/>
	</form>
</body>
</html>