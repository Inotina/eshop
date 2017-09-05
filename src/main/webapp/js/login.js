function checkLogin() {
    var login = document.getElementById("log").value;
    var password = document.getElementById("pass").value;
    var labelLogin = document.getElementById("labelLogin");
    var labelPassword = document.getElementById("labelPassword");
    var loginOk = true;
    if (login.length < 1) {
        labelLogin.innerHTML = "Login field is empty";
        labelLogin.removeAttribute("hidden");
        loginOk = false;
    } else {
        labelLogin.innerHTML = "";
    }
    if (password.length < 1) {
        labelPassword.innerHTML = "Password field is empty";
        labelPassword.removeAttribute("hidden");
        loginOk = false;
    } else {
        labelPassword.innerHTML = "";
    }
    return loginOk;
}


