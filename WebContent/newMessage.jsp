<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿</title>
</head>
<body>

<div class="main-contents">
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="messages">
				<li><c:out value="${messages}"/>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session" />
</c:if>

<form action="newMessage" method="post"><br />
	<label for="title">件名</label> <br />
	<input name="title" value="${message.title}"  id="title"/><br />

	<label for="category">カテゴリー</label> <br />
	<input name="category" value="${message.category}"  id="title"/><br />

	<label for="text">本文</label> <br />
	<textarea name="text" cols="50" rows="20" class="text-box" >${message.text}</textarea> <br />

	<input type="submit" value="投稿" /> <br />
	<a href="./">戻る</a>
</form>
</div>
</body>
</html>