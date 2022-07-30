<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <jsp:include page="base-view/baseTag.jsp"></jsp:include>       

            <link href="css/change-password.css" rel="stylesheet" type="text/css"/>

            <title>Change password</title>

        </head>

        <body>

        <jsp:include page="base-view/headerUser.jsp"></jsp:include>       

            <div id="container">
                <form action="change-password" method="POST" onsubmit="return submitForm()">
                    <div id="table-header">
                        <span>Change password</span>
                    </div>

                    <div>
                        <table>   

                        <c:if test="${isNoti != null}">
                            <tr>
                                <td colspan="2">
                                    <div id="notification">
                                        <span>Password has been changed successfully!</span>
                                    </div>
                                </td>
                            </tr>
                        </c:if>

                        <tr>
                            <td>Current password</td>
                            <td><input type="password" name="" value="" id="currentPassword"/></td>
                        </tr>
                        <tr><td colspan="2" id="error-current-password" class="error"></td></tr>

                        <tr>
                            <td>New password</td>
                            <td><input type="password" name="newPassword" value="" id="password"/></td>
                        </tr>
                        <tr><td colspan="2" id="error-password" class="error"></td></tr>

                        <tr>
                            <td>Confirm password</td>
                            <td><input type="password" name="" value="" id="re-password"/></td>
                        </tr>
                        <tr><td colspan="2" id="error-re-password" class="error"></td></tr>
                        <tr>
                            <td colspan="2"><input id="submit-btn" type="submit" value="Save" /></td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>

        <jsp:include page="base-view/footerUser.jsp"></jsp:include>

            <script>
                function submitForm() {
                    var currentPassword = document.getElementById("currentPassword").value;
                    var valuePassword = document.getElementById("password").value;
                    var valueRePassword = document.getElementById("re-password").value;
                    if (currentPassword != ${sessionScope.account.password}) {
                        document.getElementById("error-current-password").innerHTML = "Current password not same as old password";
                        return false;
                    } else {
                        document.getElementById("error-current-password").innerHTML = "";
                    }
                    if (valuePassword.length < 6) {
                        document.getElementById("error-password").innerHTML = "Password must be greater than 6 characters";
                        return false;
                    } else {
                        document.getElementById("error-password").innerHTML = "";
                    }
                    if (valueRePassword !== valuePassword) {
                        document.getElementById("error-re-password").innerHTML = "Confirm password not same as password";
                        return false;
                    } else {
                        document.getElementById("error-password").innerHTML = "";
                    }
                    return true;
                }            
        </script>
    </body>

</html>