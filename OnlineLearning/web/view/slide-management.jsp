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
        <link href="../css/slide-management.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css">
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Slide"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Manage Slide"/>

                    <div class="container">
                        <div class="container-table">
                            <div class="table-content">
                                <div class="search">
                                    <a class="margin-auto-0 btn btn-primary" id="add-blog" href="slide-edit" ><i class="fa-solid fa-plus"></i> Add Slide</a>

                                    <form action="slide-list" method="GET"> 

                                        Status: 
                                        <select name="display" class="select-tag" onchange="this.form.submit()">
                                            <option value="-1" >All Status</option>
                                            <option value="false" ${ display == "false" ? "selected" : ""}>Hidden</option>
                                            <option value="true" ${ display == "true" ? "selected" : ""}>Display</option>                                       
                                        </select>
                                    </form>
                                </div>
                                <table class="table table-striped" id="table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Image</th>
                                            <th>Title</th>
                                            <th>Backlink</th>
                                            <th>Display</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody id="myTable">
                                        <c:forEach items="${requestScope.allSliders}" var="slider">
                                            <tr>
                                                <td>${slider.sliderID}</td>
                                                <td><img class="img-thumbnail-blog" src="../img/${slider.imageUrl}"></td>
                                                <td class="title-slide">${slider.title}</td>
                                                <td class="backlink-slide">${slider.navigationLink}</td>
                                                <td class="toggle">
                                                    <c:if test="${slider.status == true}"><button class="display-toggle" onclick="changeStatus(this, ${slider.sliderID})"><i class="fa-solid fa-toggle-on"></i></button></c:if>
                                                    <c:if test="${slider.status == false}"><button class="display-toggle" onclick="changeStatus(this, ${slider.sliderID})"><i class="fa-solid fa-toggle-off"></i></button></c:if>
                                                    </td>
                                                    <td>
                                                        <div id="action">
                                                            <a class="text-primary" href="../management/slide-view?id=${slider.sliderID}">View</a>&nbsp;/&nbsp;<button class="text-danger" onclick="deletePricePackage(${slider.sliderID}, this)">Delete</button>
                                                        </div>
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
        <script src="../js/slide-management.js" type="text/javascript"></script>

    </body>

</html>