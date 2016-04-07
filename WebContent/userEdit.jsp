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

<form action="signup" method="post"><br />
	<label for="name">名前</label>
	<input name="name" value="${user.name}" id="name"/><br />

	<label for="loginId">ログインID</label>
	<input name="loginId"  value="${user.loginId}" id="loginId"/><br />

	<label for="password">パスワード(登録用)</label>
	<input name="password" type="password" id="password"/> <br />

	<label for="confirm">パスワード(確認用)</label>
	<input name="confirm" type="password" id="confirm"/> <br />

	<label for="branchId">所属支店</label> <%-- プルかラジオ --%>
	<input name="branchId"  value="${user.branchId}" id="branchId"/><br />

	<label for="departmentId">部署・役職</label> <%-- プルかラジオ --%>
	<input name="departmentId"  value="${user.departmentId}" id="departmentId"/><br />

	<input type="submit" value="変更" /> <br />
<a href="userManagement.jsp">管理画面へ戻る</a>
</form>

</div>


</body>
</html>