<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー編集</title>
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

<form action="userEdit?user_id=${user.id}" method="post"><br />
	<label for="name">名前</label>
	<input name="name" value="${user.name}" id="name"/><br />

	<label for="loginId">ログインID</label>
	<input name="loginId"  value="${user.loginId}" id="loginId"/><br />

	<label for="password">パスワード(登録用)</label>
	<input name="password" type="password" id="password"/> <br />

	<label for="confirm">パスワード(確認用)</label>
	<input name="confirm" type="password" id="confirm"/> <br />

	<label for="branch">所属支店</label>
	<select name="branch">
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
	</select>  <br />

	<label for="department">所属部署</label>
	<select name="department">
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
	</select>  <br />
	<input type="submit" value="変更" /> <br />
<a href="userManagement">管理画面へ戻る</a>
</form>
</div>
</body>
</html>