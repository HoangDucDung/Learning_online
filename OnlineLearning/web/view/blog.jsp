<%@page import="java.sql.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html translate="no">
    <head>
        <jsp:include page="base-view/baseTag.jsp"></jsp:include>

            <link href="css/blog.css" rel="stylesheet" type="text/css" />
            <title>Blog</title>
        </head>

        <body>

        <jsp:include page="base-view/headerUser.jsp"></jsp:include>

            <!-- Intro Start -->

            <div id="intro" 
                 style ="background-image: url('img/${bcMost.thumbnailUrl}')"
            >
            <div class="intro-child">
                <h1>Blogs</h1>
            </div>
        </div>

        <!-- Intro End -->

        <!-- Blog Start -->
        <div class="container-xxl">
            <div class="search">
                <div class="search-child">
                    <form action="blog" method="get">
                        <input type="text" value="${search}" name="search" placeholder="Search" id="search-input">
                    </form>
                </div>
            </div>
            <div id="container">

                <div class="blog-container">
                    <div class="blog-container-left">
                        <ul class="blog-container-right-toppic-list">
                            <li class="blog-container-right-toppic-item">
                                <a href="blog" class="${bcid == null ? 'active-category-blog' : ''}">All Category</a>
                            </li>
                            <c:forEach items="${bcList}" var="bc">
                                <li class="blog-container-right-toppic-item">
                                    <a href="blog?bcid=${bc.getBlogCategoryID()}" class="${bc.getBlogCategoryID() == bcid ? 'active-category-blog' : ''}">${bc.getName()}</a>
                                </li>
                            </c:forEach>
                            
                        </ul>
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
                                                        <img src="img/${bl.getAuthorID().profilePictureUrl}"
                                                             alt="" class="blog-item-author-avatar">
                                                        <span class="blog-item-author-name">${bl.getAuthorID().firstName} ${bl.getAuthorID().lastName}</span>
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
                        <c:choose>
                            <c:when test="${not empty blogList}">                        
                                <%@include file="page.jsp" %>
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="blog-container-right">                  
                        <form action="blog" method="get" class="margin-top-20 search-date">
                            <div>
                                <input type="week" value="${searchweek}" name="SearchWeek" id="search-input">
                                <button type="submit" id="search-input"><i class="fa fa-search"></i></button>
                            </div>
                        </form>
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
    </body>

</html>
