<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <jsp:include page="base-view/baseTag.jsp"></jsp:include> 
            <link rel="stylesheet" href="css/home.css"/>
            <link rel="stylesheet" href="css/my-course.css">
            <script src="https://code.jquery.com/jquery-1.10.0.min.js" 
                    integrity="sha256-2+LznWeWgL7AJ1ciaIG5rFP7GKemzzl+K75tRyTByOE=" crossorigin="anonymous">
            </script> 
            <title>My Courses</title>

        </head>

        <body>


        <jsp:include page="base-view/headerUser.jsp?page=My Courses"></jsp:include>

            <!-- Intro Start -->

            <div id="intro">
                <div class="intro-child">
                    <h1>My Courses</h1>
                </div>
            </div>

            <!-- Intro End -->


            <div id="my-course-header">
                <div id="form-search-info">
                    <input type="date" class="selected-tag" id="date-join" name="date-join" onchange="searchProperty()"/>
                    <select class="selected-tag" name="progress" id="progress-bar" onchange="searchProperty()">
                        <option id="All">All</option>
                        <option id="In-Progress">In Progress</option>
                        <option id="Completed">Completed</option>>
                    </select>
                    <div class="display-flex">
                        <input type="text" id="search-my-course" placeholder="Search for course..." onkeyup="searchProperty()">
                        <button type="button" id="search-icon" onclick="searchProperty()">
                            <i class="fa-solid fa-magnifying-glass"></i>
                        </button>
                    </div>
                    <div class="display-flex position-relative afterTemp">
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

                                        <div class="display-flex justify-between">
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
        </div>

        <div class="my-course"> 
            <div id="my-course-list">
                <c:if test="${listCourseAccount == null || listCourseAccount.size() == 0}">
                    <div id="no-course">
                        <h2>There is no course you are learning</h2>
                        <a href="courses">Join a new course</a>
                    </div>
                </c:if>
                <c:if test="${listCourseAccount != null}">
                    <c:forEach items="${listCourseAccount}" var="course">
                        <div class="my-course-item">
                            <div class="my-course-item-img">
                                <p class="option-course" onclick="openOption(this)">
                                    <i class="fa-solid fa-ellipsis-vertical "></i>
                                <div class="notice-unenroll">
                                    <p>Unenroll this course?</p>
                                    <form>
                                        <div class="holder-button-delete">
                                            <input type="hidden" value="${course.courseId.courseId}" class="id-course-delete">
                                            <button type="button" class="btn-unenroll" onclick="unenrollCourse(this)">Unenroll</button>
                                            <button type="button" class="btn-cancel" onclick="closeNotice(this)">Cancel</button>
                                        </div>
                                    </form>
                                </div>
                                </p>

                                <img src="${course.courseId.tinyPictureUrl}" alt="">
                                <div class="overlay-img">               
                                    <a href="lesson?id=${course.courseId.courseId}" class="btn-continue">
                                        Get Started
                                    </a>
                                </div>
                            </div>
                            <div class="my-course-item-desc">
                                <div class="my-course-item-title">${course.courseId.name}</div>
                                <ul class="ratings">
                                    <c:forEach begin="1" end="${course.rating}">
                                        <li class="star selected" onclick="voteStar(this, ${course.courseId.courseId})"></li>
                                        </c:forEach>
                                        <c:forEach begin="${course.rating + 1}" end="5">
                                        <li class="star" onclick="voteStar(this, ${course.courseId.courseId})"></li>
                                        </c:forEach>        
                                </ul>
                                <p class="my-course-item-date">${course.enrollDate}</p>
                                <div class="my-course-progress" style="--progress: ${course.progress}%"></div>
                                <p class="text-progress">${course.progress}% Complete</p>
                            </div>
                        </div> 
                    </c:forEach> 
                </c:if>

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

        <jsp:include page="base-view/footerUser.jsp"></jsp:include>

        <script src="js/my-course.js"></script>
    </body>

</html>
