<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <jsp:include page="base-view/baseTagAdmin.jsp"></jsp:include>
            <link rel="stylesheet" href="css/quiz-result.css">
            <meta charset="utf-8">
            <title>Quizz Lesson</title>
            <meta content="width=device-width, initial-scale=1.0" name="viewport">
            <meta content="" name="keywords">
            <meta content="" name="description">

            <!-- Favicon -->
            <link href="img/favicon.ico" rel="icon">

            <!-- Google Web Fonts -->
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link
                href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap"
                rel="stylesheet">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
                  integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
                  crossorigin="anonymous" referrerpolicy="no-referrer" />
            <link href="css/quizz-lesson.css" rel="stylesheet">
            <link rel="stylesheet" href="./css/base.css">
            <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />
        </head>

        <body>
            <div class="container-fluid min-vh-100 p-0 page-content">
                <div class="row fs-4 justify-content-end quiz-time m-1 pb-1 pt-1 text-center">
                    <div id="number_question" class="p-1 col-1"></div>
                    <div class="p-1 col-1 bg-info">Exam | ${count} Questions</div>
            </div>

            <div class="relative overflow-x-auto shadow-md sm:rounded-lg table">
                <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">

                    <tbody>
                        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap">
                                Score
                            </th>
                            <td class="px-6 py-4">
                                ${total}%
                            </td>

                        </tr>
                        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap">
                                Correct
                            </th>
                            <td class="px-6 py-4">
                                ${size} of ${count}
                            </td>

                        </tr>
                        <tr class="bg-white dark:bg-gray-800 hover:bg-gray-50 dark:hover:bg-gray-600">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap">
                                Exam Taken
                            </th>
                            <td class="px-6 py-4">
                                ${datenow} ${timenow}
                            </td>

                        </tr>
                        <tr class="bg-white dark:bg-gray-800 hover:bg-gray-50 dark:hover:bg-gray-600">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap">
                                Total Time
                            </th>
                            <td class="px-6 py-4">
                                ${timeDoing}
                            </td>

                        </tr>
                        <tr class="bg-white dark:bg-gray-800 hover:bg-gray-50 dark:hover:bg-gray-600">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap">
                                Average Time Per Question
                            </th>
                            <td class="px-6 py-4">
                                ${timeDo1Ques} sec
                            </td>

                        </tr>

                        <tr class="bg-white dark:bg-gray-800 hover:bg-gray-50 dark:hover:bg-gray-600">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap">

                                    <a href="reviewquiz?lID=${lessonID}"><button type="button" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800 button">Review Test</button></a>

                            </th>
                            <td class="px-6 py-4">
                                <c:if test="${size >= passScore}">
                                    <h6 style="color:green;">PASSED THE EXAM</h6>
                                </c:if> 
                                <c:if test="${size < passScore}">
                                    <h6 style="color:red;">FAILED THE EXAM</h6> 
                                </c:if> 
                                <br/>
                                
                                <a href="takequiz?id=${lessonID}"><button type="button" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800 button">Redo Test</button></a>
                            </td>

                        </tr>
                    </tbody>
                </table>
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

        <script src="question.js"></script>
    </body>

</html>