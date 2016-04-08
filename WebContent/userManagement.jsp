<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理</title>
</head>
<body>

<div class="header">
<a href="signup">ユーザー新規登録</a>
<a href="./">ホームへ戻る</a> <br />
</div>

ユーザー管理画面実装中<br />
利用状況と編集画面<br />

<c:forEach items="${user}" var="user">
		<div class="user">
			<div class="user">
				<span class="user"><a href="userEdit?user_id=${user.id}"><c:out value="${user.name}" /></a></span>
			</div>
			<div class="user"><c:out value="${user.loginId}" /> </div>
			<%-- 利用状況を表示する --%>
			<form action="" method="post">
				<input type="submit" value="ON" /> <br />
			</form>
			</div>

	</c:forEach>
</body>
</html>