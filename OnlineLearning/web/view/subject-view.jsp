<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Subject Detail</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link href="../css/subject-view.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <link href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="../js/subject-view.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css">
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>

    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Subject"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Subject Detail"/>
                    <div class="container">
                        <a class="back" href="../management/course"><i class="fa-solid fa-angle-left"></i>Back</a>
                        <div class="container-table">
                            <!-- Tab links -->
                            <div class="tab">
                                <button class="tablinks active" onclick="openCity(event, 'Overview')">Overview</button>
                                <button class="tablinks" onclick="openCity(event, 'Dimension')">Dimension</button>
                                <button class="tablinks" onclick="openCity(event, 'Price')">Price Package</button>
                            </div>

                            <!-- Tab content -->
                            <!-- overview -->
                            <div id="Overview" class="tabcontent active">
                                <form action="" method="post" class="form-submit" enctype="multipart/form-data">
                                    <div class="form-content">
                                        <div class="left-part">
                                            <h4 class="title">Subject Name</h4>
                                            <span class="input-box">${course.name}</span>
                                            <div id="category-item">
                                                <h4 class="title">Category</h4>
                                                <div class="filter-category" onmouseover="showFilterBox()" onmouseout="hideFilterBox()" >
                                                    <i class="fa-solid fa-list-check filter-icon"></i>
                                                    <div id="filter-category-box">
                                                        <div class="search-category-box">
                                                            <input onkeyup="searchCategory(this)" id="search-category" type="text" value="" placeholder="Search for category name"/>
                                                        </div>
                                                        <div id="category-checkbox">
                                                            <c:forEach var="subject" items="${course.listSubject}">
                                                                <div class="category">
                                                                    <label>${subject.name}</label>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <c:if test="${sessionScope.account.role.id==4}">
                                                <div id="list-expert">
                                                    <h4 class="title">Expert can access</h4>
                                                    <div class="expert-box" onmouseover="showExpertBox()" onmouseout="hideExpertBox()" >
                                                        <i class="fa-solid fa-list-check filter-icon"></i>
                                                        <div id="filter-expert-box">
                                                            <div class="search-category-box">
                                                                <input onkeyup="searchExpert(this)" id="search-category" type="text" value="" placeholder="Search for expert"/>
                                                            </div>
                                                            <div id="expert-checkbox">
                                                                <c:forEach var="expert" items="${accounts}">
                                                                    <div class="expert-info">
                                                                        <label for="expert${expert.accountID}">${expert.accountID} &nbsp;&nbsp;&nbsp; ${expert.firstName} ${expert.lastName}</label>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div> 
                                            </c:if>
                                            <div id="featured-status">
                                                <div>
                                                    <input disabled="disabled" id="featured-subject" ${course.featured == true ? "checked" : ""} type="checkbox" name="featured" value="true" /> 
                                                    <label for="featured-subject">Featured Subject</label>
                                                </div>
                                                <div>
                                                    <h4 class="title text-center align-middle align-content-center align-items-center">Status</h4>
                                                    <span class="text-center">${course.status == true ? "Published" : "Unpublished"}</span>
                                                </div>
                                            </div>
                                            <h4 class="title">Description</h4>
                                            <span class="input-box">${course.description}</span>
                                            <h4 class="title">About Course</h4>
                                            <span class="input-box">${course.aboutCourse}</span>
                                            <h4 class="title">Video Introduce</h4>
                                            <div>
                                                <embed src="${course.videoIntroduce}" class="course-detail-img">
                                                <span class="input-box">${course.videoIntroduce}</span>
                                            </div>
                                            <h4 class="title">Objectives</h4>
                                            <div id="objectives">
                                                <c:forEach var="objective" items="${course.objectives}">
                                                    <div class="objective-item">
                                                        <i style='color: #0dd20d; margin-right: 25px;' class="fa-solid fa-check"></i><span>${objective}</span>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="upload-img">
                                            <img src="../img/${course.thumbnailUrl}">
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <!-- dimension -->
                            <div id="Dimension" class="tabcontent">
                                <table class="table table-striped" id="table1">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Type</th>
                                            <th>Dimension</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${dimensions}" var="dimension">
                                            <tr>
                                                <td>${dimension.dimensionID}</td>
                                                <td>${dimension.typeID.name}</td>
                                                <td>${dimension.name}</td>
                                                <td>
                                                    <div class="action">
                                                        <a class="text-primary" href="../management/dimension-view?courseID=${courseID}&dimensionID=${dimension.dimensionID}">View</a>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <!-- price package -->
                            <div id="Price" class="tabcontent">
                                <table class="table table-striped" id="table2">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Package</th>
                                            <th>Duration</th>
                                            <th>List Price</th>
                                            <th>Sale Price</th>
                                            <th>Active</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${pricePackages}" var="pricePackage">
                                            <tr>
                                                <td>${pricePackage.priceId}</td>
                                                <td>${pricePackage.name}</td>
                                                <c:choose>
                                                    <c:when test="${pricePackage.accessDuration != -1}">
                                                        <td>${pricePackage.accessDuration}</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>Unlimited</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td>${pricePackage.listPrice}</td>
                                                <td>${pricePackage.salePrice}</td>
                                                <td class="toggle">
                                                    <c:choose>
                                                        <c:when test="${pricePackage.status == true}">
                                                            <button class="display-toggle" ><i class="fa-solid fa-toggle-on"></i></button>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <button class="display-toggle" ><i class="fa-solid fa-toggle-off"></i></button>
                                                            </c:otherwise>
                                                        </c:choose>
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

    </body>

</html>