<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manage Post</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

        <link rel="stylesheet" type="text/css" href="../css/post-management.css">
        <script src="https://cdn.ckeditor.com/4.19.0/standard/ckeditor.js"></script>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Post"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Manage Post"/>

                    <div class="container">
                        <div class="container-table post">
                            <c:if test="${blog != null}">
                                <div class="alert alert-success d-none" role="alert">
                                    Add successfully
                                </div>
                                <a class="back" href="../management/post-detail?id=${blog.blogID}"><i class="fa-solid fa-angle-left"></i>Back</a>
                                <form action="../management/post-detail" method="POST" class="form-submit"  enctype="multipart/form-data" onsubmit="return checkValue()">
                                    <input type="hidden" value="${blog.blogID}" id="ae-id" name="id">
                                    <h4 class="title">Title (*)</h4>
                                    <input id="ae-title" type="text" name="title" class="input-box w-50" value="${blog.title}" required>
                                    <h4 class="title">Description (*)</h4>
                                    <textarea id="ae-description" type="text" name="description" class="input-box w-50" rows="5"required>${blog.description}</textarea>
                                    <h4 class="title">Thumbnail</h4>
                                    <div class="upload-img">
                                        <input type="hidden" value="${blog.thumbnailUrl}" id="src-img">
                                        <img class="img-thumbnail-url" src="../img/${blog.thumbnailUrl}">
                                        <input type="file" id="file" name="thumbnail" value="" accept="image/*"/>
                                    </div>
                                    <h4 class="title">Display (*)</h4>
                                 
                                    <div>
                                        <input type="radio" name="status" value="1" id="display" ${blog.display == true ? "checked":""}>
                                        <label for="display">Display</label>
                                        <input type="radio" name="status" value="0" id="hidden" ${blog.display == false ? "checked":""}>
                                        <label for="hidden">Hidden</label>
                                    </div>
                                    <div class="c-course-filter">
                                        <div class="c-course-filter-main">
                                            <h4>Category (*)</h4>
                                            <i class="fa-solid fa-angle-down icon-down"></i>
                                        </div>

                                        <div class="c-course-filter-list scroll">
                                            <div id="holder-search-input">
                                                <input type="text" id="holder-search-category" onkeyup="searchCategory()"
                                                       placeholder="Search for category...">
                                            </div>
                                            <div id="form-select-category">
                                                <c:forEach items="${listCategory}" var="c">
                                                    <div class="search-category-item">
                                                        <div class="d-flex justify-content-between">
                                                            <div class="search-category-name">
                                                                <c:set var="isCheck" value="false"/>
                                                                <c:forEach items="${listCategoryOfBlog}" var="cate">
                                                                    <c:if test="${cate.blogCategoryID == c.blogCategoryID}">
                                                                        <c:set var="isCheck" value="true"/>
                                                                        <input name="search-category" type="checkbox" id="id-category-${c.blogCategoryID}" onchange="checkedCategory(this)" checked value="${c.blogCategoryID}">
                                                                    </c:if>                           
                                                                </c:forEach>
                                                                <c:if test="${isCheck == false}">
                                                                    <input  name="search-category" type="checkbox" id="id-category-${c.blogCategoryID}" onchange="checkedCategory(this)" value="${c.blogCategoryID}">
                                                                </c:if>
                                                                <label for="id-category-${c.blogCategoryID}">${c.name}</label>
                                                            </div>
                                                            <i class="fa-solid fa-angle-down icon-down-cate"></i>
                                                        </div>
                                                        <div class="search-sub-category">

                                                            <c:forEach items="${listSubCategory}" var="sc">
                                                                <c:set var="isCheckSub" value="false"/>
                                                                <c:if test="${sc.blogCategoryId.blogCategoryID == c.blogCategoryID}">
                                                                    <div class="search-sub-category-name">
                                                                        <c:forEach items="${listCategoryOfBlog}" var="cate">
                                                                            <c:if test="${isCheckSub == false}">
                                                                                <c:forEach items="${cate.blogSubCategories}" var="sub">
                                                                                    <c:if test="${sub.blogSubCategoryId == sc.blogSubCategoryId}">
                                                                                        <c:set var="isCheckSub" value="true"/>
                                                                                        <input  name="search-sub-category"  checked type="checkbox" id="id-sub-category-${sc.blogSubCategoryId}" onchange="checkedSubCategory(this)" name="search-category" value="${sc.blogSubCategoryId}">
                                                                                        <label for="id-sub-category-${sc.blogSubCategoryId}">${sc.subCategoryName}</label>
                                                                                    </c:if>                                                                                                    
                                                                                </c:forEach>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                        <c:if test="${isCheckSub == false}">
                                                                            <input  name="search-sub-category"  type="checkbox" id="id-sub-category-${sc.blogSubCategoryId}" onchange="checkedSubCategory(this)" name="search-category" value="${sc.blogSubCategoryId}">
                                                                            <label for="id-sub-category-${sc.blogSubCategoryId}">${sc.subCategoryName}</label>
                                                                        </c:if>  
                                                                    </div>
                                                                </c:if>  

                                                            </c:forEach>                 
                                                        </div>
                                                    </div>  
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                    <h4 class="title">Content (*)</h4>
                                    <textarea name="content" id="content" rows="10" cols="150">${blog.content}</textarea>
                                    <script>
                                        CKEDITOR.replace('content');
                                    </script>
                                    <div class="text-danger" id="text-error" style="margin: 15px 0"></div>
                                    <button type="submit" value="Save" class="btn btn-primary" id="button-add-edit" name="action">Save</button>
                                </form>
                            </c:if>
                            <c:if test="${blog == null}">
                                <div class="alert alert-success d-none" role="alert">
                                    Add successfully
                                </div>
                                <a class="back" href="../management/post"><i class="fa-solid fa-angle-left"></i>Back</a>
                                <form method="post" action="../management/post-detail" class="form-submit" enctype="multipart/form-data" onsubmit="return checkValue()">
                                    <input type="hidden" value="0" id="ae-id" name="id">
                                    <h4 class="title">Title (*)</h4>
                                    <input id="ae-title"  type="text" name="title" class="input-box w-50" required>
                                    <h4 class="title">Description (*)</h4>
                                    <textarea id="ae-description"  type="text" name="description" class="input-box w-50" rows="5" required></textarea>
                                    <h4 class="title">Thumbnail</h4>
                                    <div class="upload-img">
                                        <input type="hidden" value="" id="src-img">
                                        <input type="file" name="thumbnail" id="file" value="" accept="image/*"/>
                                    </div>
                                    <h4 class="title">Display (*)</h4>
                                    <div>
                                        <input type="radio" name="status" value="1" id="display" checked>
                                        <label for="display">Display</label>
                                        <input type="radio" name="status" value="0" id="hidden" >
                                        <label for="hidden">Hidden</label>
                                    </div>
                                    <div class="c-course-filter">
                                        <div class="c-course-filter-main">
                                            <h4>Category (*)</h4>
                                            <i class="fa-solid fa-angle-down icon-down"></i>
                                        </div>

                                        <div class="c-course-filter-list scroll">
                                            <div id="holder-search-input">
                                                <input type="text" id="holder-search-category" onkeyup="searchCategory()"
                                                       placeholder="Search for category...">
                                            </div>
                                            <div id="form-select-category">
                                                <c:forEach items="${listCategory}" var="c">
                                                    <div class="search-category-item">

                                                        <div class="d-flex justify-content-between">
                                                            <div class="search-category-name">

                                                                <input type="checkbox" id="id-category-${c.blogCategoryID}" onchange="checkedCategory(this)" value="${c.blogCategoryID}" name="search-category">
                                                                <label for="id-category-${c.blogCategoryID}">${c.name}</label>
                                                            </div>
                                                            <i class="fa-solid fa-angle-down icon-down-cate"></i>
                                                        </div>
                                                        <div class="search-sub-category">
                                                            <c:forEach items="${listSubCategory}" var="sc">
                                                                <c:if test="${sc.blogCategoryId.blogCategoryID == c.blogCategoryID}">
                                                                    <div class="search-sub-category-name">
                                                                        <input type="checkbox" id="id-sub-category-${sc.blogSubCategoryId}" onchange="checkedSubCategory(this)" name="search-sub-category" value="${sc.blogSubCategoryId}">
                                                                        <label for="id-sub-category-${sc.blogSubCategoryId}">${sc.subCategoryName}</label>
                                                                    </div>
                                                                </c:if>                                            
                                                            </c:forEach>                 
                                                        </div>
                                                    </div>  
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                    <h4 class="title">Content (*)</h4>
                                    <textarea name="content" id="content" rows="10" cols="150"></textarea>
                                    <script>
                                        CKEDITOR.replace('content');
                                    </script>
                                    <div class="text-danger" id="text-error" style="margin: 15px 0"></div>
                                    <button type="submit" class="btn btn-primary" id="button-add-edit" name="action" value="add">Add</button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JavaScript -->    
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
        <script type="text/javascript" src="../js/post-add-edit.js"></script>
    </body>

</html>