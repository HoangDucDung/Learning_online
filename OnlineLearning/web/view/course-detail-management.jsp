<%-- 
    Document   : course-detail-management
    Created on : Jul 7, 2022, 6:27:26 PM
    Author     : FPTSHOP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Course-${act}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <link href="../css/course-view.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <!--        <script src="../js/course-lesson-management.js" type="text/javascript"></script>-->
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Course"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Subject ${act}"/>
                    <form>
                        <div class="contain">
                            <div class="content-left">
                                <div class="content">
                                    <h6>Course Name:</h6>
                                    <div class="ct-input">
                                        <input value="${course.getName()}">
                                    </div>
                                </div>
                                <div class="content dates">
                                    <div>
                                        <h6>Create date:</h6>
                                        <div class="ct-input date">
                                            <input value="${course.getCreatedDate()}">
                                        </div>
                                    </div>
                                    <div class="number2">
                                        <c:if test="${course.getModifiedDate() != null}">
                                            <h6>Modified Date:</h6>
                                            <div class="ct-input date">
                                                <input value="${course.getModifiedDate()}">
                                            </div>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="content dates">   
                                    <div>
                                        <h6>Price:</h6>
                                        <div class="ct-input date">
                                            <input value="${course.getPrice()}">
                                        </div>
                                    </div>
                                    <div class="number2">
                                        <h6>Total people learning:</h6>
                                        <div class="ct-input date">
                                            <input value="${course.numberPeopleLearning}">
                                        </div>
                                    </div>
                                </div>

                                <div class="content">   
                                    <div class="rating">
                                        <h6>Rating:</h6>
                                        <p>
                                            <c:forEach begin="1" end="${course.star}">
                                                <i class="c-star fa-solid fa-star selected"></i>
                                            </c:forEach>
                                            <c:forEach begin="${course.star + 1}" end="5">
                                                <i class="c-star fa-solid fa-star"></i>
                                            </c:forEach>
                                        </p>
                                    </div>
                                </div>

                                <div class="content">
                                    <h6>Category:</h6>
                                    <c:forEach items="${subjectC}" var="sb">
                                        <div class="ct-input subject">
                                            <input value="${sb.getName()}">
                                        </div>
                                    </c:forEach>
                                </div>

                                <div class="content">
                                    <h6>Course price package:</h6>
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Access Package</th>
                                                <th>Price</th>
                                                <th>Sale price</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${listPrice}" var="lp">
                                                <tr>
                                                    <td>${lp.getName()}</td>
                                                    <td>${lp.getListPrice()}</td>
                                                    <td>${lp.getSalePrice()}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                                <div class="content url">
                                    <div>
                                        <h6>TinyPictureUrl:</h6>
                                        <img src="${course.getTinyPictureUrl()}">
                                    </div>
                                    <div>
                                        <h6>ThumbnailUrl:</h6>
                                        <img src="${course.getThumbnailUrl()}">
                                    </div>
                                </div>

                                <div class="content">
                                    <h6>Description:</h6>
                                    <p class="Description">${course.getDescription()}</p>
                                </div>

                            </div>
                            <div class="content-right">
                                <div class="content profile">
                                    <img src="../img/${course.getInstructorId().getProfilePictureUrl()}">
                                    <p>Name: ${course.getInstructorId().getFirstName()} ${course.getInstructorId().getLastName()}</p>
                                </div>
                                <div class="content">
                                    <!--<video style="width: 200px;height: 200px"><source src="${course.getVideoIntroduce()}"></video>-->
                                    <iframe src="${course.getVideoIntroduce()}"></iframe>
                                </div>

                                <div class="content">
                                    <h6>About Course:</h6>
                                    <p>${course.getAboutCourse()}</p>
                                </div>
                            </div>
                        </div>
                        <a href="lesson-detail?Lid=${lesson.getId()}" class="bt">Edit</a>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>
