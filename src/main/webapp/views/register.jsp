<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css"/>
    <title>Register page</title>
</head>
<body>
<h2>Registration Form</h2>
<div class="regist">
    <form:form method="post" modelAttribute="user" action="registerGo" id="registerform">
        <div style="color: red; padding-top: 20px;">${regError }</div>
        <label id="jsEmailMsg" hidden></label>
        <div style="padding-top: 5px;">
            &emsp; &emsp; &emsp;&emsp;Email: <form:input path="email" id="email"
                                                    value="${baduser.email}"/>
        </div>

        <label id="jsLoginMsg" hidden></label>
        <div style="padding-top: 5px;">
            &emsp;&emsp; &emsp; &emsp;Login: <form:input path="name" id="login"
                                                    value="${baduser.name}"/>
        </div>

        <label id="jsPasswordMsg" hidden></label>
        <div style="padding-top: 5px;">
            &emsp;&emsp;&emsp;Password: <form:password path="password" id="password"/>
        </div>

        <label id="jsPassword2Msg" hidden></label>
        <div style="padding-top: 5px;">
            Repeat Password: <input type="password" id="passwordtwo"/>
        </div>
        <input type="submit" value="register" onclick="return regOk()">
    </form:form>
</div>
<script src="${pageContext.request.contextPath}/js/register.js"></script>
</body>
</html>