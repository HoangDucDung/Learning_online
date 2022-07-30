<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manage Access</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Access"/>
                </div>
                <div class="col p-0">
                    <jsp:include page="navbar-header.jsp?page=Manage Access"/>

                    <div class="container min-vh-100">
                        
                        <ul class="nav nav-tabs">
                            <c:forEach items="${roles}" var="r">
                                <li class="nav-item">
                                    <a class="nav-link ${(selectedRole.name == r.name) ? "active" : ""}" 
                                       aria-current="page" href="./rolepermission?role=${r.name}">${r.name}
                                    </a>
                                </li>
                            </c:forEach>
                            <li class="nav-item">
                                <a class="nav-link" href="#">+ Role</a>
                            </li>
                        </ul>

                        <div>
                            <form class="" action="./rolepermission?role=${selectedRole.name}" method="POST">
                                <input type="text" value="${selectedRole.id}" name="roleId" hidden>
                                <div class="form-row mt-4">
                                    <c:forEach items="${permissions}" var="p">
                                        <div class="form-check">
                                            <input id="p-${p.id}" class="form-check-input" type="checkbox" 
                                                   value="${p.id}" name="${selectedRole.name}" ${selectedPermission.contains(p) ? "checked" : ""}>
                                            <label class="form-check-label" for="p-${p.id}">
                                                ${p.name}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <button type="submit" class="btn btn-success mr-3">Udpate</button>
                                    <div></div>
                                    <button type="" class="btn btn-danger mr-3">Delete</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JavaScript -->    
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
        <script src="../js/main.js"></script>
    </body>

</html>