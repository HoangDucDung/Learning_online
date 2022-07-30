<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quiz</title>
        <jsp:include page="base-view/baseTag.jsp"></jsp:include>

            <link href="./node_modules/bootstrap/dist/css/bootstrap-grid.min.css" rel="stylesheet">
            <link href="./node_modules/bootstrap/dist/css/bootstrap-utilities.min.css" rel="stylesheet">
            <style>
                .btn-redo {
                    background-color: #20c997;
                    padding: 20px;
                    display: inline-block;
                    width: 150px;
                    text-align: center;
                    color: white;
                    font-weight: bold;
                    border-radius: 5px;
                }
            </style>
        </head>

        <body>
        <jsp:include page="base-view/headerUser.jsp"></jsp:include>

            <div class="container-fluid p-5">
                <div class="mt-5">
                    <h1 class="">${quiz.name}</h1>
                <p class="mt-2 mb-4"><strong>Quiz Time: </strong>${quiz.examTimeInMinute}min</p>
            </div>

            <div class="row mt-5">
                <div class="col-9 mt-4">
                    <h4 class="mb-2">Submit your assignment</h4>
                    <p><strong>Due </strong> ${dueTime}</p>
                </div>

                <div class="col-3 position-relative">
                    <a class="btn-redo position-absolute end-0" 
                       href="./quiz?id=${quiz.id}&redo-quiz=true">
                        ${empty completedQuizTime.EndTime ? "Start Quiz" : "Take the Quiz Again"}
                    </a>
                </div>
            </div>

            <div class="mt-4">
                <hr>
            </div>

            <div class="row mt-5">
                <div class="col-9 mt-4">
                    <h4 class="mb-2">Receive grade</h4>
                    <p><strong>To Pass </strong>${quiz.passScore} true answers or more.</p>
                    </br>
                    <c:if test="${find != null}">
                          <div>
                              <span style="font-weight: bolder;">Last Result Doing: </span>
                              <span>${score} of ${total}</span>
                              <br/>
                              <span style="font-weight: bolder;">Last Time Doing: </span>
                              <span>${lastDoing}</span>
                              <c:if test="${score >= passScore}">
                                  <h6 style="color:green;font-size: 25px;">PASSED THE EXAM</h6>
                              </c:if> 
                              <c:if test="${score < passScore}">
                                  <h6 style="color:red;font-size: 25px;">FAILED THE EXAM</h6> 
                              </c:if> 
                          </div>
                    </c:if>
                </div>

                <div class="col-3 position-relative">
                    <c:if test = "${completedQuizTime.EndTime != null}">
                        <a class="btn-redo position-absolute end-0" 
                           href="./reviewquiz?lID=${param['id']}" hidden="${empty completedQuizTime.EndTime ? "true" : ""}">
                            Review
                        </a>
                    </c:if>
                </div>
            </div>
            <div class="mt-4">
                <hr>
            </div>
        </div>
    </body>
</html>
