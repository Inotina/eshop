var isOkLogin = false;
var isOkEmail = false;
var isOkPassword = false;
var isOkPassword2 = false;
var regLogin = document.getElementById("login");
var regEmail = document.getElementById("email");
var regPassword = document.getElementById("password");
var regPassword2 = document.getElementById("passwordtwo");

regLogin.onchange = validLogin;
function validLogin() {
    var loginValue = regLogin.value;
    var jsLoginMsg = document.getElementById("jsLoginMsg")
    if (loginValue.length < 4) {
        jsLoginMsg.innerHTML = "Login should contain more than 3 characters.";
        jsLoginMsg.style.color = "red";
    } else if (loginValue.indexOf(" ") >= 0) {
        jsLoginMsg.innerHTML = "Spaces are not allowed.";
        jsLoginMsg.style.color = "red";
    } else {
        jsLoginMsg.innerHTML = "Login is ok!";
        jsLoginMsg.style.color = "green";
        isOkLogin = true;
    }
    jsLoginMsg.removeAttribute("hidden");
}

regEmail.onchange = validEmail;
function validEmail() {
    var emailValue = regEmail.value;
    var jsEmailMsg = document.getElementById("jsEmailMsg")
    if (emailValue.length < 5) {
        jsEmailMsg.innerHTML = "Email should contain more than 4 characters.";
        jsEmailMsg.style.color = "red";
    } else if (emailValue.indexOf(" ") >= 0 || emailValue.indexOf("@") < 0) {
        jsEmailMsg.innerHTML = "Email is not valid.";
        jsEmailMsg.style.color = "red";
    } else {
        jsEmailMsg.innerHTML = "Email is ok!";
        jsEmailMsg.style.color = "green";
        isOkEmail = true;
    }
    jsEmailMsg.removeAttribute("hidden");
}

regPassword.onchange = validPass;
function validPass() {
    var passwordValue = regPassword.value;
    var jsPasswordMsg = document.getElementById("jsPasswordMsg")
    if (passwordValue.length < 8) {
        jsPasswordMsg.innerHTML = "Password should contain more than 7 characters.";
        jsPasswordMsg.style.color = "red";
    } else if (passwordValue.indexOf(" ") >= 0) {
        jsPasswordMsg.innerHTML = "Spaces are not allowed.";
        jsPasswordMsg.style.color = "red";
    } else {
        jsPasswordMsg.innerHTML = "Password is ok!";
        jsPasswordMsg.style.color = "green";
        isOkPassword = true;
    }
    jsPasswordMsg.removeAttribute("hidden");
    pass();
}

regPassword2.onchange = pass;
function pass() {
    var password2Value = regPassword2.value;
    var jsPassword2Msg = document.getElementById("jsPassword2Msg")
    if (password2Value.length > 7 && password2Value === regPassword.value) {
        jsPassword2Msg.innerHTML = "Password match!";
        jsPassword2Msg.style.color = "green";
        isOkPassword2 = true;
    } else {
        jsPassword2Msg.innerHTML = "Password doesn't match.";
        jsPassword2Msg.style.color = "red";
    }
    jsPassword2Msg.removeAttribute("hidden");
}

function regOk() {
    validEmail();
    validLogin();
    validPass();
    pass();
    return isOkLogin && isOkEmail && isOkPassword && isOkPassword2;
}