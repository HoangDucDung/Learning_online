<%-- 
    Document   : question-list-management
    Created on : Jul 12, 2022, 9:09:22 PM
    Author     : FPTSHOP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
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
        <link href="../css/question-list.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="../js/question-list.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css">
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>

    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Question"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Question-List"/>

                    <div class="container">
                        <div class="container-table">
                            <div class="table-content">
                                <div class="search">
                                    <a class="margin-auto-0" id="add-question" href="lesson-detail?Cid=${Cid}" ><i class="fa-solid fa-plus"></i> Add Question</a>

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
                                            <div class="filter-level">
                                                Level: 
                                                <select name="level" class="select-tag" id="level">
                                                    <option value="0" >All level</option>     
                                                    <c:forEach items="${questionlevel}" var="ql">
                                                        <option value="${ql.getId()}" >${ql.getLevelName()}</option> 
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="filter-category">
                                                <i id="filter-icon" class="fa-solid fa-filter"></i>
                                                <div class="filter-content">
                                                    <div id="search-category-box">
                                                        <input id="search-category" type="text" value="" placeholder="Search for category name"/>
                                                    </div>
                                                    <div class="filter-scroll">
                                                        <!--category-->
                                                        <!--for dimention-->
                                                        <!--<hr>-->
                                                        <!--for course-->
                                                        <c:forEach items="${courselist}" var="clist">
                                                            <div class="title-category">
                                                                <div class="title">
                                                                    <p><input class="subc-a ${clist.getCourseId()} clist" value="${clist.getCourseId()}" type="checkbox"> ${clist.getName()}</p>
                                                                </div>
                                                            </div>
                                                            <c:forEach items="${lessonlist}" var="llist">
                                                                <c:if test="${clist.getCourseId() == llist.getCourseID().getCourseId()}">
                                                                    <div class="sub-category ${clist.getCourseId()}">
                                                                        <p><input class="subl-a ${clist.getCourseId()}" id="${clist.getCourseId()}" value="${llist.getId()}" type="checkbox"> ${llist.getName()}</p>
                                                                    </div>
                                                                </c:if>
                                                            </c:forEach>
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
                                            <th>QuestionID</th>
                                            <th>Question text</th>
                                            <th>Lesson</th>
                                            <th>Level</th>
                                            <th>Status</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody id="content-course">
                                        <c:forEach items="${questions}" var="q">
                                            <tr>
                                                <td>${q.getId()}</td>
                                                <td  style="width: 300px">${q.getQuestionText()}</td>
                                                <c:forEach items="${lessonlist}" var="llist">
                                                    <c:if test="${q.getLessonId() == llist.getId()}">
                                                        <td>${llist.getName()}</td>
                                                    </c:if>
                                                </c:forEach>

                                                <td>${q.getLevel().getLevelName()}</td>
                                                <c:choose>
                                                    <c:when test = "${q.isActive() == true}">
                                                        <td>Published</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>Unpublished</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td>
                                                    <div class="context">
                                                        <a href="#" class="text-primary">View</a>/
                                                        <a href="#" class="text-primary">Edit</a>/
                                                        <a href="#" class="text-danger" onclick="deleteQuestion(${q.getId()},this)">Delete</a>
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

    </body>
</html>