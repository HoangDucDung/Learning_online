<%@page import="model.Blog"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.BlogCategory"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>


        <jsp:include page="base-view/baseTag.jsp"></jsp:include>

            <link href="./css/blog-detail.css" rel="stylesheet">

            <title>${blog.title}</title>

    </head>

    <body>

        <jsp:include page="base-view/headerUser.jsp?page=Blog"></jsp:include>

            <!-- Blog Details Start -->
            <div class="blog-detail-container">
                <div class="blog-detail">
                    <h1 class="blog-detail-title" >${blog.title}</h1>
                <div class="blog-detail-author">
                    <img src="img/${blog.authorID.profilePictureUrl}" alt=""
                         class="blog-detail-author-avatar">
                    <div class="blog-detail-create">
                        <p>${blog.authorID.firstName} ${blog.authorID.lastName}</p>
                        <p>${blog.createdDate}</p>
                    </div>
                </div>
                <span>${blog.description}</span>
                <p>${blog.content}</p>
            </div>
            <div class="related-blog">
                <h2>Related blogs</h2>
                <div class="list-related-blog">
                    <c:forEach items="${blogsRelated}" var="blog">
                        <div class="item-related-blog">
                            <a href="blog-detail?blogID=${blog.blogID}">
                                <img src="img/${blog.thumbnailUrl}" class="img-related-blog">
                                <div class="desc-related-blog">
                                    <h4 class="title-related-blog">${blog.title}</h4>
                                    <div class="desc-text">
                                        <span>${blog.description}</span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <!-- Blog Details End -->
        <jsp:include page="base-view/footerUser.jsp"></jsp:include>
    </body>

</html>