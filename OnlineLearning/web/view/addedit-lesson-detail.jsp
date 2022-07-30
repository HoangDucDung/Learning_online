<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
                crossorigin="anonymous">
            <!--Font Awesome-->
            <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
            <!--Jquery-->
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
            <link href="../css/addedit-lesson-detail.css" rel="stylesheet" type="text/css" />
            <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
            <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
            <script src="../js/addedit-lesson.js" type="text/javascript"></script>
        </head>

                <body>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-2 min-vh-100 bg-dark p-0">
                        <jsp:include page="sidenav.jsp?page=Manage Subject" />
                    </div>
                    <div class="col-sm-10 p-0">
                        <jsp:include page="navbar-header.jsp?page=Lesson-detail" />
                        <c:if test="${lesson==null}">
                            <a href="lesson-list?Cid=${Cid}">Back</a>
                        </c:if>
                        <c:if test="${lesson!=null}">
                            <a href="lesson-detail?Lid=${lesson.getId()}&type=view">Back</a>
                        </c:if>
                        <form action="lesson-detail?Lid=${lesson != null?lesson.getId():""}" method="POST">
                            <div>
                                <table>

                                    <c:if test="${isNoti != null}">
                                        <tr>
                                            <td colspan="2">
                                                <div id="notification">
                                                    <span>${mess}!</span>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:if>
                                    <tr>
                                        <td>Lesson name: *</td>
                                        <td><input type="text" name="lname" value="${lesson.getName()}" required /></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" id="error-fname"></td>
                                    </tr>

                                    <tr>
                                        <td>Start learning time: *</td>
                                        <td><input type="datetime-local" name="starttime"
                                                value="${lesson.getStartLearningTime()}" required /></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" id="error-lname"></td>
                                    </tr>

                                    <tr>
                                        <td>Status:</td>
                                        <td>
                                            <input type="radio" id="Published" name="status"
                                                ${lesson.isStatus()==true?"checked":""} ${lesson==null?"checked":""}
                                                value="1">
                                            <label for="male">Published</label>
                                            <input type="radio" id="Unpublished"
                                                ${lesson.isStatus()==false?"checked":""} name="status" value="0">
                                            <label for="female">Unpublished</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"></td>
                                    </tr>

                                    <tr>
                                        <td>Topic:</td>
                                        <td>
                                            <select name="sublesson" class="sublesson" required>
                                                <c:forEach items="${lsList}" var="lsList">
                                                    <option value="${lsList.getSubLessonID()}"
                                                        ${lesson.getSubLessonID().getSubLessonID()==lsList.getSubLessonID()?"selected":""}>
                                                        ${lsList.getName()}</option>
                                                </c:forEach>
                                            </select>
                                        </td>

                                        <td>
                                            <select name="order" id="order" required>
                                                <c:forEach items="${order}" var="order">
                                                    <option value="${order.getOrder()}"
                                                        ${lesson.getOrder()==order.getOrder()?"selected":""}>
                                                        ${order.getOrder()}</option>
                                                </c:forEach>
                                                <c:if test="${lesson == null}">
                                                    <option class="checkorder" value="${sizeOrder}">${sizeOrder}
                                                    </option>
                                                </c:if>
                                            </select>
                                            <input class="lesson-order" value="${lesson.getOrder()}" type="hidden">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"></td>
                                    </tr>

                                    <tr>
                                        <td>Lesson Type: *</td>
                                        <td>
                                            <select name="lessontype" class="lesson-type" ${lesson==null?"":"disabled"}
                                                required>
                                                <c:forEach items="${ltList}" var="ltList">
                                                    <option value="${ltList.getLessonTypeID()}"
                                                        ${lesson.getLessonTypeID().getLessonTypeID()==ltList.getLessonTypeID()?"selected":""}>
                                                        ${ltList.getName()}</option>
                                                </c:forEach>
                                            </select>
                                            <input name="lessont" value="${lesson.getLessonTypeID().getLessonTypeID()}"
                                                type="hidden">
                                        </td>
                                        <td class="type-contentv ${lesson.getLessonTypeID().getLessonTypeID() == 1 || lesson == null?"lestype":""}"><input type="text" name="video"
                                                value="${lesson.getVideoUrl()}" placeholder="Input VideoURL" required />
                                        </td>


                                        <td class="type-contentq ${lesson.getLessonTypeID().getLessonTypeID() == 2 && lesson != null?"lestype":""}">
                                        <c:if test="${checkQuizExist}">
                                            <a href="quizsettingdetail?id=${lesson.lessonID}" class="bt">Go to quiz</a>
                                        </c:if>        
                                        <c:if test="${!checkQuizExist}">
                                            <a href="quizinsert?id=${lesson.lessonID}" class="bt">Go to quiz</a>
                                        </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"></td>
                                    </tr>

                                    <tr>
                                        <td colspan="2"><input name="type" id="submit-btn" type="submit"
                                                value="${lesson==null?" Add":"Edit"}" /></td>
                                    </tr>
                                </table>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </body>

        </html>