<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理</title>


<style>
body{
background: #f0e68c;
font-family: Meiryo;
}

div {
background: #ffffff;


}

</style>

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
</script>
</head>
<body>

<div class="header">
<a href="signup">ユーザー新規登録</a>
<a href="./">ホームへ戻る</a> <br />
</div>

<c:forEach items="${user}" var="user">
	<div class="user">
		<div class="user">
			<span class="user"><a href="userEdit?user_id=${user.id}"><c:out value="${user.name}" /></a></span>
		</div>
		<div class="user"><c:out value="${user.loginId}" /></div>

		<c:choose>
			<c:when test="${user.using == 1 }">
				<form action="userManagement" onSubmit="return offDisp()" method="post">
					<input name="user_id" type="hidden" value="${user.id}"/>
					<input name="using" type="submit" value="ON" /><br />
				</form>
			</c:when>
			<c:otherwise>
				<form action="userManagement" onSubmit="return onDisp()" method="post">
					<input name="user_id" type="hidden" value="${user.id}"/>
					<input name="using" type="submit" value="OFF" /><br />
				</form>
			</c:otherwise>
		</c:choose>
	</div>
</c:forEach>
</body>
</html>