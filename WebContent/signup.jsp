<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./css/style.css">
<title>ユーザー新規登録</title>


</head>
<body>

<div class="main-contents">


<div id="menu">
	<p><a href="userManagement">管理画面へ戻る</a></p><br />
	<div class="home-name"><c:out value="${loginUser.name}"/>&nbsp;</div>
</div>

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

<div id="signup-field">
<p class="signup-field-title">ユーザー新規登録画面<br /></p>
<form action="signup" method="post"><br />
	<p><label for="name">名前</label></p>
	<p class="name"><input name="name" value="${user.name}" id="name"/></p>

	<p><label for="loginId">ログインID</label></p>
	<p class="login-id"><input name="loginId"  value="${user.loginId}" id="loginId"/></p>

	<p><label for="password">パスワード(登録用)</label></p>
	<p class="pass"><input name="password" type="password" id="password"/></p>

	<p><label for="confirm">パスワード(確認用)</label></p>
	<p class="confirm"><input name="confirm" type="password" id="confirm"/></p>

	<p><label for="branch">所属支店</label></p>
	<p class="branch"><select name="branch">
		<option value="0">支店選択</option>
		<c:forEach items="${branch}" var="branch">
			<c:choose>
				<c:when test="${branch.id == user.branchId}">
					<option value="${branch.id}" selected>${branch.name}</option>
				</c:when>
				<c:otherwise>
					<option value="${branch.id}">${branch.name}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select></p><br/>

	<p><label for="department">所属部署</label></p>
	<p class="department"><select name="department">
		<option value="0">部署選択</option>
		<c:forEach items="${department}" var="department">
			<c:choose>
				<c:when test="${department.id == user.departmentId}">
					<option value="${department.id}" selected>${department.name}</option>
				</c:when>
				<c:otherwise>
					<option value="${department.id}">${department.name}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select></p>
	<p class="submit"><input type="submit" value="登録" /></p>

</form>
</div>
</div>
</body>
</html>