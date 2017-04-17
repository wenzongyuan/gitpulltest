<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>using commons Upload to upload file </title>
</head>
<style>
</style>
<body>
<p align="center"> 请您选择需要上传的文件</p>
<form method="POST" enctype="multipart/form-data" action="upload">
  上传文件: <input type="file" name="upfile"><br/>
  信息: <input type="text" name="note"><br/>
  <br/>
  <input type="submit" value="Press"> to upload the file!
</form>
</body>
</html>