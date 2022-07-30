function submitForm() {
    var valuePassword = document.getElementById("password").value;

    if(valuePassword.length < 6) {
        document.getElementById("error-password").innerHTML = "Password must be greater than 6 characters";
        return false;
    }
    else {
        document.getElementById("error-password").innerHTML = "";
    }
    return true;
}