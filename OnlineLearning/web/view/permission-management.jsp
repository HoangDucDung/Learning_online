<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manage Permission</title>
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
        <!--Data table-->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.css">
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>

    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <!--Sidebar-->
                <div class="col-1 col-sm-2 bg-dark p-0 collapse-horizontal overflow-auto vh-100" id="navbarTogglerDemo01">
                    <jsp:include page="sidenav.jsp?page=Manage Permission"/>
                </div>

                <!--Page Content-->
                <div class="col p-0 vh-100 overflow-auto bg-light">
                    <jsp:include page="navbar-header.jsp?page=Manage Permission"/>

                    <div class="container bg-light">
                        <!-- Modal -->
                        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Create new permission</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>

                                    <div class="modal-body">
                                        <!--Create New Permission Form-->
                                        <form method="POST" action="./permission" id="createPermissionForm">
                                            <div class="mb-3">
                                                <label for="permissionName" class="form-label">
                                                    Permission Name<span class="text-danger">*</span>
                                                </label>
                                                <input id="permissonName" class="form-control" type="text" name="permissionName" required>
                                            </div>

                                            <div class="mb-3">
                                                <label for="requestUrl" class="form-label">
                                                    Request Url<span class="text-danger">*</span>
                                                </label>
                                                <input id="requestUrl" class="form-control" type="text" name="requestUrl" required>
                                            </div>
                                            <div>
                                                <label class="form-label" >Method</label>
                                                <select id="method" class="form-select" name="method">
                                                    <option>GET</option>
                                                    <option>POST</option>
                                                    <option>PUT</option>
                                                    <option>DELETE</option>
                                                </select>
                                            </div>

                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                <button type=submit" class="btn btn-primary">Create</button>
                                            </div>
                                        </form>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <!-- Modal -->
                        <div class="modal fade" id="editPermissionModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Edit Permission</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <input id="permission-id" hidden>
                                        <div class="mb-3">
                                            <label class="form-label">
                                                Permission Name<span class="text-danger">*</span>
                                            </label>
                                            <input id="permission-name" class="form-control" type="text">
                                        </div>

                                        <div class="mb-3">
                                            <label for="permission-request-url" class="form-label">
                                                Request Url<span class="text-danger">*</span>
                                            </label>
                                            <input id="permission-request-url" class="form-control" type="text">
                                        </div>
                                        <div>
                                            <label class="form-label" >Method</label>
                                            <select id="permission-method" class="form-select" name="method">
                                                <option>GET</option>
                                                <option>POST</option>
                                                <option>PUT</option>
                                                <option>DELETE</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        <button id="editPermissionBtn" type="button" class="btn btn-primary">Edit</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="mb-2">
                            <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                <strong>Create Permission</strong>
                            </button>
                        </div>

                        <table id="permissionTable" class="table table-striped">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Permission Name</th>
                                    <th>Request Url</th>
                                    <th>Method</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${permissions}" var="p" varStatus="loop">
                                    <tr class="align-middle">
                                        <td>${loop.count}</td>
                                        <td>${p.name}</td>
                                        <td>${p.requestUrl}</td>
                                        <td>${p.method}</td>
                                        <td>
                                            <button class="btn text-primary p-0 btn-edit-permisson"
                                                    data-permission-id="${p.id}"
                                                    data-permission-name="${p.name}"
                                                    data-permission-requestUrl="${p.requestUrl}"
                                                    data-permission-method="${p.method}"
                                                    >Edit</button> | 
                                            <button class="btn text-danger p-0" onclick="deletePermissionById(${p.id}, this)">Delete</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <script src="../js/permission.js"></script>
    </body>

</html>