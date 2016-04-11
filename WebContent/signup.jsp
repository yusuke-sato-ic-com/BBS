<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー新規登録</title>
</head>
<body>

<%--
 TODO
・本社総務部のみアクセス可能。権限を持たないユーザーが開いた場合、ホーム画面に遷移し適切なメッセージを表示
・登録が正常に完了したら、ユーザー管理画面に遷移
・登録が正常に完了しなければ、適切なバリデーションメッセージを表示
 --%>

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

<form action="signup" method="post"><br />
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
		<option>支店選択</option>
			<c:forEach items="${branch}" var="branch">
				<option value="${branch.branchId}">${branch.branchName}</option>
			</c:forEach>
		</select>  <br />

	<label for="department">所属部署</label>
	<select name="department">
		<option>部署選択</option>
			<c:forEach items="${department}" var="department">
				<option value="${department.departmentId}">${department.departmentName}</option>
			</c:forEach>
		</select>  <br />
	<input type="submit" value="登録" /> <br />
<a href="userManagement">戻る</a>
</form>
</div>
</body>
</html>