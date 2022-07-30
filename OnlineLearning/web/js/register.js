function submitForm() {
    var valueEmail = document.getElementById("email-address").value;
    var valueFName = document.getElementById("first-name").value;
    var valueLName = document.getElementById("last-name").value;
    var valuePhone = document.getElementById("phone").value;
    var valuePassword = document.getElementById("password").value;
    var valueRePassword = document.getElementById("re-password").value;

    if (valueEmail.trim() === "") {
        document.getElementById("error-email").innerHTML = "Email must not be empty";
        return false;
    } else {
        document.getElementById("error-email").innerHTML = "";
    }

    if (valueFName.trim() === "") {
        document.getElementById("error-first-name").innerHTML = "First name must not be empty";
        return false;
    } else {
        document.getElementById("error-first-name").innerHTML = "";
    }

    if (valueLName.trim() === "") {
        document.getElementById("error-last-name").innerHTML = "Last name must not be empty";
        return false;
    } else {
        document.getElementById("error-last-name").innerHTML = "";
    }

    if (valuePhone.trim() === "") {
        document.getElementById("error-phone").innerHTML = "Phone number must not be empty";
        return false;
    } else {
        document.getElementById("error-phone").innerHTML = "";
    }

    if (valuePassword.trim().length < 6) {
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