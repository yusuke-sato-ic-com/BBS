<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ホーム画面</title>
</head>
<body>
<div class="main-contents">

<div class="header">
	<c:if test="${empty loginUser }">
		<a href="login">ログイン</a>
	</c:if>
	<c:if test="${ not empty loginUser }">
		<a href="newMessage">新規投稿</a>
		<a href="userManagement">ユーザー管理</a>
		<a href="logout">ログアウト</a>
		<div class="name"><h2><c:out value="${loginUser.name}"/></h2></div>
	</c:if>
</div>



</div>


</body>
</html>