<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link type="text/css" rel="stylesheet" href="../style/admin.css" />

<title>Purchase management</title>
</head>
<body>
    <div>
	<h2>All Purchases</h2>
	<form:form action="${pageContext.request.contextPath}/admin/purchases" method="post">
	<table border=1>
		<tr>
			<th>Select</th>
			<th>ClientID</th>
			<th>Phone</th>
			<th>Adress</th>
			<th>Products</th>
			<th>Date</th>
		</tr>
		<c:forEach items="${Purchase }" var="item">
			<tr>
				<td><input type="radio" name="row" value="${item.id }"></td>
				<td>${item.clientId}</td>
				<td>${item.phone}</td>
				<td>${item.adress}</td>
				<td>${item.products}</td>
				<td>${item.date}</td>
			</tr>
		</c:forEach>
	</table>
	<input style="margin-left:100px;margin-top:10px;" type="submit" value="Select"/>
	</form:form>
    </div>
    <div style="margin-left:50px;">
		<p style="color: green;">${message }</p>
	<c:if test='${not empty target }'>
		<h2>Update\Delete Form</h2>
		<p style="color: red;">${errMessage }</p>
		<form:form modelAttribute="target" method="post" action="${pageContext.request.contextPath}/admin/purchasechange" id="upform">
			PurchaseId: <input type="text" name="uppurchaseid" value="${target.id}" disabled />
			<input type="hidden" name="id" value="${target.id}"/>
			ClientId: <form:input path="clientId" name="upclientid" value="${target.clientId}" />
			Phone: <form:input path="phone" name="upphone" value="${target.phone }" />
			Adress: <form:input path="adress" name="upadress" value="${target.adress}" />
            Products: <form:input path="products" name="upproducts" value="${target.products}" />
			
			Date: <input type="text" name="update" value="${target.date}" disabled />
			<form:input path="date" type="hidden" name="hupdate" value="${target.date}" />
			<input type="submit" name="bt" value="Update"/>
            <input type="submit" name="bt" value="Delete"/>
		</form:form>
	</c:if>
    </div>

<div class="refer">
	<a href="${pageContext.request.contextPath}/admin">Back to main admin page</a><br>
	<a href="${pageContext.request.contextPath}/">Back to main page</a>
    </div>
</body>
</html>