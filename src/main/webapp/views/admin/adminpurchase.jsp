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
			<th>ClientName</th>
			<th>Phone</th>
			<th>Adress</th>
			<th>Products</th>
			<th>Date</th>
		</tr>
		<c:forEach items="${Purchase }" var="item">
			<tr>
				<td><input type="radio" name="row" value="${item.id }"></td>
				<td>${item.client.name}</td>
				<td>${item.phone}</td>
				<td>${item.adress}</td>
				<td>
				<c:forEach items="${item.products}" var="item2">
					${item2.product.name}: ${item2.count} - ${item2.price*item2.count}$ <br/>
				</c:forEach>
				</td>
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
			ClientName: <input path="client" name="clientname" value="${target.client.name}" />
			Phone: <form:input path="phone" name="upphone" value="${target.phone }" />
			Adress: <form:input path="adress" name="upadress" value="${target.adress}" />
            <%--Products: <form:input path="products" name="upproducts" value="${target.products}" />--%>
			Date: <input type="text" name="update" value="${target.date}" disabled /><br/>
			<c:forEach items="${target.products}" var="item" varStatus="list" >
				Product: <input type="text" value="${item.product.name}" disabled />
				<form:input path="products[${list.index}].product.name" value="${item.product.name}" type="hidden" />
				Count: <form:input path="products[${list.index}].count" value="${item.count}"/>
				Price: <form:input path="products[${list.index}].price" value="${item.price}"/><br/>
			</c:forEach>
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