<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link type="text/css" rel="stylesheet" href="../style/admin.css"/>
    <title>Product management</title>
</head>
<body>
<div>
    <h2>All Products</h2>
    <div>
        <form:form action="${pageContext.request.contextPath}/admin/products" method="post">
            <table border=1 class="tab">
                <tr>
                    <th>Select</th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>In Stock</th>
                </tr>
                <c:forEach items="${Products }" var="item">
                    <tr>
                        <td><input type="radio" name="row" value="${item.id }"></td>
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>${item.price}</td>
                        <td>${item.count}</td>
                    </tr>
                </c:forEach>
            </table>
            <input style="margin-left:100px;margin-top:10px;" type="submit" value="Select"/>
        </form:form>
    </div>
    <div style="margin-left:50px;">
        <h2>Add Product Form</h2>
        <p style="color: green;">${addMessage }</p>
        <p style="color: red;">${errAddMessage }</p>

        <form:form modelAttribute="newProduct" method="post"
                   action="${pageContext.request.contextPath}/admin/productadd" id="addform">
            Name: <form:input path="name" name="name" value="${newProduct.name}"/>
            Price: <form:input path="price" name="price" value="${newProduct.price }"/>
            Stock: <form:input path="count" name="count" value="${newProduct.count}"/>
            <input type="submit" name="bt" value="Add">
            <br>
        </form:form>
    </div>
    <div style="margin-left:50px;">
        <p style="color: green;">${message }</p>
        <p style="color: red;">${errMessage }</p>

        <c:if test='${not empty targetp }'>
            <h2>Update\Delete Form</h2>
            <form:form modelAttribute="targetp" method="post"
                       action="${pageContext.request.contextPath}/admin/productchange" id="upform">
                Id: <input type="text" name="upid" value="${targetp.id}" disabled/>
                <input type="hidden" name="id" value="${targetp.id}"/>
                Name: <form:input path="name" name="upname" value="${targetp.name}"/>
                Name: <input type="text" hidden name="oldname" value="${targetp.name}"/>
                Price: <form:input path="price" name="upprice" value="${targetp.price }"/>
                Stock: <form:input path="count" name="upcount" value="${targetp.count}"/> <input
                    type="submit" name="bt" value="Update"/> <input type="submit"
                                                                    name="bt" value="Delete"/>
            </form:form>
        </c:if>
    </div>

    <div class="refer">
        <a href="${pageContext.request.contextPath}/admin/">Back to main admin page</a><br>
        <a href="${pageContext.request.contextPath}/">Back to main page</a>
    </div>
</body>
</html>