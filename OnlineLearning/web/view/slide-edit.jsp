<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manage Slide</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link href="../css/slide-detail.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="../js/slide-detail.js" type="text/javascript"></script>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Slide"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Slide Detail"/>

                    <div class="container">
                        <div class="container-table post">
                            <a class="back" href="../management/slide-list"><i class="fa-solid fa-angle-left"></i>Back</a>
                            <form action="../management/slide-edit?id=${id}" onsubmit="return isSubmitAvailable()" method="post" class="form-submit" enctype="multipart/form-data">
                                <h4 class="title">Title</h4>
                                <input pattern="^[^-\s][a-zA-Z0-9_\s-]*$" type="text" name="title" maxlength="200" id="input-title" class="input-box form-control" value="${slider.title}" required>
                                <span style="margin-bottom: 30px;" class="text-danger title">Title cannot be left blank</span>
                                <h4 class="title">Sub title</h4>
                                <input pattern="^[^-\s][a-zA-Z0-9_\s-]*$" type="text" name="subTitle" maxlength="200" id="input-subtitle" class="input-box form-control" value="${slider.subTitle}" required>
                                <span style="margin-bottom: 30px;" class="text-danger subtitle">Sub title cannot be left blank</span>
                                <h4 class="title">Image</h4>
                                <div class="upload-img">
                                    <c:if test="${id != null}"><img src="../img/${slider.imageUrl}"></c:if>
                                    <input type="file" name="photo" id="file" class="inputfile form-control" data-multiple-caption="{count} files selected" accept="image/*" <c:if test="${id == null}">required</c:if> >
                                    </div>
                                    <h4 class="title">Description</h4>
                                    <textarea pattern="^[^-\s][a-zA-Z0-9_\s-]*$" name="description" id="input-description" class="input-box form-control" rows="5" cols="50">${slider.description}</textarea>
                                    <span style="margin-bottom: 30px;" class="text-danger description">Description cannot be left blank</span>
                                <h4 class="title">Backlink</h4>
                                <input pattern="[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*" type="text" name="backlink" maxlength="3000" id="input-backlink" class="input-box form-control" value="${slider.navigationLink}" required>
                                <span style="margin-bottom: 30px;" class="text-danger backlink">Backlink cannot be left blank</span>

                                <input type="submit" value="${action}" class="save" name="action">
                            </form>
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