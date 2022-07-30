function drop(event) {
    if (event.parentNode.childNodes[3].style.display === "block") {
        event.parentNode.childNodes[3].style.display = "none";

    } else {
        event.parentNode.childNodes[3].style.display = "block";
    }

    event.parentNode.childNodes[1].childNodes[3].classList.toggle("fa-angle-down");
    event.parentNode.childNodes[1].childNodes[3].classList.toggle("fa-angle-up");
}

function checkDisplay() {
    var subLessonList = document.getElementsByClassName("sub-lesson-list");
    for (var i = 0; i < subLessonList.length; i++) {
        if (subLessonList[i].parentNode.childNodes[3].style.display === "block") {
            subLessonList[i].parentNode.childNodes[1].childNodes[3].classList.remove("fa-angle-down");
            subLessonList[i].parentNode.childNodes[1].childNodes[3].classList.add("fa-angle-up");
        } else {
            subLessonList[i].parentNode.childNodes[1].childNodes[3].classList.add("fa-angle-down");
            subLessonList[i].parentNode.childNodes[1].childNodes[3].classList.remove("fa-angle-up");
        }
    }
}
function checkNotAllowed() {
    var notAllowed = document.getElementsByClassName("not-allow");
    for (var i = 0; i < notAllowed.length; i++) {
        notAllowed[i].parentNode.classList.add('cursor-block');
    }
}
checkNotAllowed();
checkDisplay();

var note = document.getElementById("note");
var closeNote = document.getElementById("close-note");
var overlay = document.getElementById("overlay");
var noteContainer = document.getElementsByClassName("container-note")[0];
note.onclick = function () {
    noteContainer.style.transform = "translate(0)";
    noteContainer.style.opacity = 1;
    overlay.style.display = "block";
    overlay.style.opacity = 1;
};
closeNote.onclick = function () {
    noteContainer.style.transform = "translate(100%)";
    noteContainer.style.opacity = 0;
    overlay.style.opacity = 0;
    overlay.style.display = "none";
};
overlay.onclick = function () {
    noteContainer.style.transform = "translate(100%)";
    noteContainer.style.opacity = 0;
    overlay.style.opacity = 0;
    overlay.style.display = "none";
};

var createNote = document.getElementById("create-note-btn");
var cancelNote = document.getElementById("cancel-btn");
var formNote = document.getElementById("form-input-note");

createNote.onclick = function () {
    formNote.style.transform = "translate(0)";
    formNote.style.opacity = 1;
};
cancelNote.onclick = function () {
    formNote.style.transform = "translate(-100%)";
    formNote.style.opacity = 0;
};

$(function () {
    $('#save-btn').click(function () {
        $.ajax({
            type: "POST",
            url: "note",
            cache: false,
            data: {
                lessonID: $('#lessonIDLearning').val(),
                description: $('#editor').val(),
                timeVideoNote: $('.current_time')[0].innerHTML
            },
            success: function (result) {
                if (result.check === "true") {
                    $('#message').text("Create new note successfully!");
                    $('#message').css('color', 'blue');
                    $('#editor').val('');
                } else {
                    $('#message').text(result.check);
                    $('#message').css('color', 'red');
                    $('#editor').val('');
                }
            }
        });
    });
});



$(function () {
    $('#note').click(function () {
        $('.container-note-body')[0].innerHTML = '';
        $.ajax({
            type: "get",
            url: "note",
            cache: false,
            data: {
                lessonID: $('#lessonIDLearning').val()
            },
            success: function (result) {
                $('.container-note-body')[0].innerHTML += result;
            }
        });
    });
});

var saveBtn = document.getElementById("save-btn");

saveBtn.onclick = function () {
    setTimeout(function () {
        document.getElementById("message").innerText = '';
    }, 3000);
};

function editNote(element) {
    var textDesc = element.parentNode.parentNode.parentNode.childNodes[3];
    var textDescValue = textDesc.innerText;
    var buttonCancel = element.parentNode.parentNode.parentNode.childNodes[5].childNodes[3];
    var buttonSave = element.parentNode.parentNode.parentNode.childNodes[5].childNodes[5];
    textDesc.innerHTML = "<textarea id='textarea-edit-note' class='form-edit-note' row='5' >" + textDescValue + "</textarea>";
    buttonCancel.style.display = "inline-block";
    buttonSave.style.display = "inline-block";
}
;

function cancelEditNote(element) {
    var buttonSave = element.parentNode.childNodes[5];
    var textDesc = element.parentNode.parentNode.childNodes[3];
    var textDescValue = textDesc.childNodes[0].innerHTML;

    element.style.display = "none";
    buttonSave.style.display = "none";
    textDesc.innerHTML = textDescValue;
}
function saveEditNote(element) {
    var textEdit = element.parentNode.parentNode.childNodes[3].childNodes[0].value;
    var noteID = element.parentNode.childNodes[1].value;
    $(function () {
        $.ajax({
            type: "post",
            url: "note/edit",
            cache: false,
            data: {
                lessonID: $('#lessonIDLearning').val(),
                noteID: noteID,
                descEdit: textEdit

            },
            success: function (result) {
                $('.container-note-body')[0].innerHTML = '';
                $('.container-note-body')[0].innerHTML += result;
            }
        });
    });
}
;

function deleteDeleteNote(element) {
    var idNoteDelete = element.parentNode.childNodes[1].value;
    var elementParentDelete = element.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
    $(function () {
        $.ajax({
            type: "get",
            url: "note/edit",
            cache: false,
            data: {
                noteID: idNoteDelete
            },
            success: function () {
                elementParentDelete.remove();
            }
        });
    });
}

function showNoticeDelete(element) {
    var noticeDelete = element.parentNode.childNodes[3];
    noticeDelete.style.display = "block";
}

function cancelDeleteNote(element) {
    var noticeDelete = element.parentNode.parentNode;
    noticeDelete.style.display = "none";
}

var video = document.querySelector('video');
var timeStart = 0;
var timeEnd = 0;
var duration = 0;

var getTimeVideoInterval = setInterval(function () {
    var whereYouAt = video.currentTime;
    var minutes = Math.floor(whereYouAt / 60);
    var seconds = Math.floor(whereYouAt - minutes * 60);
    var x = minutes < 10 ? "0" + minutes : minutes;
    var y = seconds < 10 ? "0" + seconds : seconds;
    var currentTime = document.querySelectorAll('.current_time');
    currentTime.forEach(function (time) {
        time.innerHTML = x + ":" + y;
    });
}, 400);

video.onplay = function () {
    duration = video.duration;
};

var dateCurrent;

var request = new XMLHttpRequest();
request.open('GET', 'http://worldtimeapi.org/api/timezone/asia/Ho_Chi_Minh', true);
request.onload = function () {
    // Begin accessing JSON data here
    var data = JSON.parse(this.response);

    if (request.status >= 200 && request.status < 400) {
        dateCurrent = data.datetime;
        
        dateCurrent = new Date(dateCurrent).getTime();
    } else {
        console.log("error");
    }
};

request.send();

video.addEventListener("timeupdate", checkTime);
function checkTime() {
    var lessonID = $('#lessonIDLearning').val();
    var idFind = 'lessonID-' + lessonID;
    var lesson = document.getElementById(idFind).childNodes[1];
    if (typeof lesson.childNodes[3] === "undefined") {
        timeEnd = video.currentTime;
        if (timeEnd - timeStart >= 80 * duration / 100) {
            $(function () {
                $.ajax({
                    type: "post",
                    url: "lesson",
                    cache: false,
                    data: {
                        lessonID: $('#lessonIDLearning').val(),
                        courseID: $('#courseIDLearning').val()
                    },
                    success: function (result) {


                        var idFind = 'lessonID-' + result.lessonID;
                        var lesson = document.getElementById(idFind).childNodes[1];

                        var dateLesson = new Date(document.getElementById(idFind).childNodes[3].innerHTML);
                        if (dateLesson.getTime() < dateCurrent) {
                            var checked = document.createElement("i");
                            checked.className = "fa-solid fa-circle-check";
                            lesson.appendChild(checked);
                        }
                        if (parseInt(result.lessonIDNext) !== -1) {
                            var idFindNext = 'lessonID-' + result.lessonIDNext;
                            var lessonNext = document.getElementById(idFindNext).childNodes[1];
                            var dateLessonNext = new Date(document.getElementById(idFindNext).childNodes[3].innerHTML);

                            if (dateLessonNext.getTime() < dateCurrent) {
                                lessonNext.childNodes[3].remove();
                                lessonNext.parentNode.parentNode.classList.remove('not-allow');
                                lessonNext.parentNode.parentNode.parentNode.classList.remove('cursor-block');
                            }

                        }
                    }
                });
            });
            video.removeEventListener("timeupdate", checkTime);
        }
    } else {
        video.removeEventListener("timeupdate", checkTime);
    }
}
;

function showVideo(element) {
    const myArray = element.innerHTML.split(":");
    var minute = parseInt(myArray[0]);
    var second = parseInt(myArray[1]);
    var timeOfVideo = minute * 60 + second;
    video.currentTime = timeOfVideo;
    overlay.click();
}

window.onbeforeunload = function () {
    var lessonLearning = document.getElementById("lessonID-" + $('#lessonIDLearning').val()).childNodes[1];
    if (typeof lessonLearning.childNodes[3] === "undefined") {
        $(function () {
            $.ajax({
                type: "post",
                url: "lesson/continue",
                cache: false,
                data: {
                    lessonID: $('#lessonIDLearning').val(),
                    timeContinue: parseInt(video.currentTime)
                }
            });
        });
    } else {
    }
};

function closeNoticeContinue() {
    var overlayContinue = document.getElementById("overlay-continue");
    var noticeContinue = document.getElementById("notice-continue");
    overlayContinue.style.display = "none";
    noticeContinue.style.display = "none";
}

function continueVideo(time) {
    video.currentTime = time;
    closeNoticeContinue();
}
