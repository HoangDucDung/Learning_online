$(document).ready(function () {
    $('#table').DataTable();
});

$(document).ready(function () {
    $(".sublt-a").click(function () {
        const arr = $(this).is(':checked').valueOf();
        for (var i = 0; i < document.getElementsByClassName('sublesson-type')[0].children.length; i++) {
            if (arr === true) {
//            $('.sublt').attr('checked', true);
                document.getElementsByClassName('sublesson-type')[0].children[i].children[0].checked = true;
            } else {
//            $('.sublt').attr('checked', false);
                document.getElementsByClassName('sublesson-type')[0].children[i].children[0].checked = false;
            }
        }
    });
});

$(document).ready(function () {
    $(".subls-a").click(function () {
        const arr = $(this).is(':checked').valueOf();
        for (var i = 0; i < document.getElementsByClassName('sublesson-sub')[0].children.length; i++) {
            if (arr === true) {
                document.getElementsByClassName('sublesson-sub')[0].children[i].children[0].checked = true;
            } else {
                document.getElementsByClassName('sublesson-sub')[0].children[i].children[0].checked = false;
            }
        }
    });
});


$(document).ready(function () {
    $(".sublt").click(function () {
        const arr = $(".sublt").tooltip();

        const vlt = [];
        var count = 0;
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].checked === true) {
                vlt[i] = arr[i].value;
                count++;
            }
        }
        if (count === arr.length) {
//            $('.sublt-a').attr('checked', true);
            document.getElementsByClassName("sublt-a")[0].checked = true;
        } else {
//            $('.sublt-a').attr('checked', false);
            document.getElementsByClassName("sublt-a")[0].checked = false;
        }
    });
});

$(document).ready(function () {
    $(".subls").click(function () {
        const arr = $(".subls").tooltip();
        const vlt = [];
        var count = 0;
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].checked === true) {
                vlt[i] = arr[i].value;
                count++;
            }
        }
        if (count === arr.length) {
//            $('.sublt-a').attr('checked', true);
            document.getElementsByClassName("subls-a")[0].checked = true;
        } else {
//            $('.sublt-a').attr('checked', false);
            document.getElementsByClassName("subls-a")[0].checked = false;
        }
    });
});

$(document).ready(function () {
    $(".clickchosse").click(function () {
        const arrls = $(".subls").tooltip();
        const arrlt = $(".sublt").tooltip();

        const stt = $('#status').val();

        const checklt = $(".sublt-a").is(':checked').valueOf();
        const checkls = $(".subls-a").is(':checked').valueOf();

        const vls = [];
        var countls = 0;
        const vlt = [];
        var countlt = 0;

        for (var i = 0; i < arrls.length; i++) {
            if (arrls[i].checked === true) {
                vls[i] = arrls[i].value;
                countls++;
            }
        }

        for (var i = 0; i < arrlt.length; i++) {
            if (arrlt[i].checked === true) {
                vlt[i] = arrlt[i].value;
                countlt++;
            }
        }

        let params = (new URL(document.location)).searchParams;
        let Cid = params.get("Cid");

        $.ajax({
            type: 'POST',
            url: "lesson-list",
            data: {
                arraylsID: vls.toString(),
                arrayltID: vlt.toString(),
                checkls: checkls,
                checklt: checklt,
                Cid: Cid,
                status: stt
            },
            success: function (data) {
                $('#table').DataTable().destroy();
                var lesson = document.getElementById("content-course");
                lesson.innerHTML = data;
                $('#table').DataTable();
            }
        });
    });
});

$(document).ready(function () {
    $("#status").on('change', function () {
        var value = $('#status').val();
        const arrls = $(".subls").tooltip();
        const arrlt = $(".sublt").tooltip();

        const stt = $('#status').val();

        const checklt = $(".sublt-a").is(':checked').valueOf();
        const checkls = $(".subls-a").is(':checked').valueOf();

        const vls = [];
        var countls = 0;
        const vlt = [];
        var countlt = 0;

        for (var i = 0; i < arrls.length; i++) {
            if (arrls[i].checked === true) {
                vls[i] = arrls[i].value;
                countls++;
            }
        }

        for (var i = 0; i < arrlt.length; i++) {
            if (arrlt[i].checked === true) {
                vlt[i] = arrlt[i].value;
                countlt++;
            }
        }

        let params = (new URL(document.location)).searchParams;
        let Cid = params.get("Cid");
        $.ajax({
            type: 'POST',
            url: "lesson-list",
            data: {
                arraylsID: vls.toString(),
                arrayltID: vlt.toString(),
                checkls: checkls,
                checklt: checklt,
                Cid: Cid,
                status: stt
            },
            success: function (data) {
                $('#table').DataTable().destroy();
                var lesson = document.getElementById("content-course");
                lesson.innerHTML = data;
                $('#table').DataTable();
            }
        });
    });
});

$(document).ready(function () {
    $('#search-category').keyup( function (){
        var search = $(this).val().toString().toUpperCase();
        var txtValue, txtsub;
        const lt = document.getElementsByClassName('sub-t');
        const ls = document.getElementsByClassName('sub-s');
        for (var i = 0; i < lt.length; i++) {
            txtValue = lt[i].childNodes[1].textContent || lt[i].childNodes[1].innerText;
            if(txtValue.toUpperCase().indexOf(search) > -1){
                lt[i].style.display = "";
            } else {
                lt[i].style.display = "none";
            }
        }
        for (var i = 0; i < ls.length; i++) {
            txtValue = ls[i].childNodes[1].textContent || ls[i].childNodes[1].innerText;
            if(txtValue.toUpperCase().indexOf(search) > -1){
                ls[i].style.display = "";
            } else {
                ls[i].style.display = "none";
            }
        }
    });
});


function deleteLesson(lessonid, btn){
    if (confirm("Do you want to delete this course?")) {
        $.ajax({
                type: 'delete',
                url: "../management/lesson-list?Lid=" + lessonid,
                success: function (data) {
                    $(btn).closest('tr').fadeOut();
                }
        });
    }
}