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
		<c:if test="${(loginUser.departmentName) == '総務人事' }">
			<a href="userManagement">ユーザー管理</a>
		</c:if>
		<a href="logout">ログアウト</a>
		<div class="name"><h2><c:out value="${loginUser.name}"/></h2></div>
	</c:if>
</div>

<div class="messages">
	<c:if test="${ not empty loginUser }">
		<form><br />
			<label for="category">カテゴリーで絞り込む</label>
			<select name="category">
				<option>カテゴリー選択</option>
				<c:forEach items="${category}" var="category">
					<option value="${category.category}">${category.category}</option>
				</c:forEach>
			</select>
		<input type="submit" value="検索" /> <br />
		</form>
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

		<c:forEach items="${messages}" var="message">
			<div class="message">
				<div class="name">
					<span class="name"><c:out value="${message.name}" /> </span>
				</div>
				<div class="category">カテゴリー：<c:out value="${message.category}" /> </div>
				<div class="title">件名：<c:out value="${message.title}" /> </div>
				<div class="text">本文：<c:out value="${message.text}" /> </div>
				<div class="date">投稿日時：<fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /> </div>

				<form action="newComment" method="post"><br />
					<label for="comment">コメントする</label> <br />
						<input name="messageId" type="hidden" value="${message.id}" id="messageId"/>
						<textarea name="commentText" cols="50" rows="4" class="comment-box" >${comment.text}</textarea> <br />
						<input type="submit" value="コメントを投稿" /> <br />
				</form>
			</div>
		</c:forEach>
	</c:if>
</div>

</div>
</body>
</html>