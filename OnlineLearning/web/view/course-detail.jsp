<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="base-view/baseTag.jsp"/>
        <title>JSP Page</title>
        <link href="./css/course-detail.css" rel="stylesheet">
    </head>
    <body>

        <jsp:include page="base-view/headerUser.jsp?page=Courses"/>

        <!-- Course Details Header Start -->

        <div class="course-detail-header" style="background-image: url('${course.thumbnailUrl}')">
            <div class="course-detail-header-left">
                <div class="course-detail-title">
                    <h1>${course.name}</h1>
                    <c:if test="${listPrice.isEmpty()}">
                        <p>Free</p>
                    </c:if>             
                </div>
                <p class="course-detail-description">${course.description}</p>
                <div class="course-detail-author">
                    <img src="img/${course.instructorId.profilePictureUrl}" alt=""
                         class="course-detail-author-avatar">
                    <span class="course-detail-author-name">${course.instructorId.firstName} ${course.instructorId.lastName}</span>
                </div>
                <c:if test="${!isRegister}">
                    <a href="${sessionScope.account != null ? '#' : 'login'}" class="course-enroll-button">
                        <p>Register</p>
                    </a>
                </c:if>
                <c:if test="${isRegister}">  
                    <a href="lesson?id=${course.courseId}" class="course-continue-button">Continue</a>
                </c:if>
            </div>
            <div class="course-detail-header-right">
                <embed src="${course.videoIntroduce}" class="course-detail-img">
            </div>
        </div>

        <!-- Course Details Header End- -->

        <!-- Course Details Body Start -->

        <div class="course-detail-body">
            <div class="course-detail-body-left">
                <c:if test="${!listPrice.isEmpty()}">
                    <div id="list-price">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th scope="col">Access Package</th>
                                    <th scope="col">Price</th>
                                    <th scope="col">Sale price</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listPrice}" var="price">
                                    <tr>
                                        <td>${price.name}</td>
                                        <td style="text-decoration: line-through;">$ ${price.listPrice}</td>
                                        <td>$ ${price.salePrice}</td>
                                    </tr>
                                </c:forEach>      
                            </tbody>
                        </table>
                    </div>
                </c:if>

                <h1 class="course-detail-body-title">About this Professional Certificate</h1>
                <p class="course-detail-body-desc">${course.aboutCourse}</p>
            </div>
            <div class="course-detail-body-right">
                <div class="course-detail-up">
                    <h1>Objectives</h1>
                    <ul>
                        <c:forEach items="${course.objectives}" var="obj">
                            <li><i style='color: #0dd20d; margin-right: 25px;' class="fa-solid fa-check"></i> ${obj}</li>  
                            </c:forEach>
                    </ul>
                </div>
                <div class="course-detail-down">
                    <h1>About course</h1>
                    <div>
                        <ul>
                            <li>Total lesson :  ${numberLesson}</li>
                            <li>Total quiz :  ${numberQuiz}</li>
                            <li>Rating :   
                                <ul style="display:inline-block">
                                    <li style="margin: 0">
                                        <c:forEach begin="1" end="${course.star}">
                                            <i class="c-star fa-solid fa-star selected"></i>
                                        </c:forEach>
                                        <c:forEach begin="${course.star + 1}" end="5">
                                            <i class="c-star fa-solid fa-star"></i>
                                        </c:forEach>
                                    </li>
                                </ul>
                            </li>
                            <li>Date published :  ${course.createdDate}</li>
                            <li>Total people learning :  ${course.numberPeopleLearning}</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!-- Course Details Body End -->

        <div class="display-flex justify-between cd-content-bottom">
            <div class="w-70">
                <c:forEach items="${listSubLesson}" var="sub">
                    <div class="list-topic">
                        <div class="topic display-flex justify-between">
                            <h4 class="m-0">${sub.name}</h4>
                            <i class="fa-solid fa-angle-up"></i>
                        </div>
                        <ul class="list-group list-group-flush" style="height:auto">
                            <c:forEach items="${sub.listLesson}" var="lesson">
                                <c:choose>
                                    <c:when test="${lesson.lessonTypeID.lessonTypeID == 1}">
                                        <c:choose>
                                            <c:when test="${isRegister == true}">
                                                <li class="list-group-item"><a href="lesson?id=${course.courseId}&lid=${lesson.lessonID}"><i class="fa-solid fa-circle-play"></i>${lesson.name}</a></li>  
                                            </c:when>
                                            <c:otherwise>
                                                <li class="list-group-item"><span><i class="fa-solid fa-circle-play"></i>${lesson.name}</span></li>     
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${isRegister == true}">
                                                <li class="list-group-item"><a href="quiz?id=${lesson.lessonID}"><i class="fa-solid fa-question"></i>${lesson.name}</a></li>      
                                                    </c:when>
                                            <c:otherwise>
                                                <li class="list-group-item"><span><i class="fa-solid fa-question"></i>${lesson.name}</span></li>     
                                            </c:otherwise>
                                        </c:choose>                                                                                       
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>           
                        </ul>
                    </div>  
                </c:forEach>
            </div>
            <div class="w-30">
                <div class="list-feature-course">
                    <c:forEach items="${listTopFeatureCourse}" var="course">
                        <a href="./course-detail?id=${course.courseId}" class="color1c1d1f">
                            <div class="feature-course-item">
                                <img src="${course.thumbnailUrl}" alt="">
                                <div>
                                    <h4 class="feature-course-title">${course.name}</h4>
                                    <ul class="feature-star">
                                        <li>
                                            <c:forEach begin="1" end="${course.star}">
                                                <i class="fa-solid fa-star selected"></i>
                                            </c:forEach>
                                            <c:forEach begin="${course.star + 1}" end="5">
                                                <i class="fa-solid fa-star"></i>
                                            </c:forEach>
                                        </li>
                                    </ul>
                                    <span class="feature-desc">${course.description}</span>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div id="cd-overlay"></div>
        <form id="notice-buy-course" method="POST" action="course-detail" onsubmit="return checkBalance(${sessionScope.account.balance})">
            <div id="notice-buy-course-header">
                <h4>Enroll</h4>
                <i id="close-btn" class="fa-solid fa-xmark"></i>
            </div>
            <div id="notice-buy-course-body">
                <div class="display-flex">
                    <img src="${course.tinyPictureUrl}" alt="" id="notice-buy-course-img">
                    <div id="notice-buy-course-desc">
                        <h4>${course.name}</h4>
                        <ul style="margin-top: 15px">
                            <li>
                                <c:forEach begin="1" end="${course.star}">
                                    <i class="c-star fa-solid fa-star selected"></i>
                                </c:forEach>
                                <c:forEach begin="${course.star + 1}" end="5">
                                    <i class="c-star fa-solid fa-star"></i>
                                </c:forEach>
                            </li>
                        </ul>
                    </div>

                </div>
                <div id="notice-buy-course-price">
                    <div class="notice-price">
                        <input type="hidden" name="courseId" value="${course.courseId}">
                        <c:forEach items="${listPrice}" var="price" varStatus="loop">
                            <div style="margin-bottom: 20px">
                                <input type="hidden" value="${price.salePrice == null ? price.listPrice : price.salePrice}">
                                <input ${loop.index == 0 ? "checked" : ""} type="radio" id="priceId-${price.priceId}" name="price-package" value="${price.priceId}" class="list-price">
                                <label style="margin-left: 15px" for="priceId-${price.priceId}">${price.name}</label>
                            </div>
                        </c:forEach>
                    </div>                  
                </div>
                <p id="notice-not-enough" style="color:red"></p>
            </div>
            <div id="notice-buy-course-footer">
                <input type="submit" id="btn-buy-course" value="Pay">
            </div>
        </form>

        <jsp:include page="base-view/footerUser.jsp"/>

        <script type="text/javascript" src="js/course-detail.js"></script>
    </body>
</html>
