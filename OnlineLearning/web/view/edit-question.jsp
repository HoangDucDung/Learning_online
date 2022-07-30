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
        <!--JQuery Confirm-->
        <link href="../node_modules/jquery-confirm/dist/jquery-confirm.min.css" rel="stylesheet">
        <script src="../node_modules/jquery-confirm/dist/jquery-confirm.min.js"></script>

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
                    <jsp:include page="navbar-header.jsp?page=Edit Question"/>
                    <div class="row px-2">
                        <div class="col-1">
                            <a href="./quiz-question?quizId=${question.lessonId}" class="text-decoration-none">Back</a>
                        </div>
                    </div>

                    <div class="container bg-light">
                        <input id="questionId" type="hidden" value="${question.id}">
                        <input id="lessonId" type="hidden" value="${question.lessonId}">
                        <div class="form-group mb-2">
                            <label for="questionText">
                                Question<span class="text-danger">*</span>
                            </label>
                            <textarea id="questionText" class="form-control" id="exampleFormControlTextarea1" rows="3">${question.text}</textarea>
                        </div>

                        <div class="row justify-content-end mb-2">
                            <div class="col-2">
                                <label>Level</label>
                                <select id="questionLevelId" class="form-control-sm">
                                    <label>Level</label>
                                    <c:forEach var="l" items="${levels}">
                                        <option value="${l.id}" ${question.level.id == l.id ? "selected" : ""}>${l.levelName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-1">
                                <button id="updateQuestionBtn" class="btn btn-primary btn-sm">Update</button>
                            </div>

                            <div class="col-1">
                                <button id="createAnswerBtn" class="btn btn-primary btn-sm">+Answer</button>
                            </div>
                        </div>


                        <!-- Modal -->
                        <div class="modal fade" id="answerModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Create Answer</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <input id="questionId" value="${question.id}" type="hidden" readonly>
                                        <input id="answerId" type="hidden" value="" readonly="">
                                        <div class="mb-3">
                                            <label for="answerText" class="form-label">Answer<span class="text-danger">*</span></label>
                                            <input type="text" class="form-control" id="answerText" name="answerText">
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" id="answerStatus">
                                            <label class="form-check-label" for="answerStatus">
                                                Mark as true answer
                                            </label>
                                        </div>
                                        <div class="mb-3">
                                            <label for="answerExplain" class="form-label">Explain</label>
                                            <textarea class="form-control" id="answerExplain" name="answerExplain" rows="3"></textarea>
                                        </div>

                                    </div>
                                    <div class="modal-footer">
                                        <lable id="countNewAnswer">
                                            <!--Notification part-->
                                        </lable>
                                        <button type="button" class="btn btn-sm" data-bs-dismiss="modal">Cancel</button>
                                        <button id="submitAnswerBtn" type="button" class="btn btn-primary btn-sm">Create</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <table class="table table-sm table-striped">
                            <thead>
                            <th>Answer</th>
                            <th>Explain</th>
                            <th>Correct</th>
                            <th></th>
                            </thead>
                            <tbody>
                                <c:forEach items="${question.answers}" var="a">
                                    <tr>
                                        <td>${a.text}</td>
                                        <td>${a.explain}</td>
                                        <td>
                                            <c:if test="${a.status == 0}">
                                                <i class="fa-solid fa-circle-xmark text-danger"></i>
                                            </c:if>
                                            <c:if test="${a.status == 1}">
                                                <i class="fa-solid fa-circle-check text-success"></i>
                                            </c:if>
                                        </td>
                                        <td>
                                            <button data-answer-id="${a.id}" data-answer-text="${a.text}" data-answer-explain="${a.explain}" 
                                                    data-answer-status="${a.status}" data-question-id="${question.id}"
                                                    class="btn btn-link btn-edit-answer btn-sm p-0 m-0">Edit</button>
                                            <button data-answer-id="${a.id}" class="btn btn-link btn-delete-answer btn-sm p-0 m-0">Delete</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <jsp:include page="management-footer.jsp"/>

                </div>
            </div>

            <!--Toast-->
            <div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
                <div id="liveToast" class="toast hide" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header bg-warning">
                        <i class="fa-solid fa-bell me-1"></i>
                        <strong class="me-auto"> Notice</strong>
                        <small></small>
                        <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div id="toastMsg" class="toast-body">
                    </div>
                </div>
            </div>
        </div>

        <script src="../js/edit-question.js"></script>
    </body>
</html>