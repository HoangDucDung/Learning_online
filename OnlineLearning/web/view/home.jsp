
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="base-view/baseTag.jsp"></jsp:include>

            <link href="css/home.css" rel="stylesheet" type="text/css"/>

            <title>Home</title>

        </head>
        <body>


        <jsp:include page="base-view/headerUser.jsp?page=Home"></jsp:include>

            <!-- Slider Start -->

            <div class="slider-container">
            <c:forEach items="${requestScope.sliders}" var="slider">
                <div class="slider-item mySlide_current">
                    <i class="slide-icon-right fas fa-angle-right" onclick="plusSlides(1)"></i>
                    <i class="slide-icon-left fas fa-angle-left" onclick="plusSlides(-1)"></i>
                    <img src="img/${slider.imageUrl}" alt="">
                    <div class="slider-desc-parent">
                        <div class="slider-desc">
                            <h5>${slider.subTitle}</h5>
                            <h1>${slider.title}</h1>
                            <p>${slider.description}</p>
                            <a href="${slider.navigationLink}" >
                                Join Now
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Slider End -->

        <h1 class="best-rated-course-header">Popular Courses</h1>

        <div id="best-rated-course">
            <c:forEach items="${bestRatedCourses}" var="course">
                <div class="course-item">
                    <div class="tiny-picture">
                        <img src="${course.courseId.tinyPictureUrl}" alt="">
                        <div><a href="./course-detail?id=${course.courseId.courseId}">Join Now</a></div>
                    </div>
                    <div class="desc-best-course"> 
                        <div class="rated-star">

                            <c:forEach begin="1" end="${course.rating}">
                                <i class="fa-solid fa-star voted"></i>
                            </c:forEach>
                            <c:forEach begin="${course.rating + 1}" end="5">
                                <i class="fa-regular fa-star voted"></i>
                            </c:forEach>
                        </div>
                        <h3>${course.courseId.name}</h3>
                    </div>
                    <div class="display-flex">
                        <div class="item-desc">
                            <i class="fa-solid fa-user-tie"></i>
                            <span>${course.courseId.instructorId.firstName} ${course.courseId.instructorId.lastName}</span>
                        </div>
                    </div>

                </div>
            </c:forEach>
        </div>

        <h1 class="best-rated-course-header">Most Viewed Blogs</h1>

        <div id="most-viewed-blog">
            <c:forEach items="${mostViewedBlogs}" var="blog">
                <div class="blog-item">
                    <div class="tiny-picture">
                        <img src="img/${blog.thumbnailUrl}" alt="">
                        <div><a href="blog-detail?blogID=${blog.blogID}">View</a></div>
                    </div>
                    <div class="desc-best-course"> 
                        <h3>${blog.title}</h3>

                        <p class="text-desc-blog">${blog.description}</p>
                    </div>
                    <div class="display-flex">
                        <div class="item-desc-blog">
                            <i class="fa-solid fa-eye"></i>    
                            <span>${blog.numberOfView}</span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <jsp:include page="base-view/footerUser.jsp"></jsp:include>

        <script src="js/home.js" type="text/javascript"></script>
    </body>
</html>
