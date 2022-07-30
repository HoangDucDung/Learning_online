<%-- 
    Document   : lesson-detail-management
    Created on : Jun 27, 2022, 1:17:36 AM
    Author     : FPTSHOP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
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
        <link href="../css/lesson-detal-management.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="../js/lesson-detail.js" type="text/javascript"></script>
        <style>
            .back-btn {
                text-decoration: none;
                font-weight: 500; 
                margin-left: 30px;
            }
            
            .back-btn a {
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <div class="row">
            <div class="col-sm-2 min-vh-100 bg-dark p-0">
                <jsp:include page="sidenav.jsp?page=Manage Course"/>
            </div>
            <div class="col-sm-10 p-0">
                <jsp:include page="navbar-header.jsp?page=Lesson-detail"/>
                <div class="back-btn text-primary" >
                    <i class="fa-solid fa-angle-left"></i>
                    <a href="lesson-list?Cid=${lesson.getCourseID().getCourseId()}">Back</a>
                </div>
                
                <div class="contain">
                    <div class="contain-left">
                        <div class="content">
                            <h6>Lesson Name:</h6>
                            <span>${lesson.getName()}</span>
                        </div>
                        <div class="content">
                            <h6>Create Date:</h6>
                            <span>${createDate}</span>
                        </div>
                        <c:if test="${lesson.getUpdatedTime() != null}">
                            <div class="content">
                                <h6>Update Date:</h6>
                                <span>${updateDate}</span>
                            </div>
                        </c:if>
                        <div class="content">
                            <h6>Start learning time:</h6>
                            <span>${lesson.getStartLearningTime()}</span>
                        </div>
                        <div class="content">
                            <h6>Status</h6>
                            <span>
                                <input type="radio" id="Published" name="status" ${lesson.isStatus()==true?"checked":""} disabled>
                                <label for="male">Published</label>
                                <input type="radio" id="Unpublished" ${lesson.isStatus()==false?"checked":""} name="status" disabled>
                                <label for="female">Unpublished</label>
                            </span>
                        </div>
                        <div class="content">
                            <h6>Topic:</h6>
                            <span>
                                <c:forEach items="${lsList}" var="lsList">
                                    ${lesson.getSubLessonID().getSubLessonID()==lsList.getSubLessonID()?lsList.getName():""}
                                </c:forEach>
                            </span>
                        </div>
                        <div class="content">
                            <h6>Order:</h6>
                            <span>
                                <c:forEach items="${order}" var="order">
                                    ${lesson.getOrder()==order.getOrder()?order.getOrder():""}
                                </c:forEach>
                            </span>
                        </div>
                        <div class="content">
                            <h6>Lesson Type:</h6>
                            <span>
                                <c:forEach items="${ltList}" var="ltList">
                                    ${lesson.getLessonTypeID().getLessonTypeID()==ltList.getLessonTypeID()?ltList.getName():""}
                                </c:forEach>
                            <c:if test="${lesson.getLessonTypeID().getLessonTypeID() == 2}">
                                 <c:if test="${checkQuizExist}"><a href="quizsettingdetail?id=${lesson.lessonID}" class="btQuiz">Go to quiz</a> </c:if>
                                 <c:if test="${!checkQuizExist}"><a href="quizinsert?id=${lesson.lessonID}" class="btQuiz">Go to quiz </a></c:if>
                            </c:if>
                            </span>
                        </div>
                    </div>
                    <div class="contain-right">
                        <c:if test="${lesson.getLessonTypeID().getLessonTypeID() == 1}">
                            <video class="video-lesson" src="${lesson.getVideoUrl()}" controls></video>
                            </c:if>

                    </div>
                </div>

                <a href="lesson-detail?Lid=${lesson.getId()}" class="bt">Edit</a>
                <div style="margin-bottom: 30px"></div>
            </div>
        </div>
    </body>
</html>
