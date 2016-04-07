<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ホーム</title>
</head>
<body>
<div class="main-contents">

<div class="header">
	<c:if test="${empty loginUser }">
		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul> <%-- <ul>は順序のないリストを表示する歳際に使用する。順序のあるリスト表示は<ol> --%>
					<c:forEach items="${errorMessages}" var="messages"> <%-- 配列をループ処理 --%>
						<li><c:out value="${messages}" />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session"/>
		</c:if>

掲示板課題<br />
		<form action="login" method="post"><br />
			<label for="loginId">ログインID</label>
			<input name="loginId" id="loginId"/> <br />

			<label for="password">パスワード</label>
			<input name="password" type="password" id="password"/> <br />

			<input type="submit" value="ログイン" /> <br />
		</form>

	</c:if>
	<c:if test="${ not empty loginUser }">
		<a href="newMessage">新規投稿</a>
		<c:if test="${(loginUser.departmentId) == 1 }">
			<a href="userManagement">ユーザー管理</a>
		</c:if>
		<a href="logout">ログアウト</a>
		<div class="name"><h2><c:out value="${loginUser.name}"/></h2></div>
	</c:if>
</div>


<div class="messages">
	<c:if test="${ not empty loginUser }">

	<label for="category">カテゴリーで絞り込む</label>
		<select name="category">
			<option value="1"></option>

		</select><input type="submit" value="検索" /> <br />

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
	</c:if>
</div>

</div>
</body>
</html>