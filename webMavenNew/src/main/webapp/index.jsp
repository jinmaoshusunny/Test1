<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>login page</title>
</head>
<body >
	<div style="background-image: url(../images/ChinaMobile.jpg)">
		<c:out
			value="Welcome to visit the website of The China Mobile On-line services,Enter your name and submit."></c:out>
		<form action="login.do" method="post">
			<table width="300" border="1">
				<tr>
					<td colspan="2">登录窗口</td>
				</tr>
				<tr>
					<td>username:</td>
					<td><input type="text" name="username" size="10"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" name="submit" value="submit"></td>
				</tr>
			</table>
		</form>
	</div>	
</body>
</html>