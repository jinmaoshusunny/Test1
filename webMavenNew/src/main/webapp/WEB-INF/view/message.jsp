<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Login Information</title>
</head>
<body>
your login time : <c:out value="${user.loginTime}"></c:out> 
<br><br>
Hello <c:out value="${user.userName}"></c:out>,
 您是第 <c:out value="${number}"></c:out>位访问者，在您前边的访问量是：
<table border="1" align="center" cellpadding="5" cellspacing="0">
<tr align="center" valign="middle">
<th width="400">名字</th>
<th width="400">访问时间</th>
</tr>
<c:forEach var="users" items="${allInfo}">
<tr align="center" valign="middle">
<td>${users.userName}</td>
<td>${users.loginTime}</td>
</tr>
</c:forEach> 
 </table>
</body>
</html>