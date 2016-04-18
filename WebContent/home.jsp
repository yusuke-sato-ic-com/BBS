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

<style>
body{
background: #f0e68c;
font-family: Meiryo;
}

div {
background: #ffffff;
text-align: center;

}

</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/i18n/jquery.ui.datepicker-ja.min.js"></script>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/redmond/jquery-ui.css" >
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/ui-lightness/jquery-ui.css" >

<script>
$(function () {
    $("#fromDate").datepicker({
    	dateFormat:"yy-mm-dd",
    	maxDate: new Date(),
        onSelect: function (selectedDate) {
            $("#fromDate").datepicker("option", "showOn", 'button');
            $("#fromDate").datepicker("option", option, selectedDate);

        }
    });
    $("#toDate").datepicker({
    	dateFormat:"yy-mm-dd",
    	maxDate: new Date(),
        onSelect: function (selectedDate) {
            $("#toDate").datepicker("option", "showOn", 'button');
            $("#toDate").datepicker("option", option, selectedDate);
        }
    });
});
</script>

</head>
<body>

<div class="main-contents">

<div class="header">
	<c:if test="${empty loginUser }">
		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul> <%-- <ul>は順序のないリストを表示する歳際に使用する。順序のあるリスト表示は<ol> --%>
					<c:forEach items="${errorMessages}" var="messages"> <%-- 配列をループ処理 --%>
						<h3><li><c:out value="${messages}" /><h3>
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session"/>
		</c:if>
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
	<form><br />
		<label for="fromDate">日付範囲</label>
		<input name="fromDate" type="text" id="fromDate" placeholder="クリックしてください"/>

		<label for="toDate">～</label>
		<input name="toDate" type="text" id="toDate" placeholder="クリックしてください"/> <br >

		<label for="category">カテゴリー</label>
		<select name="category">
			<option>すべて</option>
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
			<c:if test="${message.userId == loginUser.id || loginUser.departmentId == 2 || loginUser.departmentId == 3 && loginUser.branchId == message.branchId}">
				<form method="post"><br />
					<input name="loginUserId" type="hidden" value="${loginUser.id}" id="loginUserId"/>
					<input name="messageId" type="hidden" value="${message.id}" id="messageId"/>
					<input type="submit" value="投稿を削除" /> <br />
				</form>
			</c:if>

			<c:forEach items="${comments}" var="comment">
				<c:if test="${message.id == comment.messageId }">
					<div class="comment">
						<div class="name">コメント投稿者：<c:out value="${comment.name}"/></div>
						<div class="text">コメント：<c:out value="${comment.text}" /> </div>
						<div class="date">投稿日時：<fmt:formatDate value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
						<c:if test="${comment.userId == loginUser.id || loginUser.departmentId == 2 || loginUser.departmentId == 3 && loginUser.branchId == comment.branchId}">
							<form method="post"><br />
								<input name="loginUserId" type="hidden" value="${loginUser.id}" id="loginUserId"/>
								<input name="commentId" type="hidden" value="${comment.id}" id="commentId"/>
								<input type="submit" value="コメントを削除" /> <br />
							</form>
						</c:if>
						<br />
					</div>
				</c:if>
			</c:forEach>

			<form action="newComment" method="post"><br />
				<label for="comment">コメントする</label> <br />
				<input name="messageId" type="hidden" value="${message.id}" id="messageId"/>
				<textarea name="text" cols="50" rows="4" class="comment-box" >${comment.text}</textarea> <br />
				<input type="submit" value="コメントを投稿" /> <br />
			</form>
		</div>
	</c:forEach>
</div>

</div>
</body>
</html>