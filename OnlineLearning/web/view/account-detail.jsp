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
            <title>Account Detail</title>

        </head>

        <body>

            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Account"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Account"/>

                    <div class="container">
                        <div class="container-table">
                            <div class="table-content">
                                <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                                    <a class="back" href="../management/account"><i class="fa-solid fa-angle-left"></i>Back</a>
                                    <br/>
                                    <div id="container">
                                        <form action="profile" method="POST" onsubmit="return submitForm()" enctype="multipart/form-data" style="width: 50%;">
                                            <div id="table-header">
                                                <span>User's Information</span>
                                            </div>

                                            <div>
                                                <table>                   

                                                    <c:if test="${isNoti != null}">
                                                        <tr>
                                                            <td colspan="2">
                                                                <div id="notification">
                                                                    <span>Profile has been changed successfully!</span>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                    <tr>
                                                        <td>First name:</td>
                                                        <td><span>${Accountsearchby_ID.firstName}</span></td>
                                                    </tr>
                                                    <tr><td colspan="2" id="error-fname"></td></tr>

                                                    <tr>
                                                        <td>Last name:</td>
                                                        <td><span>${Accountsearchby_ID.lastName}</span></td>
                                                    </tr>
                                                    <tr><td colspan="2" id="error-lname" ></td></tr>

                                                    <tr>
                                                        <% Account account = (Account) request.getAttribute("Accountsearchby_ID");%>
                                                        <td>Gender:</td>                       
                                                        <td>
                                                            <input id="male" type="radio" name="gender" 
                                                                   <%= account.getGender().toString().equalsIgnoreCase("male") ? "checked" : ""%> value="male"  disabled="disabled"                                     
                                                                   />
                                                            <label for="male">Male</label>
                                                            <input id="female" type="radio" name="gender" 
                                                                   <%= account.getGender().toString().equalsIgnoreCase("female") ? "checked" : ""%> value="female"  disabled="disabled"
                                                                   />
                                                            <label for="female">Female</label>
                                                        </td>
                                                    </tr>
                                                    <tr><td colspan="2"></td></tr>

                                                    <tr>
                                                        <td>Phone:</td>
                                                        <td><span>${Accountsearchby_ID.phone}</span></td>
                                                    </tr>
                                                    <tr><td colspan="2" ></td></tr>

                                                    <tr>
                                                        <td>Email:</td>
                                                        <td><span>${Accountsearchby_ID.email}</span></td>
                                                    </tr>
                                                    <tr><td colspan="2"></td></tr>

                                                    <tr>
                                                        <td>Address:</td>
                                                        <td><span>${Accountsearchby_ID.address}</span></td>
                                                    </tr>
                                                    <tr><td colspan="2"></td></tr>

                                                    <tr>
                                                        <td>Role:</td>
                                                        <td><span>${Accountsearchby_ID.role.name}</span></td>
                                                    </tr>
                                                    <tr><td colspan="2"></td></tr>

                                                    <tr>
                                                        <td>Status</td>
                                                        <td><input type="radio" 
                                                                   <c:if test="${Accountsearchby_ID.status}">
                                                                       checked="checked" 
                                                                   </c:if>
                                                                   name="status" value="active" disabled="disabled"/> <label for="active">Active</label>
                                                            <input type="radio" 
                                                                   <c:if test="${not Accountsearchby_ID.status}">
                                                                       checked="checked"
                                                                   </c:if>
                                                                   name="status" value="deactive" disabled="disabled"/> <label for="deactive">Deactive</label>
                                                        </td>
                                                    </tr>
                                                    <tr><td colspan="2"></td></tr>

                                                </table>
                                            </div>
                                        </form>

                                        <div id="avatar">
                                            <img src="../img/${Accountsearchby_ID.profilePictureUrl}" alt="Avatar" width="250" height="250">
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