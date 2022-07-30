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
        <link href="../css/subject-detail.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <link href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="../js/subject-detail.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css">
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>
        <script src="https://cdn.ckeditor.com/4.19.0/standard/ckeditor.js"></script>

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
                                <form onsubmit="return isSubmitavailable()" action="../management/subject-detail?courseID=${courseID}" method="post" class="form-submit" enctype="multipart/form-data">
                                    <div class="form-content">
                                        <div class="left-part">
                                            <h4 class="title">Subject Name</h4>
                                            <input type="text" name="name" maxlength="200" id="input-box-name" class="input-box form-control" value="${course.name}" required>
                                            <span style="margin-bottom: 30px;" class="text-danger name">Subject name cannot be left blank</span>
                                            <div id="category-item">
                                                <h4 class="title">Category</h4>
                                                <div class="filter-category" onmouseover="showFilterBox()" onmouseout="hideFilterBox()" >
                                                    <i class="fa-solid fa-list-check filter-icon"></i>
                                                    <div id="filter-category-box">
                                                        <div class="search-category-box">
                                                            <input onkeyup="searchCategory(this)" id="search-category" type="text" value="" placeholder="Search for category name"/>
                                                        </div>
                                                        <div id="category-checkbox">
                                                            <c:forEach var="category" items="${subjectCategories}">
                                                                <div>
                                                                    <div class="main-category">
                                                                        <input onchange="checkedSubcategory(this)" id="maincategory${category.categoryID}" class="form-check-input" type="checkbox" />
                                                                        <label for="maincategory${category.categoryID}">${category.name}</label>
                                                                    </div>
                                                                    <c:forEach var="subject" items="${subjects}">
                                                                        <c:if test="${subject.categoryID.categoryID == category.categoryID}">
                                                                            <div class="category">
                                                                                <input name="categoryID" onchange="checkedCategory(this)" <c:forEach var="subjectSelected" items="${course.listSubject}">${subjectSelected.subjectId == subject.subjectId ? "checked" : ""}</c:forEach> id="category${subject.subjectId}" class="form-check-input" type="checkbox" value="${subject.subjectId}" />
                                                                                <label for="category${subject.subjectId}">${subject.name}</label>
                                                                            </div>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                                &nbsp;&nbsp;<span class="text-danger">You must select a category</span>
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
                                                                <c:forEach var="expert" items="${experts}">
                                                                    <div class="expert-info">
                                                                        <input <c:forEach var="expertAccess" items="${course.listExpertCanAccess}">${expertAccess.accountID == expert.accountID ? "checked" : ""}</c:forEach> id="expert${expert.accountID}" class="form-check-input" type="checkbox" name="expertCanAccess" value="${expert.accountID}" />
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
                                                    <input <c:if test="${sessionScope.account.role.id!=4}">disabled="disabled"</c:if> id="featured-subject" ${course.featured == true ? "checked" : ""} class="form-check-input" type="checkbox" name="featured" value="true" /> 
                                                        <label for="featured-subject">Featured Subject</label>
                                                    </div>
                                                    <div>
                                                        <h4 class="title">Status</h4>
                                                        <select name="status" class="select-tag form-select">
                                                            <option value="true" ${course.status == true ? "selected" : ""}>Published</option>
                                                        <option value="false" ${course.status == false ? "selected" : ""}>Unpublished</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <h4 class="title">Description</h4>
                                            <textarea name="description" maxlength="2000" id="input-box-description" class="input-box form-control" rows="5" cols="50" required>${course.description}</textarea>
                                            <span style="margin-bottom: 30px;" class="text-danger description">Description cannot be left blank</span>
                                            <h4 class="title">Video Introduce</h4>
                                            <embed src="${course.videoIntroduce}" class="course-detail-img">
                                            <input type="text" name="video" maxlength="500" id="input-box-video" class="input-box form-control" value="${course.videoIntroduce}" required>
                                            <span style="margin-bottom: 30px;" class="text-danger video">Video introduce cannot be left blank</span>
                                            <h4 class="title">About Course</h4>
                                            <textarea name="content" id="ckeditor1" rows="5" cols="50" required>${course.aboutCourse}</textarea>
                                            <h4 style="margin-top: 20px;" class="title">Objectives</h4>
                                            <div id="objectives">
                                                <c:forEach var="objective" items="${course.objectives}">
                                                    <div class="objective-item">
                                                        <div>
                                                            <input class="form-control" type="text" name="objectives" maxlength="500" class="input-objective" value="${objective}" required>
                                                            <i onclick="deleteObjective(this)" class="text-danger fa-solid fa-circle-minus"></i>
                                                        </div>
                                                        <span class="text-danger">This objective cannot be left blank</span>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                            <input onclick="addNewObjective()" type="button" value="Add New Objective" class="btn btn-primary" >
                                        </div>
                                        <div class="upload-img">
                                            <c:if test="${courseID != null}"><img src="../img/${course.thumbnailUrl}"></c:if>
                                            <input type="file" name="photo" id="file" class="inputfile form-control" data-multiple-caption="{count} files selected" accept="image/*" <c:if test="${courseID == null}">required</c:if> >
                                            </div>
                                        </div>
                                        <input type="submit" value="EDIT" class="save">
                                    </form>
                                </div>

                                <!-- dimension -->
                                <div id="Dimension" class="tabcontent">
                                    <div class="add-search">
                                        <a class="margin-auto-0 add-dimension" href="../management/dimension-detail?courseID=${courseID}" ><i class="fa-solid fa-plus"></i> Add Dimension</a>
                                </div>
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
                                                        <a class="text-primary" href="../management/dimension-view?courseID=${courseID}&dimensionID=${dimension.dimensionID}">View</a>&nbsp;/&nbsp;
                                                        <a class="text-primary" href="../management/dimension-detail?courseID=${courseID}&dimensionID=${dimension.dimensionID}">Edit</a>&nbsp;/&nbsp;
                                                        <span class="text-danger" onclick="deleteDimension(${dimension.dimensionID}, this)">Delete</span>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <!-- price package -->
                            <div id="Price" class="tabcontent">
                                <c:if test="${sessionScope.account.role.id==4}">
                                    <div class="add-search">
                                        <a class="margin-auto-0 add-dimension" href="../management/price-package-detail?courseID=${courseID}" ><i class="fa-solid fa-plus"></i> Add Price Package</a>
                                    </div>
                                </c:if>
                                <table class="table table-striped" id="table2">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Package</th>
                                            <th>Duration</th>
                                            <th>List Price</th>
                                            <th>Sale Price</th>
                                            <th>Active</th>
                                            <th>Action</th>
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
                                                            <button class="display-toggle" onclick="changeStatusPricePackage(this, ${pricePackage.priceId}, ${sessionScope.account.role.id})" ><i class="fa-solid fa-toggle-on"></i></button>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <button class="display-toggle" onclick="changeStatusPricePackage(this, ${pricePackage.priceId}, ${sessionScope.account.role.id})" ><i class="fa-solid fa-toggle-off"></i></button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                </td>
                                                <td>
                                                    <c:if test="${sessionScope.account.role.id==4}">
                                                        <div class="action">
                                                            <a class="text-primary" href="../management/price-package-detail?priceID=${pricePackage.priceId}&courseID=${courseID}">Edit</a>&nbsp;/&nbsp;
                                                            <span class="text-danger" onclick="deletePricePackage(${pricePackage.priceId}, this)">Delete</span>
                                                        </div>
                                                    </c:if>
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
        <script>CKEDITOR.replace('ckeditor1');</script>
    </body>

</html>