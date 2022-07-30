<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quiz</title>
        <link href="./node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="./node_modules/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <style>
            .quiz-time {
                border-top: 2px black solid;
                border-bottom: 2px black solid;
            }

            .wraper-radio {
                display: inline-block;
                padding: 8px;
                background-color: darkgray;
                border-radius: 5px;
                line-height: 20px;
            }

            .wraper-radio input {
                height: 20px;
                width: 20px;
            }

            .question-tool {
                display: flex;
                justify-content: end;
                margin: 10px 0;
            }

            .question-tool button {
                margin-right: 20px;
            }
            
            .answered {
                background-color: green;
                color: white;
            }
            
            #btn-review,
            #btn-peek {
                padding: 5px;
                border-radius: 5px;
                border: 2px gray solid;
            }

            .btn-green {
                color: white;
                border: 2px white solid;
                margin-left: 10px;
                padding: 5px 10px;
                background-color: green;
                border-radius: 5px;
                width: 150px;
                font-weight: bold;
            }

            .page-content {
                overflow-x: hidden;
            }

            .small-question-box {
                height: 2rem;
                width: 2rem;
                border: 1px solid steelblue;
                border-radius: 5px;
                display: inline-block;
                margin: 3px;
                position: relative;
            }

            .marked-question-box i {
                color: rgb(255, 136, 0);
                font-size: 15px;
                position: absolute;
                top: 0;
                right: 0;

            }

            .btn-review {
                border: 2px gray solid;
                border-radius: 5px;
                padding: 4px 8px;
                display: inline-block;
            }

            .vh-70 {
                height: 65vh;
            }

            #question_content {
                height: 60vh;
                overflow-y: auto;
            }

            .btn-white,
            .btn-grey {
                border: 2px grey solid;
            }

            .btn-white i {
                margin-right: 10px;
            }
        </style>
    </head>

    <body>
        <div class="container-fluid min-vh-100 p-0 page-content">
            <div class="row justify-content-end quiz-time m-1 p-1">
                <p id="number_question" class=" col-1 fs-4 m-0"></p>
                <p id="countDown" class="text-center col-1 bg-info fs-5 m-0 p-1"></p>
            </div>

            <div class="">
                <div class="row bg-dark text-light m-1 p-1">
                    <div class="col-4">
                        <!--Question Counter-->
                        <h4 id="current_question_no" class="m-0"></h4>
                    </div>
                    <div class="col-4 offset-4">
                        <h4 id="current_question_id" class="text-end m-0"></h4>
                    </div>
                </div>
            </div>

            <div class="row justify-content-center">
                <div id="question_content" class="col-10">
                    <p id="question_text" class="mt-2">a
                    </p>
                    <!--Answers go in here-->
                </div>
            </div>

            <div class="position-fixed bottom-0 w-100">
                <div class="">
                    <div class="row p-3">
                        <div class="col d-flex justify-content-end">
                            <!-- <button class="btn btn-green" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                Peek at Answer</button> -->
                            <button id="btn_mark" class="btn btn-green">Mark for Review</button>
                        </div>
                    </div>
                    <div class="row bg-success p-3">
                        <div class="col">
                            <button id="btn_review_question" class="btn btn-green" data-bs-toggle="modal"
                                    data-bs-target="#review-process-modal">
                                Review Progress
                            </button>
                        </div>
                        <div class="col d-flex justify-content-end">
                            <button id="btn_previous" class="btn btn-green">Previous</button>
                            <button id="btn_next" class="btn btn-green">Next</button>
                            <button id="btn_score" class="btn btn-green btn-score-exam" data-bs-toggle="modal" data-bs-target="#scoreExamModal">
                                Score
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Confirm score exam box -->
            <div class="modal fade" id="scoreExamModal" tabindex="-1" aria-labelledby="scoreExamModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="scoreExamModalLabel"></h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p id="notAnsweredQuestion" class="text-danger font-weight-bold"></p>
                            <p id="helpMessage"></p>
                        </div>
                        <div class="modal-footer">
                            <a id="reviewLink" href="./quizz-result?lID=${param['id']}" class="btn btn-primary" hidden>Review</a>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Back</button>
                            <button id="btn_submit_answer" type="button" class="btn btn-success">Score Exam</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="review-process-modal" tabindex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-xl">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="">Review Process</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="row review-question-btn-filter">
                                <div class="col-9">
                                    <button id="btn_unanswered" class="btn btn-white">
                                        <i class="fa-regular fa-square"></i>UNANSWERED</button>
                                    <button id="btn_marked" class="btn btn-white"><i
                                            class="fa-solid fa-bookmark text-warning"></i>MARKED</button>
                                    <button id="btn_answered" class="btn btn-white">
                                        <i class="fa-solid fa-square text-success"></i>ANSWERED
                                    </button>
                                </div>
                                <div class="col d-flex justify-content-end">

                                    <button id="score_exam" class="btn btn-white btn-score-exam" data-bs-toggle="modal"
                                            data-bs-target="#scoreExamModal">
                                        SCORE EXAM NOW</button>
                                </div>
                                <div id="question_review">
                                    <!--Question boxes go here.-->
                                    <!-- <button class="small-question-box">1</button>
                                    <button class="small-question-box marked-question-box">
                                        <i class="fa-solid fa-bookmark"></i>2
                                    </button> -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <script src="./js/question.js"></script>
    </body>

</html>