<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manage Setting</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <link href="../css/course-list-management.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="../js/course-lesson-management.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css">
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>

    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Course"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Manage Course"/>

                    <div class="container">
                        <a class="back" href="../management/subject-list"><i class="fa-solid fa-angle-left"></i>Back</a>
                        <div class="container-table">
                            <div class="table-content">
                                <div class="search">
                                    <a class="margin-auto-0" id="add-blog" href="../management/course-detail" ><i class="fa-solid fa-plus"></i> Add Course</a>
                                    <form class="search-form"> 
                                        <div class="filter">
                                            <div class="filter-status">
                                                Status: 
                                                <select name="status" class="select-tag" id="status">
                                                    <option value="-1" >All Status</option>
                                                    <option class="false" value="0" >Unpublished</option>
                                                    <option class="true" value="1" >Published</option>                                       
                                                </select>
                                            </div>
                                            <div class="filter-category">
                                                <i id="filter-icon" class="fa-solid fa-filter"></i>
                                                <div class="filter-content">
                                                    <div id="search-category-box">
                                                        <input id="search-category" type="text" value="" placeholder="Search for category name"/>
                                                    </div>
                                                    <div class="filter-scroll">
                                                        <div class="filter-price">
                                                            <div class="filter-price-name">
                                                                <input type="checkbox" class="title-price">
                                                                <label>Price</label>
                                                            </div>
                                                        </div>
                                                        <div class="sub-filter-price">
                                                            <div class="element-filter price">
                                                                <input type="checkbox" value="0" class="type-price">
                                                                <label>Free</label>
                                                            </div>
                                                            <div class="element-filter price">
                                                                <input type="checkbox" value="1" class="type-price">
                                                                <label>paid</label>
                                                            </div>
                                                        </div>
                                                        <c:forEach items="${listSC}" var="sc">
                                                            <div class="filter-subject">
                                                                <div class="filter-subject-name">
                                                                    <input type="checkbox" value="${sc.categoryID}" class="title-subject ${sc.categoryID}">
                                                                    <label>${sc.name}</label>
                                                                </div>
                                                            </div>
                                                            <div class="sub sub-filter-subject">
                                                                <c:forEach items="${listSubject}" var="subject">
                                                                    <c:if test="${subject.categoryID.categoryID == sc.categoryID}">
                                                                        <div class="element-filter category">
                                                                            <input type="checkbox" value="${subject.subjectId}" class="type-subject ${sc.categoryID}" onclick="checkCategory(this, ${sc.categoryID})">
                                                                            <label>${subject.name}</label>                                                                        
                                                                        </div>
                                                                    </c:if>                                            
                                                                </c:forEach>   
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <table class="table table-striped" id="table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Price</th>
                                            <th>Instructor</th>
                                            <th>Instructor Name</th>
                                            <th>Instructor Email</th>
                                            <th>Status</th>
                                            <th>Lesson</th>
                                            <th>Action</th>

                                        </tr>
                                    </thead>
                                    <tbody id="content-course">
                                        <c:forEach items="${CourseList}" var="course">
                                            <tr>
                                                <td>${course.getCourseId()}</td>
                                                <td>${course.getName()}</td>
                                                <td>${course.getPrice()}</td>
                                                <td><img class="img-thumbnail-blog" src="../img/${course.getInstructorId().getProfilePictureUrl()}"></td>
                                                <td>${course.getInstructorId().getFirstName()} ${course.getInstructorId().getLastName()}</td>
                                                <td>${course.getInstructorId().getEmail()}</td>
                                                <c:choose>
                                                    <c:when test = "${course.isStatus() == true}">
                                                        <td>Published</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>Unpublished</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td><a class="text-primary" href="lesson-list?Cid=${course.getCourseId()}&Sid=${subject.subjectId}">Lessons</a></td>
                                                <td>
                                                    <div class="context">
                                                        <a href="../management/course-detail?Cid=${course.getCourseId()}&type=view" class="text-primary">View</a>/
                                                        <a href="../management/course-detail?Cid=${course.getCourseId()}" class="text-primary">Edit</a>/
                                                        <a href="#" class="text-danger" id="${course.getCourseId()}" >Delete</a>
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
    </div>

    <!-- Bootstrap JavaScript -->    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
    crossorigin="anonymous"></script>

</body>

</html>