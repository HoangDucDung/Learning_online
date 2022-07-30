<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <link href="../css/lesson-list-management.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="../js/lesson-management.js" type="text/javascript"></script>
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
                    <jsp:include page="navbar-header.jsp?page=Manage Lesson"/>

                    <div class="container">
                        <a class="back" href="../management/course"><i class="fa-solid fa-angle-left"></i>Back</a>
                        <div class="container-table">
                            <div class="table-content">
                                <div class="search">
                                    <a class="margin-auto-0" id="add-lesson" href="lesson-detail?Cid=${Cid}" ><i class="fa-solid fa-plus"></i> Add Lesson</a>

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
                                                        <div class="filter-lesson-type">
                                                            <p><input class="sublt-a clickchosse" type="checkbox">Type lesson</p>
                                                            <div class="sublesson-type">
                                                                <c:forEach items="${ltList}" var="lessontype">
                                                                    <p class="sub-t"><input class="sublt clickchosse" value="${lessontype.getLessonTypeID()}" type="checkbox"><label>${lessontype.getName()}</label></p>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                        <div class="filter-sublesson">
                                                            <p><input class="subls-a clickchosse" type="checkbox"> Sub lesson</p>
                                                            <div class="sublesson-sub">
                                                                <c:forEach items="${slList}" var="sub">
                                                                    <p class="sub-s"><input class="subls clickchosse" type="checkbox" value="${sub.getSubLessonID()}"><label>${sub.getName()}</label></p>
                                                                        </c:forEach>
                                                            </div>
                                                        </div>
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
                                            <th>Video</th>
                                            <th>Start day</th>
                                            <th class="type-lesson">Type</th>
                                            <th>SubLesson Name</th>
                                            <th>Status</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody id="content-course">
                                        <c:forEach items="${lessonlist}" var="lesson">
                                            <tr>
                                                <td>${lesson.getId()}</td>
                                                <td>${lesson.getName()}</td>
                                                <c:choose>
                                                    <c:when test = "${lesson.getVideoUrl() == null}">
                                                        <td></td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td><video  style="width: 200px;height: 120px;" src="${lesson.getVideoUrl()}" controls></video></td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                <td>${lesson.getStartLearningTime()}</td>
                                                <td>${lesson.getLessonTypeID().getName()}</td>
                                                <td>${lesson.getSubLessonID().getName()}</td>
                                                <c:choose>
                                                    <c:when test = "${lesson.isStatus() == true}">
                                                        <td>Published</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>Unpublished</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td>
                                                    <div class="context">
                                                        <a href="lesson-detail?Lid=${lesson.getId()}&type=view" class="text-primary">View</a>/
                                                        <a href="lesson-detail?Lid=${lesson.getId()}" class="text-primary">Edit</a>/
                                                        <a href="#" class="text-danger" onclick="deleteLesson(${lesson.getId()}, this)">Delete</a>
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

    </body>

</html>