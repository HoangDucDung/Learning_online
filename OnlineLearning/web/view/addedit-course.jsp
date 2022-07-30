<%-- 
    Document   : addedit-course
    Created on : Jul 8, 2022, 2:40:01 PM
    Author     : FPTSHOP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Course-${act}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <link href="../css/addedit-course.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="../js/addedit-course.js" type="text/javascript"></script>
        <script src="https://cdn.ckeditor.com/4.19.0/standard/ckeditor.js"></script>

    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Course"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Subject ${act}"/>
                    <form>
                        <div class="contain">
                            <div class="content-left">
                                <div class="content">
                                    <h6>Course Name:</h6>
                                    <div class="ct-input">
                                        <input value="${course.getName()}">
                                    </div>
                                </div>
                                <div class="content dates">
                                    <div>
                                        <h6>Display:</h6>
                                        <div>
                                            <select class="display" name="display">
                                                <option value="1">Published</option>
                                                <option value="0">Unpublished</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div>
                                        <h6>Price:</h6>
                                        <div class="ct-input date">
                                            <input value="${course.getName()}">
                                        </div>
                                    </div>
                                </div>
                                <div class="content">
                                    <h6>TinyPictureUrl:</h6>
                                    <div class="ct-input">
                                        <input value="${course.getName()}">
                                    </div>
                                </div>
                                <div class="content">
                                    <h6>ThumbnailUrl:</h6>
                                    <div class="ct-input">
                                        <input value="${course.getName()}">
                                    </div>
                                </div>
                                <div class="content">
                                    <h6>VideoIntroduce:</h6>
                                    <div class="ct-input">
                                        <input value="${course.getName()}">
                                    </div>
                                </div>
                            </div>
                            <div class="content-right">
                                <div class="content">
                                    <div class="category">
                                        <h6>Category</h6>
                                        <div class="title"><span>Category</span><i class="fa-solid fa-angle-down"></i></div>
                                        <div class="contain-category-notshow" id="type-show">
                                            <div class="filter-scroll">
                                                <div id="search-category-box">
                                                    <input id="search-category" type="text" value="" placeholder="Search for category name"/>
                                                </div>
                                                <c:forEach items="${listSC}" var="sc">
                                                    <div class="filter-subject">
                                                        <div class="filter-subject-name">
                                                            <input type="checkbox" value="${sc.categoryID}" class="title-subject ${sc.categoryID}">
                                                            <label>${sc.name}</label>
                                                        </div>
                                                    </div>
                                                    <div class="sub sub-filter-subject">
                                                        <c:forEach items="${listSubject}" var="subject">
                                                            <c:if test="${subject.categoryID.categoryID == sc.categoryID}">
                                                                <div class="element-filter category">
                                                                    <input type="checkbox" value="${subject.subjectId}" class="type-subject ${sc.categoryID}" onclick="checkCategory(this, ${sc.categoryID})">
                                                                    <label>${subject.name}</label>                                                                        
                                                                </div>
                                                            </c:if>                                            
                                                        </c:forEach>   
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="content">
                                    <div class="action-table">
                                        <h6>Course price package:</h6>
                                        <div class="plus-minus">
                                            <div class="add-tr">
                                                <i class="fa-solid fa-plus"></i>
                                            </div>
                                            <div class="delete-tr">
                                                <i class="fa-solid fa-minus"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <table class="table" id="table">
                                        <thead>
                                            <tr>
                                                <th>Access Package</th>
                                                <th>Month</th>
                                                <th>Price</th>
                                                <th>Sale price</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td><input class="tbinput" type="text"></td>
                                                <td><input class="tbinput numb" type="number"></td>
                                                <td><input class="tbinput" type="text"></td>
                                                <td><input class="tbinput" type="text"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="content">
                                    <h6>Description:</h6>
                                    <textarea>${course.getDescription()}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="content-bottom">
                            <div class="about-course">
                            <h6>About Course:</h6>
                            <textarea name="aboutcourse" id="aboutcourse" rows="10" cols="150">${course.getDescription()}</textarea>
                            <script>
                                CKEDITOR.replace('aboutcourse');
                            </script>
                            </div>
                        </div>
                        <a href="lesson-detail?Lid=${lesson.getId()}" class="bt">${act}</a>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>
