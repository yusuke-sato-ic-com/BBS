<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./css/style.css">
<title>ホーム</title>

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
	<c:if test="${empty loginUser }">
		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul> <%-- <ul>は順序のないリストを表示する歳際に使用する。順序のあるリスト表示は<ol> --%>
					<c:forEach items="${errorMessages}" var="messages"> <%-- 配列をループ処理 --%>
						<center><font size="4" color="#ff0000"><c:out value="${messages}" /></font></center>
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session"/>
		</c:if>
	</c:if>

	<div id="menu">
		<c:if test="${ not empty loginUser }">
			<p><a href="newMessage">新規投稿</a>&nbsp;
			<c:if test="${(loginUser.departmentId) == 1 }">
				<a href="userManagement">ユーザー管理</a>&nbsp;
			</c:if>
			<a href="logout">ログアウト</a></p>&nbsp;
			<div class="home-name"><c:out value="${loginUser.name}"/>&nbsp;</div>
		</c:if>
	</div>
</div>

<div class="messages">

	<div id="refine-search">
		<form>
			<p><label for="fromDate">日付範囲</label>
			<input name="fromDate" type="text" id="fromDate" placeholder="From"/>
			<label for="toDate">～</label>
			<input name="toDate" type="text" id="toDate" placeholder="To"/>
			<label for="category">カテゴリー</label>
			<select name="category">
				<option>すべて</option>
					<c:forEach items="${category}" var="category">
						<option value="${category.category}">${category.category}</option>
					</c:forEach>
			</select></p>
			<p class="home-submit"><input type="submit" value="検索" /></p>
		</form>
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

	<div id="message-comment">
	<c:forEach items="${messages}" var="message">
		<div class="message">
			<div class="message-name">
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
					<p class="home-submit"><input type="submit" value="投稿を削除" /></p>
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
								<p class="home-submit"><input type="submit" value="コメントを削除" /></p>
							</form>
						</c:if>
						<br />
					</div>
				</c:if>
			</c:forEach>

			<form action="newComment" method="post"><br />
				<label for="comment">コメントする</label> <br />
				<input name="messageId" type="hidden" value="${message.id}" id="messageId"/>
				<textarea name="text" cols="68" rows="5" class="comment-box" >${comment.text}</textarea> <br />
				<p class="home-submit"><input type="submit" value="コメントを投稿" /></p>
			</form>
		</div>
	</c:forEach>
	</div>
</div>

</body>
</html>