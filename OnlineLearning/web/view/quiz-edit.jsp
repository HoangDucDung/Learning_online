<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <jsp:include page="base-view/baseTagAdmin.jsp"></jsp:include>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <!-- Bootstrap CSS -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
                  integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
            <!--Font Awesome-->
            <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
            <!--Jquery-->
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
            <link href="../css/setting.css" rel="stylesheet" type="text/css"/>
            <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
            <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
            <script src="../js/setting.js" type="text/javascript"></script>
            <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />
            <script src="https://unpkg.com/flowbite@1.4.7/dist/flowbite.js"></script>
            <script src="../js/dashboard.js"></script>
            <script src="../js/table.js"></script>
            <title>Quiz Detail</title>
        </head>

        <body>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Quiz"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Quiz"/>
                    <a class="back" href="../management/quizsetting"><i class="fa-solid fa-angle-left"></i>Back</a>
                    <br/>
                    <div class="container">

                        <div class="container-table">

                            <div class="table-content">


                                <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                                    <form action="quizedit" method="POST">

                                        <div class="grid gap-6 mb-6 lg:grid-cols-2">
                                            <div>
                                                <label for="id" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">ID</label>
                                                <input type="hidden" name="quizid" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" value="${quizLessonDetail.quizID}" required="">${quizLessonDetail.quizID}
                                                <input type="hidden" name="lessonid" value="${quizLessonDetail.lessonID}">
                                            </div>
                                            <br/>
                                            <div>
                                                <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Name</label>
                                                <input pattern="^[^-\s][a-zA-Z0-9_\s-]*$" type="text" id="company" name="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" value="${quizLessonDetail.name}" required="">
                                            </div>
                                            <br/>
                                            <select id="countries"  name="subject" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                                <c:forEach items="${allSubjectName}" var="subject">
                                                    <option
                                                        ${(subjectNamebyID.name eq subject.name)?"selected=\"selected\"":""}
                                                        value="${subject.subjectId}">${subject.name}</option>
                                                </c:forEach>
                                            </select>
                                            <br/>
                                            <select id="countries"  name="level" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                                <c:forEach items="${allQuizLevel}" var="level">
                                                    <option
                                                        ${(quizLessonDetail.level eq level.level)?"selected=\"selected\"":""}
                                                        value="${level.level}">${level.level}</option>
                                                </c:forEach>
                                            </select>
                                            <br/>
                                            <div>
                                                <label for="totalques" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Total Question</label>
                                                <input pattern="[1-9]{1,100}" type="text" id="phone" name="totalques" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" value="${quizLessonDetail.totalQues}" required="">
                                            </div>
                                            <br/>
                                            <div>
                                                <label for="duration" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Duration</label>
                                                <input pattern="[1-9]{1,100}" type="text" id="phone" name="duration" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" value="${quizLessonDetail.examTimeInMinute}" required="">
                                            </div>
                                            <br/>
                                            <div>
                                                <label for="passrate" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Pass Rate</label>
                                                <input pattern="[1-9]{1,100}" type="text" id="phone" name="passrate" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" value="${quizLessonDetail.passScore}" required="">
                                            </div>
                                            <br/>
                                            <div>
                                                <label for="type" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Type</label>
                                                <input type="hidden" id="phone" name="type" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" value="${quizLessonDetail.type}" required="">${quizLessonDetail.type}
                                            </div>
                                            <br/>
                                        </div>
                                        <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Edit</button>
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