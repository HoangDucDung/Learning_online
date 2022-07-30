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

        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css">
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/post-management.css">

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
                        <!-- Modal -->
                        <div id="pm-container-head" class="d-flex justify-content-between">
                            <form action="../management/post-detail" method="POST">
                                <button type="submit" class="btn btn-primary" style="margin: 0"><i class="fa-solid fa-plus"></i>ADD</button>
                            </form>
                            <div id="search-holder" class="d-flex">                         
                                <input placeholder="Select date" type="date" id="date-picker" class="form-control" onchange="searchByValue()">
                                <select class="custom-select" onchange="searchByValue()">
                                    <option selected value="All" id="All">All</option>
                                    <option value="Display">Display</option>
                                    <option value="Hide">Hide</option>
                                </select>
                                <div class="d-flex position-relative afterTemp">
                                    <p class="fa-solid fa-filter icon-filter"></p>
                                    <div id="search-category">
                                        <form>
                                            <div id="holder-search-input">
                                                <input type="text" id="holder-search-category" onkeyup="searchCategory()"
                                                       placeholder="Search for category...">
                                                <button type="button" id="clear-btn" onclick="clearValueSearch()">Clear</button>
                                            </div>
                                            <div id="form-select-category">
                                                <c:forEach items="${listCategory}" var="c">
                                                    <div class="search-category-item">

                                                        <div class="d-flex justify-content-between">
                                                            <div class="search-category-name">
                                                                <input type="checkbox" id="id-category-${c.blogCategoryID}" onchange="checkedCategory(this)">
                                                                <label for="id-category-${c.blogCategoryID}">${c.name}</label>
                                                            </div>
                                                            <i class="fa-solid fa-angle-down icon-down-cate" onclick="dropDownSubCate(this)"></i>
                                                        </div>
                                                        <div class="search-sub-category">
                                                            <c:forEach items="${listSubCategory}" var="sc">
                                                                <c:if test="${sc.blogCategoryId.blogCategoryID == c.blogCategoryID}">
                                                                    <div class="search-sub-category-name">
                                                                        <input type="checkbox" id="id-sub-category-${sc.blogSubCategoryId}" onchange="checkedSubCategory(this)" name="search-category" value="${sc.blogSubCategoryId}">
                                                                        <label for="id-sub-category-${sc.blogSubCategoryId}">${sc.subCategoryName}</label>
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

                        <table id="tablesss" class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th style="width: 50px">Thumbnail</th>
                                    <th style="width: 300px">Title</th>
                                    <th style="width: 200px">Category</th>
                                    <th>Date Created</th>
                                    <th>Status</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody id="myTable">
                                <c:forEach items="${listBlog}" var="blog" >
                                    <tr>
                                        <td>${blog.blogID}</td>
                                        <td style="width: 50px"><img style="width: 100%" class="img-thumbnail-blog" src="../img/${blog.thumbnailUrl}"></td>
                                        <td>${blog.title}</td>

                                        <td>
                                            <c:forEach items="${blog.getBlogCategories()}" var="blogCategory" varStatus="loop">
                                                <c:choose>
                                                    <c:when test="${loop.index == 0}">
                                                        ${blogCategory.name}
                                                    </c:when>
                                                    <c:otherwise>
                                                        , ${blogCategory.name} 
                                                    </c:otherwise>
                                                </c:choose>

                                            </c:forEach>
                                        </td>

                                        <td>${blog.createdDate}</td>
                                        <td>${blog.display ? 'Display' : 'Hidden'}</td>
                                        <td><a class="text-primary myTable-change myTable-change1" href="post-detail?id=${blog.blogID}" >View</a> / 
                                            <a class="text-danger myTable-change myTable-change2" href="#" onclick="deleteBlog(${blog.blogID})">Delete</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>         
                    </div>
                </div>
            </div>
        </div>
        <!-- Bootstrap JavaScript -->    
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
        <script src="../js/post-management.js"></script>
    </body>

</html>