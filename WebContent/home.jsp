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

投稿の一覧表示実装中
<div class="messages">
	<c:forEach items="${messages}" var="message">
		<div class="message">
			<div class="name">
				<span class="name"><c:out value="${message.name}" /> </span>
			</div>
			<div class="title"><c:out value="${message.title}" /> </div>
			<div class="category"><c:out value="${message.category}" /> </div>
			<div class="text"><c:out value="${message.text}" /> </div>
			<div class="date"><fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /> </div>
		</div>
	</c:forEach>
</div>

</div>
</body>
</html>