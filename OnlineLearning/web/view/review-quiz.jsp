<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        int i = 1;
        ArrayList<String> type = (ArrayList<String>) request.getAttribute("type");
    %>
    <head> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <title>${lesson.name}</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
              integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="css/lesson.css" rel="stylesheet">
        <link rel="stylesheet" href="css/quiz-review.css">
        <link rel="stylesheet" href="css/base.css">
    </head>
    <body>
        <div class="lesson-header">
            <div class="lesson-header-left">
                <a href="quiz?id=${lessonID}"><i class="fa-solid fa-angle-left"></i></a>
                <h4>${course.name}</h4>
            </div>
        </div>
        <div class="review-container">
            <h1 class="review-title">
                <span>${lesson.name}</span>
            </h1>
            <div class="reivew-container-child">
                <div class="review-container-left" id="review-container-left">
                    <div class="review-container-left-left">
                        <h3 class="review-container-left-left-title">Question <span>1</span></h3>
                        <span>Complete</span>
                    </div>
                    <div class="review-container-left-right">
                        <div class="text-question">
                            <p>
                                ${FirstQuiz.getQuestionText()}
                            </p>
                        </div>
                        <div class="select-choice">
                            <div class="answer">
                                <ul>
                                    <c:forEach items="${AnswerQuiz}" var="an">
                                        <li
                                            <c:if test="${selectAns==null}" >
                                                ${an.getStatus()==1?"class='right-choice'":""} 
                                            </c:if>
                                            <c:forEach items="${selectAns}" var="se">
                                                ${an.getStatus()==1?"class='right-choice'":""} 
                                                ${an.getAnswerID()==se.getSelectedAnswerID()&&an.getStatus()==0?"class='wrong-choice'":""}
                                            </c:forEach>>
                                            <input type="radio" <c:forEach items="${selectAns}" var="se">${an.getAnswerID()==se.getSelectedAnswerID()?"checked":""}</c:forEach> disabled>
                                            <label>${an.getAnswerText()}</label>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <div class="answer-desc">${note}</div>
                        <div class="next-previous-question">
                            <button id="btn_next" class="btn-green"> <i class="fa-solid fa-left-long"></i> Previous Question</button>
                            <button id="btn_next" class="btn-green" onclick="showQuiz(${SecondQuiz.getId()}, <%=2%>,${lessonID})">Next <i class="fa-solid fa-right-long"></i></button>
                        </div>
                    </div>
                </div>
                <div class="review-container-right">
                    <p class="review-container-right-title">Quiz navigation</p>
                    <div class="navigation-question">
                        <!--<ul>-->
                        <c:forEach items="${totoalQuiz}" var="bc">
                            <div class="answer-number hover-answer <%=type.get(i-1)%>" onclick="showQuiz(${bc.getId()}, <%=i%>,${lessonID})">
                                <p><%=i++%></p>
                            </div>
                        </c:forEach>
                    </div>
                    <div style="margin-top: 35px">
                        <a href="quiz?id=${lessonID}">Finish review</a>
                    </div>
                    <div style="margin-top: 20px">
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
                </div>
            </div>
        </div>

        <div class="footer">
            <ul>
                <li>
                    <h4>Contact</h4>
                    <p><i class="fa-solid fa-location-dot"></i>Ha Noi, Viet Nam</p>
                    <p><i class="fa-solid fa-phone"></i>+84 123 456 789</p>
                    <p><i class="fa-solid fa-envelope"></i>Email@gmail.com</p>
                </li>
                <li>
                    <h4>Gallery</h4>
                    <div class="gallery-img">
                        <img src="img/course-1.jpg" alt="">
                        <img src="img/course-2.jpg" alt="">
                        <img src="img/course-3.jpg" alt="">
                        <img src="img/course-3.jpg" alt="">
                        <img src="img/course-1.jpg" alt="">
                        <img src="img/course-2.jpg" alt="">

                    </div>
                </li>
                <li>
                    <h4>Newsletter</h4>
                    <p>Dolor amet sit justo amet elitr clita ipsum elitr est.</p>
                </li>
            </ul>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>       
        <script>
                                    function showQuiz(questionID, number, lesson) {
                                        $.ajax({
                                            url: "reviewquiz",
                                            type: "post", //send it through get method
                                            data: {
                                                questionID: questionID,
                                                quizNumber: number,
                                                lID: lesson,
                                            },
                                            success: function (data) {
                                                var quiz = document.getElementById("review-container-left");
                                                quiz.innerHTML = data;
                                            },
                                            error: function (xhr) {
                                                //Do Something to handle error
                                            }
                                        });
                                    }
        </script>
    </body>
</html>
