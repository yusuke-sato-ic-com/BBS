<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./css/style.css">
<title>新規投稿</title>


</head>
<body>

<div class="main-contents">
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="messages">
				<font size="4" color="#ff0000"><c:out value="${messages}" /><br /></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session" />
</c:if>

<div id="message-field">
<form action="newMessage" method="post"><br />
	<p><label for="title">件名</label></p>
	<p class="title"><input name="title" value="${message.title}"  id="title"/></p>

	<p><label for="category">カテゴリー</label></p>
	<p class="category"><input name="category" value="${message.category}"  id="title"/></p>

	<p><label for="text">本文</label></p>
	<p class="text-box"><textarea name="text" cols="50" rows="20" class="text-box" >${message.text}</textarea></p>

	<p class="submit"><input type="submit" value="投稿" /></p>
	<p><a href="./">ホームに戻る</a></p>
</form>
</div>
</div>
</body>
</html>