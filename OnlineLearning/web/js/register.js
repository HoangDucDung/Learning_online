function submitForm() {
    
    var valuePassword = document.getElementById("password").value;
    var valueRePassword = document.getElementById("re-password").value;

    if (valuePassword.length < 6) {
        document.getElementById("error-password").innerHTML = "Password must be greater than 6 characters";
        return false;
    } else {
        document.getElementById("error-password").innerHTML = "";
    }
    
    if (valueRePassword !== valuePassword) {
        document.getElementById("error-re-password").innerHTML = "Confirm password not same with password";
        return false;
    } else {
        document.getElementById("error-re-password").innerHTML = "";
    }
    return true;
}