<%@page import="model.LessonBeingLearned"%>
<%@page import="model.Lesson"%>
<%@page import="java.sql.Date"%>
<%@page import="java.sql.Timestamp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <jsp:include page="base-view/baseTag.jsp"></jsp:include>
        <c:if test="${listSubLesson != null}">
            <title>${listSubLesson.get(0).listLesson.get(0).courseID.name}</title>
        </c:if>
        <link href="css/lesson.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-1.10.0.min.js" 
                integrity="sha256-2+LznWeWgL7AJ1ciaIG5rFP7GKemzzl+K75tRyTByOE=" crossorigin="anonymous">
        </script>  

        <c:set var="timeCurrent" value="<%= new Timestamp(System.currentTimeMillis())%>" ></c:set>
        </head>

    </head>

    <body>
    <c:if test="${lessonBeingLearned != null}">
        <div id="overlay-continue" onclick="closeNoticeContinue()"></div>
        <div id="notice-continue">
            <div id="notice-continue-header">
                <span>Notification</span>
                <i class="fa-solid fa-x" id="closeNotice-icon" onclick="closeNoticeContinue()"></i>
            </div>

            <%
                LessonBeingLearned lessonBeing = (LessonBeingLearned) request.getAttribute("lessonBeingLearned");
                int time = lessonBeing.getTimeContinue();
                String minute = "";
                if (time / 60 < 10) {
                    minute = "0" + (int) (time / 60);
                } else {
                    minute = (int) (time / 60) + "";
                }
                String second = "";
                if (time % 60 < 10) {
                    second = "0" + (int) (time % 60);
                } else {
                    second = (int) (time % 60) + "";
                }
            %>

            <div id="notice-continue-body">
                <h4> The previous lesson you are studying is at <span><%= minute%>:<%= second%></span>. Do you want to continue?</h4>
                <i class="fa-brands fa-youtube" id="icon-youtube"></i>
            </div>
            <div id="notice-continue-footer">
                <h4 id="notice-continue-footer-continue" onclick="continueVideo(${lessonBeingLearned.timeContinue})">Continue</h4>
                <h4 id="notice-continue-footer-again"  onclick="continueVideo(0)">Start Again</h4>
            </div>
        </div>
    </c:if>    

    <c:choose>
        <c:when test="${listSubLesson == null}">
            <section class="page_404">
                <div class="container">
                    <div class="row">	
                        <div class="col-sm-12 ">
                            <div class="col-sm-10 col-sm-offset-1  text-center">
                                <div class="four_zero_four_bg">
                                    <h4 class="text-center ">No found course your are looking for</h4>


                                </div>

                                <div class="contant_box_404">
                                    <h3 class="h2">
                                        Look like you're lost
                                    </h3>

                                    <p>the page you are looking for not avaible!</p>

                                    <a href="" class="link_404">Go to Home</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </c:when>
        <c:otherwise>
            <!-- Lesson Header Start -->
            <div class="lesson-header">
                <div class="lesson-header-left">
                    <a href="my-course"><i class="fa-solid fa-angle-left"></i></a>
                    <h4>${listSubLesson.get(0).listLesson.get(0).courseID.name}</h4>
                </div>
                <div class="lesson-header-right">
                    <div id="note">
                        <i class="fa-solid fa-book"></i>
                        <h4>Note</h4>
                    </div>
                </div>
            </div>

            <!-- Lesson Header End -->

            <!-- Lesson Body Start -->

            <div class="lesson-body">
                <c:if test="${lessonCurrent.startLearningTime.compareTo(timeCurrent) <= 0}">
                    <div class="lesson-body-left">
                        <video width="100%" height="650px" controls autoplay="true">
                            <source src="${lessonCurrent.videoUrl}" type="video/mp4">
                        </video>
                        <div class="desc-lesson">
                            <div>
                                <h2>${lessonCurrent.name}</h2>
                                <% Timestamp timeUpdate = ((Lesson) request.getAttribute("lessonCurrent")).getUpdatedTime();
                                    Date dateUpdate = new Date(timeUpdate.getTime());
                                %>
                                <p>Update <%= dateUpdate%></p>
                            </div>
                            <button type="button" id="create-note-btn">
                                <i class="fa-solid fa-plus"></i> Add New Note
                                <br>
                                <span class="current_time">00:00</span>
                            </button>
                        </div>
                        <div id="form-input-note">
                            <form>
                                <div id="add-note-at">Add note at <span class="current_time">00:00</span></div>
                                <span id="message"></span>
                                <textarea id="editor" rows="5" style="width: 100%; padding: 10px 15px; font-size: 16px"></textarea>
                                <input type="hidden" value="${lessonCurrent.lessonID}" id="lessonIDLearning"/>
                                <input type="hidden" value="<%= request.getParameter("id")%>" id="courseIDLearning"/>
                                <div class="btn-note">
                                    <button type="button" value="Cancel" id="cancel-btn">Cancel</button>
                                    <input type="button" value="Save" id="save-btn">
                                </div>
                            </form>       
                        </div>
                    </div>
                </c:if>
                <c:if test="${lessonCurrent.startLearningTime.compareTo(timeCurrent) > 0}">
                    <div class="lesson-body-left">
                        <h4 id="notice-not-start">The lesson has not yet begin. Please come back later.</h4>
                    </div>
                </c:if>
                <div class="lesson-body-right">
                    <h4>List Lessons</h4>
                    <div class="lesson-body-lesson-list">
                        <c:forEach items="${listSubLesson}" var="subLesson" >
                            <div>
                                <div class="sub-lesson" onclick="drop(this)">
                                    <p>${subLesson.name}</p>
                                    <i class="fa-solid fa-angle-down"></i>
                                </div>
                                <ul class="sub-lesson-list" 
                                    <c:forEach items="${subLesson.listLesson}" var="lesson">
                                        <c:if test="${lesson.lessonID == lessonCurrent.lessonID}">style="display: block"</c:if>
                                    </c:forEach>
                                    >
                                    <c:forEach items="${subLesson.listLesson}" var="lesson">
                                        <div>
                                            <li class="lesson-body-lesson-item
                                                <c:if test="${lessonCurrent.lessonID == lesson.lessonID}"> lesson-current </c:if>                                       
                                                <c:if test="${lesson.startLearningTime.compareTo(timeCurrent) > 0}">not-allow</c:if>
                                                    ">
                                                    <a class="info-lesson" id="lessonID-${lesson.lessonID}" href=
                                                   <c:choose>
                                                       <c:when test="${lesson.lessonTypeID.lessonTypeID == 1}">
                                                           "lesson?id=${courseID}&lid=${lesson.lessonID}"
                                                       </c:when>
                                                       <c:otherwise>
                                                           "quiz?id=${lesson.lessonID}"
                                                       </c:otherwise>
                                                   </c:choose>
                                                   >
                                                   <div class="d-flex-container-lesson">
                                                    <span class="name-lesson">
                                                        ${lesson.name}

                                                        <c:if test="${lesson.lessonTypeID.lessonTypeID == 2}">
                                                            <span class="quiz">Quiz</span>
                                                        </c:if>
                                                    </span>

                                                    <c:forEach items="${listCompleted}" var="lessonComp">
                                                        <c:choose>
                                                            <c:when test="${lessonComp.lessonID.lessonID == lesson.lessonID}">
                                                                <i class="fa-solid fa-circle-check"></i>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:forEach>
                                                    <c:if test="${lesson.startLearningTime.compareTo(timeCurrent) > 0}">
                                                        <i class="fa-solid fa-lock lesson-lock"></i>
                                                    </c:if>
                                        </div>
                                        <c:if test="${lesson.lessonTypeID.lessonTypeID == 1}">
                                            <span class="start-learning-time">${lesson.startLearningTime}</span>
                                        </c:if>
                                        </a>
                                        </li>
                                </div>
                            </c:forEach>
                            </ul>
                        </div>
                    </c:forEach>
                </div> 
            </div>
        </div>

        <div class="container-note">
            <div class="container-note-header">
                <h2 class="note-header-text">My notes</h2>
                <i class="fa-solid fa-xmark" id="close-note"></i>
            </div>
            <div class="container-note-body">
            </div>
        </div>

        <div id="overlay"></div>
        <script src="js/lesson.js"></script>
    </c:otherwise>
</c:choose>

</body>

</html>
