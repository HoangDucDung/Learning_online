<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Dashboard</title>

        <!-- Bootstrap CSS -->
        <link href="../node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="../node_modules/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--JQuery-->
        <script src="../node_modules/jquery/dist/jquery.min.js"></script>

        <style>
            .table-wraper {
                overflow: auto;
                height: 40vh;
            }
        </style>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <!--Sidebar-->
                <div class="col-1 col-sm-2 bg-dark p-0 collapse-horizontal overflow-auto vh-100" id="navbarTogglerDemo01">
                    <jsp:include page="sidenav.jsp?page=A"/>
                </div>

                <!--Page Content-->
                <div class="col p-0 vh-100 overflow-auto">
                    <jsp:include page="navbar-header.jsp?page=A"/>
                    
                    <div class="container bg-light">
                        <div>
                            <p>Quiz Name: ${quizLesson.name}</p>
                            <p>Quiz Time: ${quizLesson.examTimeInMinute}</p>
                            <p>Pass Score: ${quizLesson.passScore}</p>
                        </div>
                    </div>
                    <c:forEach var="q" items="${questions}">
                        <div>
                            <h4>${q.text}</h4>
                            <ul>
                                <c:forEach var="a" items="${q.answers}">
                                    <li>${a.answerText} <span>${(a.status != 0) ? "True" : "False"}</span></li>
                                    </c:forEach>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>