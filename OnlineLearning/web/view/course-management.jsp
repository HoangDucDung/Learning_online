<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manage Course</title>
        <link rel="stylesheet" href="../css/my-course.css">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link href="../css/paging.css" rel="stylesheet" />

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
                        <a href="./subject-add" class="btn btn-primary" style="margin-bottom: 20px">Add New</a>
                        <div class="d-flex justify-content-between">
                            <div class="input-group">
                                <div class="form-outline">
                                    <input type="search" id="search-course" class="form-control" />
                                </div>
                                <button type="button" class="btn btn-primary" onclick="searchByValue()">
                                    <i class="fas fa-search"></i>
                                </button>
                            </div>

                            <select style="width: auto" id="status-option" class="form-select form-select-sm" aria-label=".form-select-sm example" onchange="searchByValue()">
                                <option id="All" value="">All</option>
                                <option value="true">Publish</option>
                                <option value="false">Unpublish</option>
                            </select>

                            <div class="d-flex position-relative afterTemp" style="float: right">
                                <p class="fa-solid fa-filter icon-filter"></p>
                                <div id="search-category">
                                    <form>
                                        <div id="holder-search-input">
                                            <input type="text" id="holder-search-category" onkeyup="searchCategory()"
                                                   placeholder="Search for category...">
                                            <button type="button" id="clear-btn" onclick="clearValueSearch()">Clear</button>
                                        </div>
                                        <div id="form-select-category">  
                                            <c:forEach items="${listSC}" var="sc">
                                                <div class="search-category-item">

                                                    <div class="d-flex justify-content-between">
                                                        <div class="search-category-name">
                                                            <input type="checkbox" id="id-category-${sc.categoryID}" onchange="checkedCategory(this)">
                                                            <label for="id-category-${sc.categoryID}">${sc.name}</label>
                                                        </div>
                                                        <i class="fa-solid fa-angle-down icon-down-cate" onclick="dropDownSubCate(this)"></i>
                                                    </div>
                                                    <div class="search-sub-category">
                                                        <c:forEach items="${listSubject}" var="subject">
                                                            <c:if test="${subject.categoryID.categoryID == sc.categoryID}">
                                                                <div class="search-sub-category-name">
                                                                    <input type="checkbox" id="id-sub-category-${subject.subjectId}" onchange="checkedSubCategory(this)" name="search-category" value="${subject.subjectId}">
                                                                    <label for="id-sub-category-${subject.subjectId}">${subject.name}</label>
                                                                </div>
                                                            </c:if>                                            
                                                        </c:forEach>                 
                                                    </div>
                                                </div>  
                                            </c:forEach>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="container-table">
                            <div class="table-content">

                                <table class="table table-striped" id="table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th style="width:300px">Name</th>
                                            <th>Category</th>
                                            <th>Number of lessons</th>
                                            <th>Lesson</th>
                                            <th>Owner</th>
                                            <th>Status</th>
                                            <th>Action</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${listCourse}" var="course">
                                            <tr>
                                                <td>${course.courseId}</td>                                                
                                                <td>${course.name}</td>
                                                <td>
                                                    <c:forEach items="${course.listSubject}" var="sub">
                                                        ${sub.name}, 
                                                    </c:forEach>
                                                </td>
                                                <td>${course.numberLesson}</td>
                                                <td><a href="./lesson-list?Cid=${course.courseId}">Lesson List</a></td>
                                                <td>${course.instructorId.firstName} ${course.instructorId.lastName}</td>
                                                <c:choose>
                                                    <c:when test="${course.status}">
                                                        <td>Publish</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>Unpublish</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td>  
                                                    <c:if test="${roleAccount == 4}">
                                                        <a class="text-primary" href="./subject-view?courseID=${course.courseId}">View</a> / <a class="text-primary" href="./subject-detail?courseID=${course.courseId}">Edit</a> / <button class="text-danger" onclick="deleteSubject(${course.courseId}, this)">Delete</button>
                                                    </c:if>  
                                                    <c:forEach items="${listCourseIdCanAccess}" var="courseCanAccess">
                                                        <c:if test="${courseCanAccess == course.courseId}">
                                                            <a class="text-primary" href="./subject-view?courseID=${course.courseId}">View</a> / <a class="text-primary" href="./subject-detail?courseID=${course.courseId}">Edit</a> / <button class="text-danger" onclick="deleteSubject(${course.courseId}, this)">Delete</button>
                                                        </c:if>  
                                                    </c:forEach>  
                                                </td>
                                            </tr> 
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <div id="pagination-page">
                                    <div class="pagination">
                                        <ul class="pagination-list">
                                            <li>
                                                <button onclick="pagination(${page-1==0?1:page-1})" class="previous-btn">Previous</button>
                                            </li>
                                            <c:forEach begin="1" end="${totalpage}" var="p">
                                                <li>
                                                    <button onclick="pagination(${p})" ${p==page?"class='paging-active page-num'":"class='page-num'"} >${p}</button>
                                                </li>
                                            </c:forEach>
                                            <li>
                                                <button onclick="pagination(${page+1>totalpage?totalpage:page+1})" class="next-btn" >Next</button>
                                            </li>
                                        </ul>
                                        <input type="hidden" id="page-num" value="${page}">
                                    </div>    
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
        <script src="../js/subject.js"></script>
    </body>

</html>