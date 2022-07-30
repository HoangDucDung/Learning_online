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
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Post"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Post Detail"/>

                    <div class="container">
                        <div class="container-table post">
                            <a class="back" href="../management/post"><i class="fa-solid fa-angle-left"></i>Back</a>
                            <div class="form-submit">
                                <h4 class="title">Title</h4>
                                <span class="input-box">${blog.title}</span>
                                <h4 class="title">Image</h4>
                                <img class="img-thumbnail-url" src="../img/${blog.thumbnailUrl}">
                                <h4 class="title">Description</h4>
                                <span class="input-box">${blog.description}</span>
                                <h4 class="title">Content Detail</h4>
                                <span class="input-box">${blog.content}</span>
                                <h4 class="title">Created Date</h4>
                                <span class="input-box">${blog.createdDate}</span>
                                <h4 class="title">Author</h4>
                                <span class="input-box">${blog.authorID.firstName} ${blog.authorID.lastName}</span>
                                <h4 class="title">Display</h4>
                                <span class="input-box"><i class="fa-solid button-on-off ${blog.display ? 'fa-toggle-on' : 'fa-toggle-off'}"></i></span>
                                <form action="../management/post-detail" method="POST">
                                    <input type="hidden" value="${blog.blogID}" name="id">
                                    <button type="submit" class="action-btn"><i class="fa-solid fa-pencil"></i>Edit</button>
                                </form>
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JavaScript -->    
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

    </body>

</html>

