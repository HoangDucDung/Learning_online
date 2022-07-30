<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manage Slide</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link href="../css/dimension-detail.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="../js/slide-management.js" type="text/javascript"></script>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Subject"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Dimension Detail"/>

                    <div class="container">
                        <div class="container-table post">
                            <a class="back" href="../management/subject-detail?courseID=${courseID}"><i class="fa-solid fa-angle-left"></i>Back</a>
                            <form action="../management/dimension-detail?courseID=${courseID}&dimensionID=${dimensionID}" method="post" class="form-submit">
                                <h4 class="title">Title</h4>
                                <input pattern="^[^-\s][a-zA-Z0-9_\s-]*$" type="text" name="name" maxlength="200" class="input-box form-control" value="${dimension.name}" required>
                                <h4 class="title">Description</h4>
                                <input pattern="^[^-\s][a-zA-Z0-9_\s-]*$" type="text" name="description" maxlength="2000" class="input-box form-control" value="${dimension.description}" required>
                                <h4 class="title">Type</h4>
                                <select name="type" id="collection" class="select-tag">
                                    <c:forEach items="${allDimensionTypes}" var="type" >
                                        <option value="${type.typeID}" ${dimension.typeID.typeID == type.typeID ? "selected" : ""}>${type.name}</option>
                                    </c:forEach>
                                </select>
                                <input type="submit" value="${action}" class="save" name="action">
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

    </body>

</html>