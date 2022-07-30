
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">

    <head>
        <jsp:include page="base-view/baseTag.jsp"></jsp:include>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
        
        <link rel="stylesheet" href="./css/login.css">

        <script src="./js/register.js"></script>

        <title>Register</title>
    </head>

    <body>

        <jsp:include page="base-view/headerLogin.jsp"></jsp:include>

        <main class="login-form">
            <div class="cotainer">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-header">Register</div>
                            <div class="card-body">                             
                                
                                <c:if test="${success != null}">
                                <div class="forn-group row success">
                                    <div class="col-md-2"></div>
                                    <p class="col-md-10 margin-0">${success}</p>
                                </div>
                                </c:if>

                                <form action="register" method="POST" onsubmit="return submitForm()">
                                    <!--Email Start-->
                                    <div class="form-group row">
                                        <label for="email-address" class="col-md-4 col-form-label text-md-right">E-Mail
                                            Address</label>
                                        <div class="col-md-6">
                                            <input type="text" id="email-address" class="form-control" name="email-address">
                                        </div>
                                    </div>

                                    <div class="form-group row margin-bottom-0">
                                        <p class="col-md-6 offset-md-4 error" id="error-email"></p>
                                    </div>
                                    <!--Email End-->
                                    
                                    <!--First Name Start-->
                                    <div class="form-group row">
                                        <label for="first-name" class="col-md-4 col-form-label text-md-right">First Name</label>
                                        <div class="col-md-6">
                                            <input type="text" id="first-name" class="form-control" name="first-name">
                                        </div>
                                    </div>

                                    <div class="form-group row margin-bottom-0" >
                                        <p class="col-md-6 offset-md-4 error" id="error-first-name" ></p>
                                    </div>
                                    <!--First Name End-->

                                    <!--Last Name Start-->
                                    <div class="form-group row">
                                        <label for="last-name" class="col-md-4 col-form-label text-md-right">Last Name</label>
                                        <div class="col-md-6">
                                            <input type="text" id="last-name" class="form-control" name="last-name">
                                        </div>
                                    </div>
                                    <div class="form-group row margin-bottom-0" >
                                        <p class="col-md-6 offset-md-4 error" id="error-last-name"></p>
                                    </div>
                                    <!--Last Name End-->

                                    <!--Gender Start-->
                                    <div class="form-group row">
                                        <label class="col-md-4 col-form-label text-md-right">Gender</label>
                                        <div class="col-md-6 gender-form" >
                                            <div class="display-flex">
                                                <label for="male" class="margin-auto-0">Male</label>
                                                <input type="radio" id="male" name="gender" value="Male" checked class="margin-auto-15px">
                                            </div>

                                            <div class="display-flex">
                                                <label for="female" class="margin-auto-0">Female</label>
                                                <input type="radio" id="female" name="gender" value="Female" class="margin-auto-15">
                                            </div>
                                        </div>                                       
                                    </div>   
                                    <div class="form-group row margin-bottom-0">
                                        <p class="col-md-6 offset-md-4 error"></p>
                                    </div>
                                    <!--Gender End-->
                                    
                                    <!--Phone Start-->
                                    <div class="form-group row">
                                        <label for="phone" class="col-md-4 col-form-label text-md-right">Phone Number</label>
                                        <div class="col-md-6">
                                            <input type="text" id="phone" class="form-control" name="phone">
                                        </div>
                                    </div>
                                    
                                    <div class="form-group row margin-bottom-0">
                                        <p class="col-md-6 offset-md-4 error" id="error-phone"></p>
                                    </div>
                                    <!--Phone End-->
                                    
                                    <!--Password Start-->
                                    <div class="form-group row">
                                        <label for="password" class="col-md-4 col-form-label text-md-right">Password</label>
                                        <div class="col-md-6">
                                            <input type="password" id="password" class="form-control" name="password">
                                        </div>
                                    </div>

                                    <div class="form-group row margin-bottom-0" >
                                        <p class="col-md-6 offset-md-4 error" id="error-password"></p>
                                    </div>
                                    <!--Phone End-->
                                    
                                    <!--Confirm Password Start-->
                                    <div class="form-group row">
                                        <label for="re-password" class="col-md-4 col-form-label text-md-right">Confirm Password</label>
                                        <div class="col-md-6">
                                            <input type="password" id="re-password" class="form-control" name="re-password">
                                        </div>
                                    </div>

                                    <div class="form-group row margin-bottom-0">
                                        <p class="col-md-6 offset-md-4 error" id="error-re-password"></p>
                                    </div>
                                    <!--Confirm Password End-->                               
                                    
                                    <c:if test="${error != null}">
                                        <div class="form-group row">
                                            <p class="col-md-6 offset-md-4 error">${error}</p>
                                        </div>
                                    </c:if>

                                    <div class="col-md-6 offset-md-4">
                                        <button type="submit" class="btn btn-primary" >
                                            Register
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