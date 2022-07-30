<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="./base-view/baseTagAdmin.jsp"></jsp:include>

            <title>Post detail</title>

            <script src="../ckeditor/ckeditor.js"></script>
        </head>

        <body>
            <div class="display-flex min-height-100vh">

            <jsp:include page="./base-view/headerAdmin.jsp"></jsp:include>

                <div class="width85">

                <jsp:include page="./base-view/dropDownAdmin.jsp"></jsp:include>

                    <!-- Container Start -->

                    <div class="container">
                        <h1 class="container-title">Post</h1>
                        <div class="container-table post">
                            <form action="../management/post-detail?bid=${bid}" method="post" class="form-submit" enctype="multipart/form-data">
                            <h4>Title</h4>
                            <input type="text" name="title" class="title-post" id="" value="${Post.getTitle()}" required>
                            <h4>Category</h4>
                            <c:forEach items="${listBlogCategory}" var="categoryBlog">
                                <div class="category-item">
                                    <input type="checkbox" name="blogCategory" value="${categoryBlog.blogCategoryID}" id="${categoryBlog.name}-${categoryBlog.blogCategoryID}"
                                           <c:forEach items="${listCategoryOfBlog}" var="cate">
                                               ${cate.blogCategoryID == categoryBlog.blogCategoryID ? "checked" : ""} 
                                           </c:forEach>
                                           >
                                    <label for="${categoryBlog.name}-${categoryBlog.blogCategoryID}">${categoryBlog.name}</label>
                                </div>
                            </c:forEach>
                            <h4>Display</h4>
                            <div class="property-display">
                                <div>
                                    <input type="radio" value="hidden" id="hidden" name="status" 
                                           <c:choose>
                                               <c:when test="${bid == null}">checked</c:when>
                                               <c:otherwise> 
                                                   ${Post.display ? '' : 'checked'}
                                               </c:otherwise>
                                           </c:choose>
                                           />
                                    <label for="hidden" >Hidden</label>
                                </div>
                                <div>
                                    <input type="radio" value="display" id="display" name="status"
                                           <c:if test="${Post.display}">checked</c:if>
                                               >
                                           <label for="display" >Display</label>
                                    </div>

                                </div>
                                <h4>Link IMG post</h4>
                                <input type="file" name="photo" class="title-post" id="" value="img/${Post.thumbnailUrl}" required>
                            <c:if test="${Post.thumbnailUrl != null}">
                                <img src="../img/${Post.thumbnailUrl}" class="img-file">
                            </c:if>
                            <h4>Description</h4>
                            <textarea required class="post-desc" name="description" id="" cols="66" rows="6">${Post.description}</textarea>
                            <h4>Content</h4>
                            <textarea required id="editor1" name="content" cols="50" rows="10">${Post.content}</textarea>
                            <script>
                                CKEDITOR.replace('content');
                            </script>
                            <input type="submit" value="${action}" class="save" name="action">
                        </form>
                    </div>
                </div>

                <!-- Container End -->
            </div>
        </div>
    </body>

</html>
