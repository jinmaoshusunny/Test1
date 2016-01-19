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
		
		<form action="message.do" method="post">
			<table width="300" border="1">				
				<tr>
					<td>username:</td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td><input type="submit" value="submit"></td>
				</tr>
			</table>
		</form>
	</div>	
</body>
</html>