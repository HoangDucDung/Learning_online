let availableQuestions = [];
let questionCounter = 0;
let userAnswers = [];
let markedQuestions = [];
let remainSecond = 0;
let endTime = 0;

const btnNextQuestion = document.getElementById("btn_next");
const btnPreviousQuestion = document.getElementById("btn_previous");
const btnScoreExam = document.getElementById("btn_score");
const btnReviewProgress = document.getElementById("btn_review_question");
const btnMark = document.getElementById("btn_mark");
const countDown = document.getElementById("countDown");
let counter;

function removeItemInArray(array, item) {
    let index = array.indexOf(item);
    if (index > -1) {
        array.splice(index, 1);
    }
}

btnMark.addEventListener('click', () => {
    markedQuestions = JSON.parse(localStorage.getItem("markedQuestions")) || [];
    if (isMarkedQuestion(questionCounter)) {
        btnMark.innerHTML = "Mark For Review";
        removeItemInArray(markedQuestions, questionCounter);
    } else {
        btnMark.innerHTML = "Unmark";
        markedQuestions.push(questionCounter);
    }
    localStorage.setItem("markedQuestions", JSON.stringify(markedQuestions));
});

function isMarkedQuestion(number) {
    return markedQuestions.includes(number);
}

function checkAnsweredQuestion(questionId) {
    let answer = userAnswers.find(s => s.questionId == questionId);
    if (!answer) {
        return false;
    }
    return answer.answerIds.length != 0;
}

class Answer {
    constructor(questionId) {
        this.questionId = questionId;
        this.answerIds = new Array();
    }
}

function getQuizId() {
    let url = new URL(window.location.href);
    let quizId = url.searchParams.get("id");

    if (!quizId) {
        throw new Error("Lack param id in query string");
    }
    return quizId;
}

function initQuestion() {
    let url = new URL(window.location.href);
    let lessonId = url.searchParams.get("id");

    if (!lessonId) {
        throw new Error("Lack param id in query string");
    }

    fetch(`./api/question?lesson=${lessonId}`)
            .then(resp => resp.json())
            .then(data => {
                console.log("Loaded Question");
                availableQuestions = data;
                questionCounter = 0;
                userAnswers = JSON.parse(localStorage.getItem("userAnswers")) || [];
                markedQuestions = JSON.parse(localStorage.getItem("markedQuestions")) || [];
                loadQuestion(questionCounter, availableQuestions);
            });

    fetch(`http://localhost:8080/OnlineLearning/api/question/session/${lessonId}`)
            .then(resp => resp.json())
            .then(data => {
                endTime = new Date(data.expiredTime);
                counter = setInterval(() => {
                    let distance = endTime - new Date();

                    var hours = Math.floor(distance / (1000 * 60 * 60));
                    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                    var seconds = Math.floor((distance % (1000 * 60)) / 1000);

                    countDown.innerHTML = `${hours}:${minutes}:${seconds}`;
                    if (distance < 1000) {
                        clearInterval(counter);
                        submitAnswer();
                    }
                }, 1000);
            });
}


initQuestion();

function clearQuestionOption() {
    let options = document.querySelectorAll(".question-answer");
    options.forEach(o => o.remove());
}

function getSelectedAnswerIds() {
    let ids = [];
    let answers = document.getElementsByClassName("answer");
    for (let a of Array.from(answers)) {
        if (a.checked) {
            ids.push(a.value);
        }
    }
    return ids;
}

function loadSelectedAnswer(questionId) {
    let answer = userAnswers.find(q => q.questionId == questionId);

    if (answer && answer.answerIds) {
        for (let i of answer.answerIds) {
            try {
                document.getElementById(`answer-${i}`).checked = "true";
            } catch (error) {
                console.log(error);
            }
        }
    }
}

function loadQuestion(questionNo, questions) {
    let currentQuestion = questions[questionNo];

    btnMark.innerHTML = isMarkedQuestion(questionNo) ? "Unmark" : "Mark For Review";

    if (questionNo == 0) {
        btnPreviousQuestion.style.display = "none";
        btnNextQuestion.style.display = "inline";
        btnScoreExam.style.display = "none";
    } else if (questionNo == questions.length - 1) {
        btnPreviousQuestion.style.display = "inline";
        btnNextQuestion.style.display = "none";
        btnScoreExam.style.display = "inline";
    } else {
        btnPreviousQuestion.style.display = "inline";
        btnNextQuestion.style.display = "inline";
        btnScoreExam.style.display = "none";
    }

    document.getElementById("question_text").innerHTML = currentQuestion.text;
    clearQuestionOption();

    document.getElementById("number_question").innerText = `${questionNo + 1}/${questions.length}`;
    document.getElementById("current_question_no").innerText = `${questionNo + 1})`;
    document.getElementById("current_question_id").innerText = `QuestionID: ${currentQuestion.id}`;

    let questionContent = document.getElementById("question_content");
    for (let answer of currentQuestion.answers) {
        let optionNode = `
            <div class="question-answer pb-2">
                <span class="wraper-radio">
                    <input id="answer-${answer.id}" class="answer" value="${answer.id}" type="checkbox" name="question-1">
                </span>
                <label for="answer-${answer.id}">${answer.text}</label>
            </div>`;
        questionContent.innerHTML += optionNode;
    }

    loadSelectedAnswer(currentQuestion.id);
}

document.getElementById("question_content").addEventListener("click", event => {
    let answer = event.target;
    if (answer.nodeName == "INPUT") {
        let questionId = availableQuestions[questionCounter].id;
        let savedAnswer = userAnswers.find(s => s.questionId == questionId);
        let selectedIds = getSelectedAnswerIds();
        console.log(selectedIds);
        if (savedAnswer) {
            console.log("Update");
            savedAnswer.answerIds = selectedIds;
        } else {
            console.log("Create");
            let temp = new Answer(questionId);
            temp.answerIds = selectedIds;
            userAnswers.push(temp);
        }
        localStorage.setItem("userAnswers", JSON.stringify(userAnswers));
    }
});

btnNextQuestion.addEventListener('click', () => {
    let next = questionCounter + 1;
    if (next < availableQuestions.length) {
        questionCounter = next;
        loadQuestion(next, availableQuestions);
    }
});

btnPreviousQuestion.addEventListener('click', () => {
    let prev = questionCounter - 1;
    if (prev >= 0) {
        questionCounter = prev;
        loadQuestion(prev, availableQuestions);
    }
});

btnReviewProgress.addEventListener('click', function () {
    loadQuestionBox(availableQuestions);
});

function loadQuestionBox(questions) {
    let questionReview = document.getElementById("question_review");
    questionReview.innerHTML = "";

    questions.forEach((_, index) => {
        let btn = document.createElement("button");

        if (isMarkedQuestion(index)) {
            btn.className = "small-question-box marked-question-box ";
            btn.innerHTML = `<i class="fa-solid fa-bookmark" ></i >${index + 1}`;
        } else {
            btn.innerHTML = index + 1;
            btn.className = "small-question-box";
        }

        if (checkAnsweredQuestion(availableQuestions[index].id)) {
            btn.className += " answered";
        }

        btn.addEventListener('click', () => {
            $("#review-process-modal").modal("hide");
            questionCounter = index;
            loadQuestion(index, questions);
        });
        questionReview.appendChild(btn);
    });
}

function submitAnswer() {
    let data = {
        quizId: getQuizId(),
        answers: userAnswers
    }

    if (data) {
        $.ajax({
            url: `http://localhost:8080/OnlineLearning/api/question/submit`,
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (data, textStatus, jqXHR) {
                console.log(data.message);
                localStorage.removeItem("markedQuetions");
                localStorage.removeItem("userAnswers");
                document.querySelectorAll(".btn-score-exam").forEach(e => e.disabled = true);
                clearInterval(counter);
                countDown.innerHTML = "Submited";
                document.getElementById("reviewLink").hidden = false;
                $("#notAnsweredQuestion").text("");
                $("#helpMessage").text(jqXHR.responseJSON.message);
            },
            error: function (jqXHR, textStatus) {
                $("#notAnsweredQuestion").text("");
                $("#helpMessage").text('Submit fail: ' + jqXHR.responseJSON.message);
                console.log(jqXHR.responseJSON.message);
            }
        });
    }
}

document.getElementById("btn_submit_answer").addEventListener("click", () => {
    submitAnswer()
});

document.querySelectorAll(".btn-score-exam").forEach(btn => {
    btn.addEventListener('click', () => {
        let total = 0;
        for (let question of availableQuestions) {
            total += checkAnsweredQuestion(question.id) ? 1 : 0;
        }

        let msg1 = "You have not answered any questions. By clicking on the [Exit Exam] button below, you will complete your current exam and be returned to the dashboard";
        let msg2 = "By clicking on the [Score Exam] button below, you will complete your current exam and receive your score. You will not be able to change any answers after this point";

        let numberOfQuestion = availableQuestions.length;
        let header = (total == 0) ? "Exit Exam?" : "Score Exam";
        let str1 = (total > 0 && total < numberOfQuestion) ? `${total} of ${numberOfQuestion} Questions Answered` : ""
        let str2 = (total == 0) ? msg1 : msg2;
        $("#scoreExamModalLabel").text(header);
        $("#notAnsweredQuestion").text(str1);
        $("#helpMessage").text(str2);
    });
});
