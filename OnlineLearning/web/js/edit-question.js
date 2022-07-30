const answerModal = $("#answerModal");
let countNewAnswer = 0;

answerModal.on("hide.bs.modal", function () {
    countNewAnswer = 0; // Reset counter
    $("#countNewAnswer").text("");
    location.reload();
});

function customAlert(title, content, type) {
    $.alert({
        title: title,
        content: content,
        type: type,
        typeAnimated: true,
        buttons: {
            close: function () {
            }
        }
    });
}

/**
 * Fill Answer'form
 * @returns {undefined|bindAnswer.answer}
 */
function fillAnswerToModal(id, text, explain, status) {
    $("#answerId").val(id);
    $("#answerText").val(text);
    $("#answerExplain").val(explain);
    $("#answerStatus").prop("checked", Number(status));
}

/**
 * Create Answer object from form data
 * @returns {bindAnswer.answer}
 */
function bindAnswer() {
    let answer = {
        id: $("#answerId").val(),
        text: $("#answerText").val(),
        explain: $("#answerExplain").val(),
        status: $("#answerStatus").prop("checked") ? 1 : 0,
        questionId: $("#questionId").val()
    }
    return answer;
}

$(".btn-edit-answer").on("click", function () {
    let answerData = $(this);
    $("#submitAnswerBtn").text("Edit");
    fillAnswerToModal(answerData.attr("data-answer-id"),
            answerData.attr("data-answer-text"),
            answerData.attr("data-answer-explain"),
            answerData.attr("data-answer-status"));
    answerModal.modal("show");
});

$("#createAnswerBtn").on("click", function () {
    $("#submitAnswerBtn").text("Create");
    fillAnswerToModal(0, "", "This answer should not be selected!", 0);
    answerModal.modal("show");
});

$("#submitAnswerBtn").on("click", function () {
    let action = $("#submitAnswerBtn").text();
    let answer = bindAnswer();

    if (!answer.text.trim()) {
        $.alert({
            title: 'Create answer fail!',
            type: 'red',
            content: 'Answer content is not empty.',
        });
        return;
    }

    if (action === "Create") {
        $.post("./answer", answer).done(function (data) {
            console.log(data);
            countNewAnswer++;
            fillAnswerToModal(0, "", "This answer should not be selected!", 0);
            $("#countNewAnswer").html(`<i class="fa-solid fa-circle-check text-success"></i><strong> ${countNewAnswer} answer created</strong>`);
        })
    } else if (action === "Edit") {
        $.ajax({
            type: "PUT",
            url: "./answer",
            data: JSON.stringify(answer),
            success: function (data, textStatus, jqXHR) {
                location.reload();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $.alert({
                    title: 'Error!',
                    content: 'Update answer fail!',
                });
            },
            dataType: "text"
        });
    }
});

function showToast(msg) {
    $("#liveToast").toast("show");
    $("#toastMsg").text(msg);
}

$(".btn-delete-answer").on("click", function () {
    let id = $(this).attr("data-answer-id");
    let clickedBtn = $(this);

    $.confirm({
        title: "Confirm!",
        content: "Do you want to delete this answer ?",
        buttons: {
            delete: {
                btnClass: 'btn-red',
                action: function () {
                    $.ajax({
                        type: "DELETE",
                        url: `./answer?id=${id}`,
                        success: function () {
                            clickedBtn.closest("tr").hide(200);
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            $.alert({
                                title: 'Error!',
                                content: 'Delete answer fail!',
                            });
                        }
                    });
                }
            },
            cancel: function () {
            }
        }
    });
});

$("#updateQuestionBtn").on('click', function () {
    let question = {
        id: $("#questionId").val(),
        text: $("#questionText").val(),
        lessonId: $("#lessonId").val(),
        questionLevelId: $("#questionLevelId").val()
    };

    if (!question.text.trim()) {
        $.alert({
            title: 'Error!',
            type: 'red',
            content: 'Update question fail! Question content is required.',
        });
        return;
    }
    $.ajax({
        type: "PUT",
        url: `./question`,
        data: JSON.stringify(question),
        success: function (data, textStatus, jqXHR) {
            showToast("Update question successfully.");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $.alert({
                title: 'Error!',
                content: 'Update question fail!',
            });
        }
    });
});