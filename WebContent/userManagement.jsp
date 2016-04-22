<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./css/style.css">
<title>ユーザー管理</title>


<script type="text/javascript">
<!--
function offDisp(){
	if(window.confirm('本当に停止してよろしいですか？')){
		return true;
	} else {
		return false;
	}
}
// -->

<!--
function onDisp(){
	if(window.confirm('本当に復活させてよろしいですか？')){
		return true;
	} else {
		return false;
	}
}
// -->

<!--
function deleteDisp(){
	if(window.confirm('本当に削除してよろしいですか？')){
		return true;
	} else {
		return false;
	}
}
// -->
</script>
</head>
<body>

<div id="menu">
<p><a href="signup">ユーザー新規登録</a>
<a href="./">ホームへ戻る</a></p> <br />
<div class="home-name"><c:out value="${loginUser.name}"/>&nbsp;</div>
</div>

<div id="management-field">
<p class="management-field-title">ユーザー管理画面<br /></p>
<c:forEach items="${user}" var="user">
	<div class="user">
		<div class="user">
			<p><a href="userEdit?user_id=${user.id}"><c:out value="${user.name}" /></a>
			<c:out value="${user.loginId}" /></p>
			<p><c:out value="${user.branchName}" />	<c:out value="${user.departmentName}" /></p>
			<form action="userManagement" onSubmit="return deleteDisp()" method="post">
				<p><input name="user_id" type="hidden" value="${user.id}"/>
				<p class="management-delete-submit"><input name="delete-user" type="submit" value="削除" /></p>
			</form>
			<c:choose>
				<c:when test="${user.using == 1 }">
					<form action="userManagement" onSubmit="return offDisp()" method="post">
						<p><input name="user_id" type="hidden" value="${user.id}"/></p>
						<p class="management-submit"><input name="using" type="submit" value="ON" /></p>
					</form>
				</c:when>
				<c:otherwise>
					<form action="userManagement" onSubmit="return onDisp()" method="post">
						<p><input name="user_id" type="hidden" value="${user.id}"/></p>
						<p class="management-submit"><input name="using" type="submit" value="OFF" /></p>
					</form>
				</c:otherwise>
			</c:choose>

		</div>

	</div>
</c:forEach>
</div>
</body>
</html>