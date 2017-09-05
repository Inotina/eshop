var labelClientId = document.getElementById("labelId");
var labelPhone = document.getElementById("labelPhone");
var labelAdress = document.getElementById("labelAdress");
var clientId = document.getElementById("clientId");
var phone = document.getElementById("phone");
var adress = document.getElementById("adress");
var validClient = false;
var validPhone = false;
var validAdress = false;

clientId.onchange = validateClient;
function validateClient() {
    if (isNaN(clientId.value)){
        labelClientId.removeAttribute("hidden");
        labelClientId.innerHTML = "Invalid value";
    }else{
        validClient = true;
        labelClientId.innerHTML = "";
    }
}

phone.onchange = validatePhone;
function validatePhone() {
    if (phone.value.length < 1){
        labelPhone.removeAttribute("hidden");
        labelPhone.innerHTML = "Empty field";
    }else{
        validPhone = true;
        labelPhone.innerHTML = "";
    }
}

adress.onchange = validateAderess;
function validateAdress() {
    if (adress.value.length < 1){
        labelAdress.removeAttribute("hidden");
        labelAdress.innerHTML = "Empty field";
    }else{
        validAdress = true;
        labelAdress.innerHTML = "";
    }
}

function validatePurchase() {
    validateClient();
    validatePhone();
    validateAdress();
    return validClient && validAdress && validPhone;
}