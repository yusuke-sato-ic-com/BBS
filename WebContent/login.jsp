<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./css/style.css">

<title>ログイン</title>
</head>
<body>

<div id="header">
	<c:if test="${ not empty errorMessages }">
		<div class="errorMessages">
			<ul> <%-- <ul>は順序のないリストを表示する歳際に使用する。順序のあるリスト表示は<ol> --%>
				<c:forEach items="${errorMessages}" var="messages"> <%-- 配列をループ処理 --%>
					<font size="4" color="#ff0000"><c:out value="${messages}" /><br /></font>
				</c:forEach>
			</ul>
		</div>
		<c:remove var="errorMessages" scope="session"/>
	</c:if>
</div>

<div id="login-field">
<p class="login-field-title">掲示板ログイン<br /></p>
<form action="login" method="post">
	<p><label for="loginId">ログインID</label></p>
	<p class="id"><input name="loginId" id="loginId"/></p>

	<p><label for="password">パスワード</label></p>
	<p class="pass"><input name="password" type="password" id="password"/></p>
	<p class="submit"><input type="submit" value="ログイン" /></p>
</form>
</div>
</body>
</html>