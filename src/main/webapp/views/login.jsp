 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css" />
<title>Login page</title>
</head>
<body>
	<div class="dlogin">
		<div class="login">
			<form:form method="post" modelAttribute="user" action="loginGo" id="loginform" >
					<div style="color: red; padding-top: 10px;">${invalidLogin }</div>
				<label id="labelLogin" style="color:red;" hidden></label>
				<div style="padding-top: 5px;">
					Login: &emsp; <form:input path="name" id="log" name="login"  />
				</div>
				<label id="labelPassword" style="color:red;" hidden></label>
				<div style="padding-top: 5px;">
					Password: <form:password path="password" id="pass" name="password" />
				</div>
				<input type="submit" onclick="return checkLogin()" value="Login">
			</form:form>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>