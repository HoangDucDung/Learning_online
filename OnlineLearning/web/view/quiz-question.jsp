<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Quiz</title>

        <!-- Bootstrap CSS -->
        <link href="../node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="../node_modules/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--JQuery-->
        <script src="../node_modules/jquery/dist/jquery.min.js"></script>
        <!--JQuery Confirm-->
        <link href="../node_modules/jquery-confirm/dist/jquery-confirm.min.css" rel="stylesheet">
        <script src="../node_modules/jquery-confirm/dist/jquery-confirm.min.js"></script>

        <style>
            .list-style-none {
                list-style-type: none;
            }
        </style>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <!--Sidebar-->
                <div class="col-1 col-sm-2 bg-dark p-0 collapse-horizontal overflow-auto vh-100" id="navbarTogglerDemo01">
                    <jsp:include page="sidenav.jsp?page=Manage Quiz"/>
                </div>

                <!--Page Content-->
                <div class="col p-0 vh-100 overflow-auto">
                    <jsp:include page="navbar-header.jsp?page=Quiz Question"/>
                    <div class="row px-2">
                        <div class="col-1">
                            <a href="./quizsetting" class="text-primary text-decoration-none">Back</a>
                        </div>
                    </div>

                    <div class="p-2 card m-3">
                        <h5>
                            Quiz: <a href="./quizsettingdetail?id=${quiz.id}" class="text-decoration-none">
                                ${quiz.name}
                            </a>
                        </h5>
                        <h6>Pass Score: ${quiz.passScore}</h6>
                        <h6>Time: ${quiz.examTimeInMinute}  minutes</h6>
                        <h6>Total Question: ${questions.size()}</h6>
                    </div>

                    <div class="row px-4">
                        <div class="col-2">
                            <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#createQuestionModal">
                                + Question
                            </button>
                        </div>

                        <div class="col ms-auto d-flex">
                            <div class="ms-auto">
                                <form method="post" action="${pageContext.request.contextPath}/upload-question-file" enctype="multipart/form-data">
                                    <div class="input-group input-group-sm">
                                        <label class="input-group-text">Import Question</label>
                                        <input name="quizId" value="${quiz.id}" type="hidden">
                                        <input  class="form-control form-control-sm" id="formFileSm" type="file" name="file"/>
                                        <input class="btn btn-primary btn-sm" type="submit" value="Upload" />
                                    </div>
                                    <div>
                                        <span class="text-danger">${param['error']}</span>
                                    </div>
                                </form>
                            </div>

                            <div class="ms-1">
                                <a class="btn btn-primary btn-sm text-white"
                                   href="${pageContext.request.contextPath}/question_template.xlsx">
                                    <i class="fa-solid fa-arrow-down"></i>Template
                                </a>
                            </div>
                        </div>
                    </div>
                    <!-- Modal -->
                    <div class="modal fade" id="createQuestionModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Create Question</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <form action="./question" method="POST">
                                    <div class="modal-body">
                                        <input name="quizId" value="${quiz.id}" type="hidden">
                                        <div class="mb-2">
                                            <label class="form-label">Question<span class="text-danger">*</span></label>
                                            <textarea id="questionTextInput" name="questionTextInput" class="form-control" required
                                                      aria-label="With textarea" rows="2"></textarea>
                                        </div>

                                        <div class="input-group mb-3">
                                            <label class="input-group-text" for="inputGroupSelect01">Level</label>
                                            <select class="form-select" id="questionLevelId" name="questionLevelId">
                                                <c:forEach items="${levels}" var="l">
                                                    <option value="${l.id}">${l.levelName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn" data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn btn-primary">Create</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <ul class="list-style-none">
                        <c:forEach items="${questions}" var="q" varStatus="loop">
                            <li>
                                <div>
                                    <strong>${loop.count}.${q.level.levelName}</strong>
                                    ${q.text}
                                </div>
                                <div class="mt-0">
                                    <a class="btn btn-link btn-sm text-decoration-none" href="./question?questionId=${q.id}">
                                        <i class="fa-solid fa-pen"></i> Edit
                                    </a>
                                    <button class="btn btn-link text-danger btn-sm btn-delete-question text-decoration-none" data-question-id="${q.id}">
                                        <i class="fa-solid fa-trash-can"></i> Delete
                                    </button>
                                </div>
                                <ul class="list-style-none">
                                    <c:forEach items="${q.answers}" var="a">
                                        <li class="mt-2">
                                            <div class="fs-5">
                                                <c:if test="${a.status == 1}">
                                                    <i class="fa-solid fa-square-check text-success"></i>
                                                </c:if>
                                                <c:if test="${a.status == 0}">
                                                    <i class="fa-solid fa-square-xmark text-danger"></i>
                                                </c:if>${a.text}
                                            </div>
                                            <div class="ps-3">
                                                <i class="fa-solid fa-caret-right text-primary"></i>
                                                Explain: ${a.explain}
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>

                    <%--<jsp:include page="management-footer.jsp"/>--%>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/js/quiz-question.js"></script>
    </body>
</html>