<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <link href="../css/price-package-detail.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="../js/price-package-detail.js" type="text/javascript"></script>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Subject"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Price Package Detail"/>

                    <div class="container">
                        <div class="container-table post">
                            <a class="back" href="../management/subject-detail?courseID=${courseID}"><i class="fa-solid fa-angle-left"></i>Back</a>
                            <form onsubmit="return isDataValid()" action="../management/price-package-detail?courseID=${courseID}&priceID=${pricePackage.priceId}" method="post" class="form-submit">
                                <h4 class="title">Package</h4>
                                <input pattern="^[^-\s][a-zA-Z0-9_\s-]*$" type="text" name="name" maxlength="255" class="input-box form-control" value="${pricePackage.name}" required>
                                <h4 class="title">Duration</h4>
                                <input id="duration" type="text" name="duration" class="input-box form-control" <c:if test="${pricePackage.accessDuration!=-1}">value="${pricePackage.accessDuration}"</c:if> >
                                    <span class="text-danger duration">Duration must be a positive integer greater than 0 or must be left blank</span>
                                    <h4 class="title">List Price</h4>
                                    <input id="listPrice" type="text" name="listPrice" class="input-box form-control" value="${pricePackage.listPrice}" required>
                                <span class="text-danger list-price">List price must be a number greater than or equal to 0</span>
                                <h4 class="title">Sale Price</h4>
                                <input id="salePrice" type="text" name="salePrice" class="input-box form-control" value="${pricePackage.salePrice}" required>
                                <span class="text-danger sale-price">Sale price must be a number greater than or equal to 0</span>
                                <h4 class="title">Status</h4>
                                <select name="status" class="select-tag form-select">
                                    <option value="true" ${pricePackage.status == true ? "selected" : ""}>Active</option>
                                    <option value="false" ${pricePackage.status == false ? "selected" : ""}>Deactive</option>
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