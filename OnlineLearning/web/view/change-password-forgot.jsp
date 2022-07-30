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
                                <div class="card-header">Login</div>
                                <div class="card-body">

                                <c:if test="${success != null}">
                                    <div class="forn-group row success">
                                        <div class="col-md-2"></div>
                                        <p class="col-md-10 margin-0">${success}</p>
                                    </div>
                                </c:if>

                                <form action="change-password-forgot" method="POST" onsubmit="return submitForm()">
                                    <div class="form-group row">
                                        <label for="new-password" class="col-md-4 col-form-label text-md-right">New password</label>
                                        <div class="col-md-6">
                                            <input type="password" id="password" class="form-control" name="new-password">
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

</body>

</html>