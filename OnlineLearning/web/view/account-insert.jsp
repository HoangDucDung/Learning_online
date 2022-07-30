<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.Account"%>
<%@page import="model.Gender" %>

<!DOCTYPE html>
<html lang="en">

    <head>

        <jsp:include page="base-view/baseTagAdmin.jsp"></jsp:include>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <!-- Bootstrap CSS -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
                  integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
            <!--Font Awesome-->
            <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
            <!--Jquery-->

            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
            <link href="../css/setting.css" rel="stylesheet" type="text/css"/>
            <link href="../css/profile.css" rel="stylesheet" type="text/css"/>
            <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
            <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
            <script src="../js/setting.js" type="text/javascript"></script>
            <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />
            <script src="https://unpkg.com/flowbite@1.4.7/dist/flowbite.js"></script>
            <script src="../js/dashboard.js"></script>
            <script src="../js/table.js"></script>
            <link rel="stylesheet" href="../css/login.css">
            <script src="../js/register.js"></script>
            <title>Account Detail</title>
            <style>
                .back-btn {
                    text-decoration: none;
                    font-weight: 500;
                    margin-left: 30px;
                }

                .back-btn a {
                    text-decoration: none;
                }
            </style>
        </head>

        <body>

            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Account"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Manage Account"/>

                    <div class="container">
                        <div class="container-table">
                            <div class="back-btn text-primary" >
                                <i class="fa-solid fa-angle-left"></i>
                                <a href="account">Back</a>
                            </div>
                            <div class="table-content">
                                <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                                    <div class="col-md-8">
                                        <c:if test="${success != null}">
                                            <div class="forn-group row success">
                                                <div class="col-md-2"></div>
                                                <p class="col-md-10 margin-0">${success}</p>
                                            </div>
                                        </c:if>
                                        <form action="accountinsert" method="POST" onsubmit="return submitForm()" enctype="multipart/form-data">
                                            <!--Email Start-->
                                            <div class="form-group row">
                                                <label for="email-address" class="col-md-4 col-form-label text-md-right">E-Mail
                                                    Address</label>
                                                <div class="col-md-6">
                                                    <input pattern="^[^-\s][a-zA-Z0-9@gmail.com_\s-]*$" type="text" id="email-address" class="form-control" name="email-address" required>
                                                </div>
                                            </div>

                                            <div class="form-group row margin-bottom-0">
                                                <p class="col-md-6 offset-md-4 error" id="error-email"></p>
                                            </div>
                                            <!--Email End-->

                                            <!--First Name Start-->
                                            <br/>
                                            <div class="form-group row">
                                                <label for="first-name" class="col-md-4 col-form-label text-md-right">First Name</label>
                                                <div class="col-md-6">
                                                    <input  pattern="^[^-\s][a-zA-Z0-9_\s-]*$" type="text" id="first-name" class="form-control" name="first-name" required>
                                                </div>
                                            </div>

                                            <div class="form-group row margin-bottom-0" >
                                                <p class="col-md-6 offset-md-4 error" id="error-first-name" ></p>
                                            </div>
                                            <!--First Name End-->

                                            <!--Last Name Start-->
                                            <br/>
                                            <div class="form-group row">
                                                <label for="last-name" class="col-md-4 col-form-label text-md-right">Last Name</label>
                                                <div class="col-md-6">
                                                    <input  pattern="^[^-\s][a-zA-Z0-9_\s-]*$" type="text" id="last-name" class="form-control" name="last-name" required>
                                                </div>
                                            </div>
                                            <div class="form-group row margin-bottom-0" >
                                                <p class="col-md-6 offset-md-4 error" id="error-last-name"></p>
                                            </div>
                                            <br/>

                                            <div class="form-group row">
                                                <label for="last-name" class="col-md-4 col-form-label text-md-right">Choose a Role</label>
                                                <div class="col-md-6">
                                                    <select required id="countries"  name="role" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                                        <c:forEach items="${allRole_Name}" var="role">
                                                            <option
                                                                ${(Accountsearchby_ID.role.name eq role.name)?"selected=\"selected\"":""}
                                                                value="${role.id}">${role.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                            </div>
                                            <div class="form-group row margin-bottom-0">
                                                <p class="col-md-6 offset-md-4 error"></p>
                                            </div>
                                            <!--Last Name End-->

                                            <!--Gender Start-->
                                            <br/>
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
                                            <br/>
                                            <div class="form-group row">
                                                <label for="phone" class="col-md-4 col-form-label text-md-right">Phone Number</label>
                                                <div class="col-md-6">
                                                    <input pattern="[0-9]{1,100}" type="text" id="phone" class="form-control" name="phone" required>
                                                </div>
                                            </div>

                                            <div class="form-group row margin-bottom-0">
                                                <p class="col-md-6 offset-md-4 error" id="error-phone"></p>
                                            </div>

                                            <br/>
                                            <div class="form-group row">
                                                <label for="address" class="col-md-4 col-form-label text-md-right">Address</label>
                                                <div class="col-md-6">
                                                    <input pattern="^[^-\s][a-zA-Z0-9_\s-]*$" type="text" id="address" class="form-control" name="address" required>
                                                </div>
                                            </div>

                                            <div class="form-group row margin-bottom-0">
                                                <p class="col-md-6 offset-md-4 error" id="error-address"></p>
                                            </div>

                                            <br/>
                                            <div class="form-group row">
                                                <label class="col-md-4 col-form-label text-md-right">Status</label>
                                                <div class="col-md-6 gender-form" >
                                                    <div class="display-flex">
                                                        <label for="active" class="margin-auto-0">Active</label>
                                                        <input type="radio" id="active" name="status" value="Active" checked class="margin-auto-15px">
                                                    </div>

                                                    <div class="display-flex">
                                                        <label for="deactive" class="margin-auto-0">Deactive</label>
                                                        <input type="radio" id="deactive" name="status" value="Deactive" class="margin-auto-15">
                                                    </div>
                                                </div>                                       
                                            </div>  
                                            <br/>
                                            <div class="form-group row">
                                                <label for="avarta" class="col-md-4 col-form-label text-md-right">Avatar</label>
                                                <div class="col-md-6">
                                                    <input type="file" name="photo" id="file" class="inputfile" data-multiple-caption="{count} files selected" accept="image/*" required >
                                                </div>
                                            </div>

                                            <div class="form-group row margin-bottom-0">
                                                <p class="col-md-6 offset-md-4 error" id="error-re-password"></p>
                                            </div>
                                            <br/>
                                            <c:if test="${error != null}">
                                                <div id="infor" class="flex p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg dark:bg-red-200 dark:text-red-800" role="alert">
                                                    <svg class="inline flex-shrink-0 mr-3 w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 id="infor" 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"></path></svg>
                                                    <div>
                                                        <span class="font-medium">${error}</span>
                                                    </div>
                                                </div>
                                            </c:if>

                                            <div class="col-md-5 offset-md-5">
                                                <input id="submit-btn" type="submit" value="Add" />
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- Bootstrap JavaScript -->    
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
    </body>

</html>