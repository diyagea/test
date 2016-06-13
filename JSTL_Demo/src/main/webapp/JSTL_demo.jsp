<%@page import="java.io.IOException"%>
<%@page import="java.io.Reader"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="person" class="Person"></jsp:useBean>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 使用out标签输出常量 -->
<c:out value="This is our first JSTL demo"></c:out><br>
<!-- 使用out标签输出变量 -->
<!-- 直接输出变量 -->
<%session.setAttribute("name", "Jessica"); %> 
<c:out value="${name}"></c:out>
<!-- 当变量不存在时，通过default属性输出默认值 -->
<c:out value="${name1}" default="error"></c:out><br>
<!-- 设置输出转义后的字符需要escapeXml属性值设置成false -->
<c:out value="&ltout标签&gt" escapeXml="false"></c:out><br>


<!-- 存值到scope中 -->
<c:set value="today" var="day" scope="session"></c:set>
<c:out value="${day}"></c:out><br>

<c:set var="age" scope="application">eleven</c:set>
<c:out value="${age}"></c:out><br>

<!-- 通过set标签向person中赋值 -->
<c:set target="${person}" property="name2" value="zhangsan"></c:set>
<c:out value="${person.name2}"></c:out>&nbsp;&nbsp;
<c:set target="${person}" property="age2" value="26"></c:set>
<c:out value="${person.age2}"></c:out>&nbsp;&nbsp;
<c:set target="${person}" property="address" value="北京市 朝阳区"></c:set>
<c:out value="${person.address}"></c:out>&nbsp;&nbsp;<br>

<c:set target="${person}" property="name2">zhangsan</c:set>
<c:out value="${person.name2}"></c:out>&nbsp;&nbsp;
<c:set target="${person}" property="age2">26</c:set>
<c:out value="${person.age2}"></c:out>&nbsp;&nbsp;
<c:set target="${person}" property="address">北京市 朝阳区</c:set>
<c:out value="${person.address}"></c:out>&nbsp;&nbsp;<br>


<!-- remove标签的用法 -->
<c:set var="lastName" value="Lily"></c:set>
<c:out value="${lastName}"></c:out>
<c:set var="firstName" value="Wang"></c:set>
<c:remove var="firstName"/>
<c:out value="${firstName}"></c:out><br>



<!-- catch标签的用法 -->
<c:catch var="error">
	<c:set target="aa" property="bb">asasa</c:set>
</c:catch>
<c:out value="${error}"></c:out><br>


<!-- if标签的用法 -->
<form action="firstDemo.jsp" method="post">
	<input type="text" name="score" value="${param.score}"/>
	<input type="submit"/>
</form>
<!-- 优秀 score>=90-->
<!--
<c:if test="${param.score>=90}" var="result" scope="session">
	<c:out value="恭喜，您的成绩是优秀！"></c:out>
</c:if>
<c:out value="${applicationScope.result}"></c:out>
-->


<!-- choose、when、otherwise -->
<c:choose>
	<c:when test="${param.score>=90 && param.score<=100}">
		<c:out value="优秀"></c:out>
	</c:when>
	<c:when test="${param.score>=80 && param.score<90}">
		<c:out value="良好"></c:out>
	</c:when>
	<c:when test="${param.score>=70 && param.score<80}">
		<c:out value="中等"></c:out>
	</c:when>
	<c:when test="${param.score>=60 && param.score<70}">
		<c:out value="及格"></c:out>
	</c:when>
	<c:when test="${param.score<60 && param.score>=0}">
		<c:out value="不及格"></c:out>
	</c:when>
	<c:otherwise>
		<c:out value="您的输入有问题"></c:out>
	</c:otherwise>
</c:choose>
<br>
<c:choose>
	<c:when test="${param.score==100}">
		<c:out value="太棒了，你是第一名！"></c:out>
	</c:when>
</c:choose>
<br>


<!-- forEach标签的用法 -->
<%
	List animals = new ArrayList();
	animals.add("tiger");
	animals.add("dog");
	animals.add("elephant");
	animals.add("lion");
	animals.add("bird");
	animals.add("fish");
	animals.add("bear");
	request.setAttribute("animals", animals);
%>

<c:forEach var="ani" items="${animals}">
	<c:out value="${ani}"></c:out><br>
</c:forEach>
<c:out value="-------------------------"></c:out><br>
<c:forEach var="ani" items="${animals}" begin="2" end="4">
	<c:out value="${ani}"></c:out><br>
</c:forEach>
<c:out value="-------------------------"></c:out><br>
<c:forEach var="ani" items="${animals}" begin="2" end="4" step="2">
	<c:out value="${ani}"></c:out><br>
</c:forEach>
<c:out value="-------------------------"></c:out><br>
<c:forEach var="ani" items="${animals}" begin="2" end="4" varStatus="a">
	<c:out value="原集合中的索引值${a.index}"></c:out><br>
	<c:out value="总共已迭代的次数${a.count}"></c:out><br>
	<c:out value="判断当前元素是否是子集中的第一个值${a.first}"></c:out><br>
	<c:out value="判断当前元素是否是子集中的最后一个值${a.last}"></c:out><br>
	<c:out value="======"></c:out><br>
</c:forEach><br>


<!-- forTokens的用法 -->

<c:forTokens items="010-88668543-1033" delims="-" var="num">
	<c:out value="${num}"></c:out><br>
</c:forTokens>

<c:out value="==================="></c:out><br>
<c:forTokens items="苹果、橘子、香蕉、梨、葡萄、西瓜" delims="、" var="fruit" begin="1" end="3" varStatus="fru">
	<c:out value="${fruit}"></c:out>的四个属性：<br>
	索引值是：<c:out value="${fru.index}"></c:out><br>
	已遍历的个数<c:out value="${fru.count}"></c:out><br>
	是否第一个遍历<c:out value="${fru.first}"></c:out><br>
	是否最后一个遍历<c:out value="${fru.last}"></c:out><br>
	<c:out value="-----------------"></c:out><br>
</c:forTokens>
<br><br>

<!-- import标签的用法 -->
<!-- 导入网络上的绝对路径 -->
<c:catch var="error09">
	<c:import url="http://www.imooc.com"></c:import>
</c:catch>
<c:out value="${error09}"></c:out><br>

<!-- 导入相对路径文件 -->
<c:catch var="error08">
	<c:import url="tt.txt" charEncoding="gbk"></c:import>
</c:catch>
<c:out value="${error08}"></c:out><br>
<!-- var及scope属性的用法 -->
<c:catch var="error07">
	<c:import url="tt.txt" var="tt" scope="session" charEncoding="gbk"></c:import>
</c:catch>
<c:out value="${error07}"></c:out><br>
<c:out value="import标签存储的tt字符串变量值为：${sessionScope.tt}"></c:out><br>
<!-- context属性的用法 -->
<c:catch var="error06">
	<c:import url="/importFile.jsp" context="/AnotherWebProject"></c:import>
</c:catch>
<c:out value="${error06}"></c:out><br>

<!-- varReader属性的用法 -->
<c:import url="tt.txt" varReader="tt" charEncoding="gbk">
<c:set var="strTT" value="${tt}" scope="session"></c:set>
<%
	Reader r = (Reader)session.getAttribute("strTT");
	char[] buffer = new char[256];
	try{
		int dataLen = r.read(buffer);
		String result = new String(buffer, 0, dataLen);
		System.out.print(result);
	}catch(IOException e){
		e.printStackTrace();
	}
%>
</c:import>

</body>
</html>