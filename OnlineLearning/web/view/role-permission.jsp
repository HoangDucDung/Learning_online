<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manage Access</title>
        <!-- Bootstrap CSS -->
        <link href="../node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="../node_modules/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--JQuery-->
        <script src="../node_modules/jquery/dist/jquery.min.js"></script>

        <!--JQuery Confirm-->
        <link href="../node_modules/jquery-confirm/dist/jquery-confirm.min.css" rel="stylesheet">
        <script src="../node_modules/jquery-confirm/dist/jquery-confirm.min.js"></script>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <!--Sidebar-->
                <div class="col-1 col-sm-2 bg-dark p-0 collapse-horizontal overflow-auto vh-100" id="navbarTogglerDemo01">
                    <jsp:include page="sidenav.jsp?page=Manage Access"/>
                </div>

                <!-- Create New Role Modal -->
                <div class="modal fade" id="createNewRoleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Create Role</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div class="mb-3 row">
                                    <label for="inputRoleName" class="col-3 col-form-label">
                                        Role Name<span class="text-danger">*</span>
                                    </label>
                                    <div class="col">
                                        <input type="text" class="form-control" id="inputRoleName" name="inputRoleName">
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger btn-sm" data-bs-dismiss="modal">Cancel</button>
                                <button id="btnCreateRole" type="button" class="btn btn-primary btn-sm">Create</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!--Page Content-->
                <div class="col p-0 vh-100 overflow-auto bg-light">
                    <jsp:include page="navbar-header.jsp?page=Manage Access"/>

                    <div class="container">
                        <ul class="nav nav-tabs">
                            <c:forEach items="${roles}" var="r">
                                <li class="nav-item">
                                    <a class="nav-link ${(selectedRole.id == r.id) ? "active" : ""}" href="./rolepermission?roleId=${r.id}">
                                        ${r.name}
                                    </a>
                                </li>
                            </c:forEach>

                            <li class="nav-item">
                                <button type="button" class="nav-link text-success" data-bs-toggle="modal" data-bs-target="#createNewRoleModal">
                                    <i class="fa-solid fa-plus"></i> Role
                                </button>
                            </li>
                        </ul>

                        <div class="mt-2">
                            <div class="d-flex justify-content-end">
                                <button id="checkAllBtn" class="btn btn-sm btn-success text-white me-2">
                                    <i class="fa-solid fa-square-check"></i> Check All
                                </button>
                                <button id="uncheckAllBtn" class="btn btn-sm btn-danger text-white">
                                    <i class="fa-regular fa-square"></i> Uncheck All
                                </button>
                            </div>

                            <input type="text" value="${selectedRole.id}" name="roleId" hidden>
                            <div class="row">
                                <c:forEach items="${permissions}" var="p" varStatus="loop">
                                    <div class="col-6">
                                        <input id="p-${p.id}" class="form-check-input permission-check" type="checkbox" 
                                               value="${p.id}" name="${selectedRole.name}" ${selectedPermission.contains(p) ? "checked" : ""}>
                                        <label class="form-check-label" for="p-${p.id}">
                                            ${p.name}
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>

                            <div class="d-flex justify-content-end">
                                <button id="updateBtn" type="button" class="btn btn-success btn-sm me-3" value="${selectedRole.id}">Update</button>

                                <button id="deleteBtn" data-role-name="${selectedRole.name}" value="${selectedRole.id}" 
                                        type="button" class="btn btn-danger btn-sm me-3">Delete</button>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/js/role-permission.js"></script>
    </body>

</html>