<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manage Account</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link href="../css/setting.css" rel="stylesheet" type="text/css"/>
        <script src="../js/setting.js" type="text/javascript"></script>
        <script src="../js/account-management.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css">
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>
        <style>
            .toggle{
                font-size: 30px;
                text-align: center;
            }

            .toggle button{
                border: 0;
                color: #0d6efd;
                background: none;
            }
        </style>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Account"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Manage Account"/>

                    <div class="container">
                        <div class="container-table">
                            <div class="table-content">
                                <div class="search ">

                                    <form class="form-search d-flex justify-content-between" action="account" method="GET"> 
                                        <a href="accountinsert" class="btn btn-primary"><i class="fa-solid fa-plus"></i> Add User</a>
                                        <div class="d-flex">
                                            <select name="cid"  class="form-select form-select-sm" aria-label=".form-select-sm example">
                                                <option value="-1" >Select Role</option>
                                                <c:forEach items="${allRoleName}" var="role" >
                                                    <option value="${role.id}" ${id == role.id ? "selected" : ""}>${role.name}</option>
                                                </c:forEach>
                                            </select>
                                            <select name="status"  class="form-select form-select-sm" aria-label=".form-select-sm example">
                                                <option value="-1" >Select Status</option>
                                                <option value="false" ${ status == "false" ? "selected" : ""}>Deactive</option>
                                                <option value="true" ${ status == "true" ? "selected" : ""}>Active</option>    
                                            </select>
                                            <button type="submit" class="btn btn-primary">Filter</button>
                                        </div>
                                    </form>  
                                </div>
                                <div class="align-content-center">
                                    <table id="table" class="table table-striped text-center" border="0">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>FullName</th>
                                                <th>Email</th>
                                                <th>Phone</th>
                                                <th>Role</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody id="myTable">
                                            <c:forEach items="${listAccount}" var="account">
                                                <tr>
                                                    <td>${account.accountID}</td>
                                                    <td>${account.firstName} ${account.lastName}</td>
                                                    <td>${account.email}</td>
                                                    <td>${account.phone}</td>
                                                    <td>${account.role.name}</td>
                                                    <td class="toggle">
                                                        <c:if test="${account.status == true}"><button class="display-toggle" onclick="changeStatus(this, ${account.accountID})"><i class="fa-solid fa-toggle-on"></i></button></c:if>
                                                        <c:if test="${account.status == false}"><button class="display-toggle" onclick="changeStatus(this, ${account.accountID})"><i class="fa-solid fa-toggle-off"></i></button></c:if>
                                                        </td>
                                                        <td>
                                                            <a class="text-primary" href="accountdetail?id=${account.accountID}">View</a>
                                                        /<a class="text-success" href="accountedit?id=${account.accountID}">Edit</a>
                                                        /<span style="cursor: pointer" class="text-danger" onclick="confirmDelete(${account.accountID}, this)">Delete</span>
                                                    </td>
                                                </tr>
                                            </c:forEach>    
                                        </tbody>
                                    </table>
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
            <script>
                function confirmDelete(id, btn) {
                    var result = confirm("Do you want to delete this account?");
                    if (result === true) {
                        $(function () {
                            $.ajax({
                                type: "delete",
                                url: "account?id=" + id,
                                cache: false,
                                success: function () {
                                    $(btn).closest('tr').fadeOut("slow");
                                }
                            });
                        });
                    }
                }
            </script>
    </body>

</html>