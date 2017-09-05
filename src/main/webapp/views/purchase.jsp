<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Purchase page</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css"/>
<body>
<h2>Super shop</h2>
<div>
    <h2>Your Cart</h2>
    <c:if test='${not empty Cart}'><br>
    </c:if>
    <c:if test='${not empty stockmessage}'>
        <p style="color: red;">${stockmessage }</p>
    </c:if>
    <div class="cart">
        <c:forEach items="${Cart}" var="item">
            <div style="padding-bottom: 5px;">
                <span>${item.key}</span>: ${item.value} <a
                    href="cart?name=${item.key }&cart=remove&id=${Product.id}"><input
                    type="button" value="Remove"/></a>
            </div>
        </c:forEach>
        <div style="padding-bottom: 7px;">
            <a href="cart?cart=clear&id=${Product.id}"><input type="button"
                                                              value="Clear"/></a>
        </div>
    </div>
    <c:if test="${not empty Cart}">
        <div class="regist">
            <form:form modelAttribute="purchase" action="purchase" method="post" id="purform">
                <div style="color: red; padding-top: 10px;">${errMessage}</div>
                <label id="labelId" style="color:red;" hidden></label>
                <div style="padding-top: 5px;">
                    Your ID: <form:input path="clientId" id="clientId" value="${clientId }"/>
                </div>
                <label id="labelPhone" style="color:red;" hidden></label>
                <div style="padding-top: 5px;">
                    Phone:&emsp;<form:input path="phone" id="phone" value="${phone }"/>
                </div>
                <label id="labelAdress" style="color:red;" hidden></label>
                <div style="padding-top: 5px;">
                    Adress:&emsp;<form:input path="adress" id="adress" value="${adress }"/>
                </div>
                <input type="submit" value="Purchase" onclick="return validatePurchase()">
            </form:form>
        </div>
    </c:if>
</div>
<a href="./">Back to Mainpage</a>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/purchase.js"></script>

</body>
</html>
