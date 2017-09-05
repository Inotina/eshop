<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Best eshop</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
</head>
<body>
	<div class="auth">
		<c:if test="${empty User}">
			<a href="${pageContext.request.contextPath}/login"><input type="button" value="Login" /></a>

			<a href="${pageContext.request.contextPath}/register"><input type="button" value="Register" /></a>
		</c:if>

		<c:if test='${not empty User}'>
			<p>Hi, ${User.name}</p>
			<a href="logout"><input type="button" value="Logout" /></a>
			<c:if test="${User.isAdmin == 'Y'}">
				<a href="${pageContext.request.contextPath}/admin"><input type="button" value="Admin" /></a>
			</c:if>
		</c:if>
	</div>
	<h2>Super shop</h2>
	<div>
		<h2>List of products</h2>
		<div class="prod">
			<c:forEach items="${productList}" var="item">
				<a class="product" href="product?id=${item.id}">${item.name}</a>
				<br>
			</c:forEach>
		</div>
	</div> 
</body>
</html>
