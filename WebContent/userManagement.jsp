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
<a href="signup.jsp">ユーザー新規登録</a>
<a href="./">ホームへ戻る</a> <br />
</div>

ユーザー管理画面実装中<br />

<c:forEach items="${user}" var="user">
		<div class="user">
			<div class="user">
				<span class="user"><c:out value="${user.name}" /> </span>
			</div>
			<div class="user"><c:out value="${user.loginId}" /> </div>
			<%-- 利用状況を表示する --%>
		</div>
	</c:forEach>
</body>
</html>