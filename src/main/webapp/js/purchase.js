
var labelPhone = document.getElementById("labelPhone");
var labelAdress = document.getElementById("labelAdress");
var phone = document.getElementById("phone");
var adress = document.getElementById("adress");
var validPhone = false;
var validAdress = false;

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
    validatePhone();
    validateAdress();
    return validAdress && validPhone;
}