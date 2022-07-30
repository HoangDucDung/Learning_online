<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">

    <head>
        <jsp:include page="base-view/baseTag.jsp"></jsp:include>

            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

            <link rel="stylesheet" href="./css/login.css">
            <script src="./js/register.js"></script>
            <title>Login</title>
        </head>

        <body>

        <jsp:include page="base-view/headerLogin.jsp"></jsp:include>

            <main class="login-form">
                <div class="cotainer">
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="card">
                                <div class="card-header">Change password</div>
                                <div class="card-body">
                                <c:if test="${isNoti != null}">
                                    <div class="forn-group row success">
                                        <div class="col-md-2"></div>
                                        <p class="col-md-10 margin-0">Password has been changed successfully</p>
                                    </div>
                                </c:if>

                                <form action="modified" method="POST" onsubmit="return submitForm()">
                                    <input type="hidden" name="accountid" value="${accountid}"/>
                                    <div class="form-group row">
                                        <label for="new-password" class="col-md-4 col-form-label text-md-right">New password</label>
                                        <div class="col-md-6">
                                            <input type="password" id="password" class="form-control" name="newPassword">
                                        </div>
                                    </div>

                                    <div class="form-group row margin-bottom-0"">
                                        <p class="col-md-6 offset-md-4 error" id="error-password" ></p>
                                    </div>

                                    <div class="form-group row">
                                        <label for="confirm-password" class="col-md-4 col-form-label text-md-right">Confirm password</label>
                                        <div class="col-md-6">
                                            <input type="password" id="re-password" class="form-control" name="confirm-password">
                                        </div>
                                    </div>

                                    <div class="form-group row margin-bottom-0">
                                        <p class="col-md-6 offset-md-4 error" id="error-re-password" ></p>
                                    </div>                                                            

                                    <div class="col-md-6 offset-md-4">
                                        <button type="submit" class="btn btn-primary">
                                            Change
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </main>
    <script>
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