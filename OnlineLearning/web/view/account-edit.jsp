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
                                        <form action="accountedit" method="POST" enctype="multipart/form-data">
                                            <div id="table-header">
                                                <span>User's Information</span>
                                            </div>

                                            <div>
                                                <table>                   


                                                    <tr>
                                                        <td>Account ID</td>
                                                        <td><input type="hidden"  id="fname" name="id" value="${Accountsearchby_ID.accountID}"  />${Accountsearchby_ID.accountID}</td>
                                                    </tr>
                                                    <tr><td colspan="2" id="error-fname"></td></tr>
                                                    <tr>
                                                        <td>First name</td>
                                                        <td><input pattern="^[^-\s][a-zA-Z0-9_\s-]*$" type="text" id="fname" name="firstName" value="${Accountsearchby_ID.firstName}"  required/></td>
                                                    </tr>
                                                    <tr><td colspan="2" id="error-fname"></td></tr>

                                                    <tr>
                                                        <td>Last name</td>
                                                        <td><input pattern="^[^-\s][a-zA-Z0-9_\s-]*$"type="text" id="lname" name="lastName" value="${Accountsearchby_ID.lastName}"  required/></td>
                                                    </tr>
                                                    <tr><td colspan="2" id="error-lname" ></td></tr>

                                                    <tr>
                                                        <% Account account = (Account) request.getAttribute("Accountsearchby_ID");%>
                                                        <td>Gender</td>                       
                                                        <td>
                                                            <input id="male" type="radio" name="gender" 
                                                                   <%= account.getGender().toString().equalsIgnoreCase("male") ? "checked" : ""%> value="male"                                      
                                                                   />
                                                            <label for="male">Male</label>
                                                            <input id="female" type="radio" name="gender" 
                                                                   <%= account.getGender().toString().equalsIgnoreCase("female") ? "checked" : ""%> value="female"  
                                                                   />
                                                            <label for="female">Female</label>
                                                        </td>
                                                    </tr>
                                                    <tr><td colspan="2"></td></tr>

                                                    <tr>
                                                        <td>Phone</td>
                                                        <td><input pattern="[0-9]{1,100}" type="text" name="phone" value="${Accountsearchby_ID.phone}"  required/></td>
                                                    </tr>
                                                    <tr><td colspan="2" ></td></tr>

                                                    <tr>
                                                        <td>Email</td>
                                                        <td><input type="text" name="email" value="${Accountsearchby_ID.email}" disabled="disabled"/></td>
                                                    </tr>
                                                    <tr><td colspan="2"></td></tr>

                                                    <tr>
                                                        <td>Address</td>
                                                        <td><input pattern="^[^-\s][a-zA-Z0-9_\s-]*$" type="text" name="address" value="${Accountsearchby_ID.address}" required/></td>
                                                    </tr>
                                                    <tr><td colspan="2"></td></tr>

                                                    <tr>
                                                        <td>Role</td>
                                                        <td><select id="countries"  name="role" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                                                <c:forEach items="${allRole_Name}" var="role">
                                                                    <option
                                                                        ${(Accountsearchby_ID.role.name eq role.name)?"selected=\"selected\"":""}
                                                                        value="${role.id}">${role.name}</option>
                                                                </c:forEach>
                                                            </select></td>
                                                    </tr>
                                                    <tr><td colspan="2"></td></tr>

                                                    <tr>
                                                        <td>Status</td>
                                                        <td><input type="radio" 
                                                                   <c:if test="${Accountsearchby_ID.status}">
                                                                       checked="checked" 
                                                                   </c:if>
                                                                   name="status" value="active" /> <label for="active">Active</label>
                                                            <input type="radio" 
                                                                   <c:if test="${not Accountsearchby_ID.status}">
                                                                       checked="checked"
                                                                   </c:if>
                                                                   name="status" value="deactive" /> <label for="deactive">Deactive</label>
                                                        </td>
                                                    </tr>
                                                    <tr><td colspan="2"></td></tr>
                                                    <tr>
                                                        <td>Avatar</td>
                                                        <td><input type="file" name="photo" value="" accept="image/*"/></td>
                                                    </tr>

                                                    <tr>
                                                        <td colspan="2"><input id="submit-btn" type="submit" value="Edit" /></td>
                                                    </tr>

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
            </div>
        </div>


        <!-- Bootstrap JavaScript -->    
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
    </body>

</html>