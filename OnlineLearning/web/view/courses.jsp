
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="base-view/baseTag.jsp"/>
        <link rel="stylesheet" href="css/my-course.css"/>
        <link rel="stylesheet" href="css/courses.css"/>
        <script src="https://code.jquery.com/jquery-1.10.0.min.js" 
                integrity="sha256-2+LznWeWgL7AJ1ciaIG5rFP7GKemzzl+K75tRyTByOE=" crossorigin="anonymous">
        </script> 

        <title>Courses</title>
    </head>
    <body>
        <jsp:include page="base-view/headerUser.jsp?page=Courses"/>

        <div id="intro">
            <div class="intro-child">
                <h1>Courses</h1>
            </div>
        </div>

        <div id="c-course-container">
            <div id="c-course-search">
                <form class="search-bar">
                    <input type="search" name="search" pattern=".*\S.*" required onkeyup="searchByValue()">
                    <button class="search-btn" type="submit">
                        <span>Search</span>
                    </button>
                </form>
                <form class="filter-bar">
                    <button type="button" onclick="clearSearch()">Clear</button>
                    <div class="select-bar">
                        <select onchange="sortedCourse()">
                            <option id="all-course">All Course</option>
                            <option>Highest Rated</option>
                            <option>Newest</option>
                        </select>
                    </div>
                    <div class="filter-button">
                        <i class="fa-solid fa-filter"></i>
                        <span>Filter</span>
                    </div>
                </form>
            </div>
            <div id="c-course-container-left">
                <div id="c-course-list">
                    <c:forEach items="${listCourse}" var="course">

                        <div class="c-course-item">
                            <c:if test="${course.listPrice.size() != 0}">
                                <div class="price-container">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th scope="col">Access Package</th>
                                                <th scope="col">Price</th>
                                                <th scope="col">Sale price</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${course.listPrice}" var="price">
                                                <tr>
                                                    <td>${price.name}</td>
                                                    <td style="text-decoration: line-through">${price.listPrice}</td>
                                                    <td>${price.salePrice}</td>
                                                </tr>
                                            </c:forEach>                                      
                                        </tbody>
                                    </table>
                                </div>
                            </c:if>
                            <a style="width:27%; margin: auto 0" href="course-detail?id=${course.courseId}" <c:if test="${course.listPrice.size() != 0}">onmouseover="displayPrice(this)" onmouseout="hiddenPrice(this)"</c:if>>
                                <div class="c-course-item-img">
                                    <img src="${course.tinyPictureUrl}" alt="">
                                </div>
                            </a>
                            <div class="c-course-item-intro justify-between">
                                <div class="c-course-item-intro-text">
                                    <a href="course-detail?id=${course.courseId}">
                                        <h5 class="c-course-item-title">${course.name}</h5>
                                        <p class="c-course-item-desc">${course.description}</p>
                                        <ul class="ratings">
                                            <c:forEach begin="1" end="${course.star}">
                                                <i class="c-star fa-solid fa-star selected"></i>
                                            </c:forEach>
                                            <c:forEach begin="${course.star + 1}" end="5">
                                                <i class="c-star fa-solid fa-star"></i>
                                            </c:forEach>
                                            <p class="c-course-isLearning">(${course.numberPeopleLearning})</p>
                                        </ul>
                                        <p class="c-course-item-author">${course.instructorId.firstName} ${course.instructorId.lastName}</p>
                                        <p class="c-course-subject">
                                            <i class="fa-solid fa-book"></i> :
                                            <c:forEach items="${course.listSubject}" var="subject" varStatus="loop">
                                                <c:choose>
                                                    <c:when test="${loop.index == 0}">${subject.name}</c:when>
                                                    <c:otherwise>, ${subject.name}</c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </p>
                                    </a>
                                    <c:set var="isRegister" value="false"/>
                                    <c:forEach items="${listCourseAccount}" var="courseAccount">
                                        <c:if test="${course.courseId == courseAccount.courseId.courseId}">
                                            <c:set var="isRegister" value="true"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${isRegister == true}">
                                        <a href="lesson?id=${course.courseId}" class="btn-for-course">Continue</a>
                                    </c:if>
                                    <c:if test="${isRegister == false}">
                                        <a href="course-detail?id=${course.courseId}" class="btn-for-course">Register</a>
                                    </c:if>
                                </div>


                                <c:if test="${isRegister == true}">
                                    <div class="c-course-item-intro-registered">
                                        <h5>Registered</h5>
                                    </div>
                                </c:if>
                                <c:if test="${isRegister == false}">
                                    <div class="c-course-item-intro-price">
                                        <c:if test="${course.price <= 0}">
                                            <span class="tag-free">Free</span>
                                        </c:if>
                                    </div>
                                </c:if>

                            </div>
                        </div>
                    </c:forEach>
                </div>
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
            <div id="c-course-container-right">
                <div class="c-course-filter">
                    <div class="c-course-filter-main">
                        <h4>Price</h4>
                        <i class="fa-solid fa-angle-down icon-down"></i>
                    </div>

                    <div class="c-course-filter-list">
                        <div class="c-course-filter-item">
                            <input type="checkbox" id="c-course-price-free" value="Free" onchange="searchByPrice()">
                            <label for="c-course-price-free">Free</label>
                        </div>
                        <div class="c-course-filter-item">
                            <input type="checkbox" id="c-course-price-paid" value="Paid" onchange="searchByPrice()">
                            <label for="c-course-price-paid">Paid</label>
                        </div>
                    </div>
                </div>

                <div class="c-course-filter">
                    <div class="c-course-filter-main">
                        <h4>Category</h4>
                        <i class="fa-solid fa-angle-down icon-down"></i>
                    </div>

                    <div class="c-course-filter-list scroll">
                        <form>
                            <div id="holder-search-input">
                                <input type="text" id="holder-search-category" onkeyup="searchCategory()"
                                       placeholder="Search for category...">
                            </div>
                            <div id="form-select-category">
                                <c:forEach items="${listSC}" var="sc">
                                    <div class="search-category-item">

                                        <div class="display-flex justify-between">
                                            <div class="search-category-name">
                                                <input type="checkbox" id="id-category-${sc.categoryID}" onchange="checkedCategory(this)" value="${sc.categoryID}">
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
                <div class="featured-course">
                    <h4 class="feature-course-title">Most feature course</h4>
                    <div class="list-feature-course">
                        <c:forEach items="${listTopFeatureCourse}" var="course">
                            <a href="course-detail?id=${course.courseId}" class="color1c1d1f">
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
        </div>
        <jsp:include page="base-view/footerUser.jsp"/>
        <script src="js/courses.js"></script>
    </body>
</html>
