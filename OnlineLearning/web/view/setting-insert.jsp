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
        <link href="../css/setting.css" rel="stylesheet" type="text/css"/>
        <script src="../js/setting.js" type="text/javascript"></script>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />
        <script src="https://unpkg.com/flowbite@1.4.7/dist/flowbite.js"></script>
        <style>
            .back-btn {
                text-decoration: none;
                font-weight: 500;
                margin-left: 30px;
            }

            .back-btn a {
                text-decoration: none;
            }
        </style>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Setting"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Setting"/>
                    <div class="container">
                        <div class="container-table">
                            <div class="back-btn text-primary" >
                                <i class="fa-solid fa-angle-left"></i>
                                <a href="./setting">Back</a>
                            </div>
                            <div class="table-content">

                                <form action="setting-insert" method="POST">
                                    <div class="grid gap-6 mb-6 lg:grid-cols-2">
                                        <div>
                                            <label for="type" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Choose a type</label>
                                            <select id="countries" onchange="genderChanged(this)"  name="type" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                                <c:forEach items="${allSettings_DistinctType}" var="setting">
                                                    <option
                                                        value="${setting.type}">${setting.type}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <br/>
                                        <div style="display: none;" id="superblog">
                                            <label for="blogcategory" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Choose a Blog Category</label>
                                            <select id="countries"  name="superblog" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                                <c:forEach items="${allBlogCategory}" var="blog">
                                                    <option
                                                        value="${blog.blogCategoryID}">${blog.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <br/>
                                        <div style="display: none;" id="mainsubject">
                                            <label for="subjectcategory" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Choose a Subject Category</label>
                                            <select id="countries"  name="mainsubject" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                                <c:forEach items="${allSubjectMainCategories}" var="subject">
                                                    <option
                                                        value="${subject.categoryID}">${subject.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <br/>
                                        <div>
                                            <label for="Value" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Value</label>
                                            <input  pattern="^[^-\s][a-zA-Z0-9_\s-]*$" type="text" id="company" name="value" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required="">
                                        </div>
                                        <br/>
                                        <div>
                                            <label for="Order" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Order</label>
                                            <input pattern="[1-9]{1,100}"  type="text" id="phone" name="order" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required="">
                                        </div>
                                        <br/>
                                        <div>
                                            Status: 
                                            <input type="radio" 
                                                   checked="checked"
                                                   name="status" value="active"/> Active 
                                            <input type="radio" 
                                                   name="status" value="deactive"/> Deactive 
                                        </div>
                                    </div>
                                    <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Insert</button>
                                </form>
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

