<%@page import="java.sql.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Blog</title>
        <jsp:include page="base-view/baseTag.jsp"></jsp:include>
            <script src="https://code.jquery.com/jquery-1.10.0.min.js" 
                    integrity="sha256-2+LznWeWgL7AJ1ciaIG5rFP7GKemzzl+K75tRyTByOE=" crossorigin="anonymous">
            </script> 
            <link href="css/blog.css" rel="stylesheet" type="text/css" />
            <title>Blog</title>
        </head>

        <body>

        <jsp:include page="base-view/headerUser.jsp?page=Blog"></jsp:include>

            <!-- Intro Start -->

            <div id="intro" 
                 style ="background-image: url('img/${bcMost.thumbnailUrl}')"
            >
            <div class="intro-child">
                <h1>Blogs</h1>
            </div>
        </div>

        <!-- Intro End -->
        <div id="my-course-header">
            <div id="form-search-info">
                <input type="date" class="selected-tag" id="date-join" name="date-join" onchange="searchProperty()"/>
                <div class="display-flex">
                    <input type="text" id="search-my-course" placeholder="Search for blog..." onkeyup="searchProperty()">
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
                                <c:forEach items="${listBC}" var="bc">
                                    <div class="search-category-item">

                                        <div class="display-flex justify-between">
                                            <div class="search-category-name">
                                                <input type="checkbox" id="id-category-${bc.blogCategoryID}" onchange="checkedCategory(this)">
                                                <label for="id-category-${bc.blogCategoryID}">${bc.name}</label>
                                            </div>
                                            <i class="fa-solid fa-angle-down icon-down-cate" onclick="dropDownSubCate(this)"></i>
                                        </div>
                                        <div class="search-sub-category">
                                            <c:forEach items="${listSubCate}" var="sub">
                                                <c:if test="${sub.blogCategoryId.blogCategoryID == bc.blogCategoryID}">
                                                    <div class="search-sub-category-name">
                                                        <input type="checkbox" id="id-sub-category-${sub.blogSubCategoryId}" onchange="checkedSubCategory(this)" name="search-category" value="${sub.blogSubCategoryId}">
                                                        <label for="id-sub-category-${sub.blogSubCategoryId}">${sub.subCategoryName}</label>
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
        <!-- Blog Start -->
        <div class="container-xxl">
            <div id="container">
                <div class="blog-container">
                    <div class="blog-container-left">
                        
                        <ul class="blog-list" id="container-left">
                            <c:choose>
                                <c:when test="${blogList == null}">
                                    <p>No matching records found</p>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${blogList}" var="bl">
                                        <li class="blog-item">
                                            <a href="blog-detail?blogID=${bl.blogID}">
                                                <div class="blog-item-header">
                                                    <div class="blog-item-author">
                                                        <img src="img/${bl.authorID.profilePictureUrl}"
                                                             alt="" class="blog-item-author-avatar">
                                                        <span class="blog-item-author-name">${bl.authorID.firstName} ${bl.authorID.lastName}</span>
                                                    </div>
                                                </div>
                                                <div class="blog-item-body">
                                                    <div class="blog-item-body-left">
                                                        <h3 class="blog-item-body-title">${bl.getTitle()}</h3>
                                                        <p class="blog-item-body-description">${bl.getDescription()}</p>
                                                        <p class="blog-item-body-time">${bl.createdDate}</p>
                                                    </div>
                                                    <div class="blog-item-body-right">
                                                        <img src="img/${bl.getThumbnailUrl()}"
                                                             alt="" class="blog-item-body-right-img">
                                                    </div>
                                                </div>
                                            </a>
                                        </li>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </ul>
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
                    <div class="blog-container-right">                  
                        
                        <div class="blog-container-right-title margin-top-20" >
                            <h3>Most views</h3>
                        </div>
                        <ul class="blog-container-right-toppic-list">
                            <c:forEach items="${bcMostView}" var="mv">
                                <li class="blog-item">
                                    <a href="blog-detail?blogID=${mv.blogID}">
                                        <div class="blog-item-header">
                                            <div class="blog-item-author">
                                                <img src="img/${mv.getAuthorID().profilePictureUrl}"
                                                     alt="" class="blog-item-author-avatar">
                                                <span class="blog-item-author-name">${mv.getAuthorID().firstName} ${mv.getAuthorID().lastName}</span>
                                            </div>
                                        </div>
                                        <div class="blog-item-body">
                                            <div class="blog-item-body-left">
                                                <h3 class="blog-item-body-title">${mv.getTitle()}</h3>
                                                <p class="blog-item-body-time">${mv.createdDate}</p>
                                                <p class="blog-item-body-time"><i class="fa fa-eye"></i> ${mv.getNumberOfView()}</p>
                                            </div>
                                            <div class="blog-item-body-right">
                                                <img src="img/${mv.getThumbnailUrl()}"
                                                     alt="" class="blog-item-body-right-img-most">
                                            </div>
                                        </div>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!-- Blog End -->

        <jsp:include page="base-view/footerUser.jsp"></jsp:include>
        <script src="js/blog.js"></script>
    </body>

</html>
