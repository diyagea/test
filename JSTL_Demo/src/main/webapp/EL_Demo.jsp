<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EL</title>
</head>
<body>
	<!-- 使用out标签输出常量 -->
	<c:out value="This is our first JSTL demo"></c:out>
	<br>
	<c:out value="${username}"></c:out>
	<c:out value="${1+2}"></c:out>
	<!-- <%String username = "zhangsan";
			request.setAttribute("username", username);%>
<c:out value="${empty username}"></c:out>
 -->
	<form action="firstDemo.jsp" method="post">
		<input type="text" name="role"> <input type="submit" name="登录">
	</form>
	<c:if test="${param.role=='admin'}">
		<c:out value="欢迎管理员"></c:out>
	</c:if>
</body>
</html>