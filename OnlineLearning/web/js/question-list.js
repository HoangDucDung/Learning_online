$(document).ready(function () {
    $('#table').DataTable();
});

$(document).ready(function () {
    $(".subdt-a").click(function () {
        const value = $(this).val();
        const arr = $(this).is(':checked').valueOf();
        if(arr == true){
            for (var i = 0; i < document.getElementsByClassName("subd-a "+value).length; i++) {
                document.getElementsByClassName("subd-a "+value)[i].checked = true;
            }
        } else {
            for (var i = 0; i < document.getElementsByClassName("subd-a "+value).length; i++) {
                document.getElementsByClassName("subd-a "+value)[i].checked = false;
            }
        }
    });
});

$(document).ready(function () {
    $(".subc-a").click(function () {
        const value = $(this).val();
        const arr = $(this).is(':checked').valueOf();
        if(arr == true){
            for (var i = 0; i < document.getElementsByClassName("subl-a "+value).length; i++) {
                document.getElementsByClassName("subl-a "+value)[i].checked = true;
            }
        } else {
            for (var i = 0; i < document.getElementsByClassName("subl-a "+value).length; i++) {
                document.getElementsByClassName("subl-a "+value)[i].checked = false;
            }
        }
    });
});

$(document).ready(function () {
    $(".subd-a").click(function () {
        const value = $(this).attr('id');
        var priceRange = document.getElementsByClassName("subd-a "+value);
        var count = 0;
        
        for (var i = 0; i < priceRange.length; i++) {
            if (document.getElementsByClassName("subd-a "+value)[i].checked == true) {
                count++;
            }
        }
        if (count == priceRange.length) {
            document.getElementsByClassName("subdt-a "+value)[0].checked = true;
        } else {
            document.getElementsByClassName("subdt-a "+value)[0].checked = false;
        }
    });
});

$(document).ready(function () {
    $(".subl-a").click(function () {
        const value = $(this).attr('id');
        var priceRange = document.getElementsByClassName("subl-a "+value);
        var count = 0;
        
        for (var i = 0; i < priceRange.length; i++) {
            if (document.getElementsByClassName("subl-a "+value)[i].checked == true) {
                count++;
            }
        }
        if (count == priceRange.length) {
            document.getElementsByClassName("subc-a "+value)[0].checked = true;
        } else {
            document.getElementsByClassName("subc-a "+value)[0].checked = false;
        }
    });
});


$(document).ready(function () {
    $("#status, #level").on('change', function () {
        const arrayDimension = document.getElementsByClassName('subd-a');
        const arrayLesson = document.getElementsByClassName('subl-a');
        const stt = $('#status').val();
        const level = $('#level').val();
        var arrDimension = [];
        var arrLesson = [];
        
        for (var i = 0; i < arrayDimension.length; i++) {
            if(arrayDimension[i].checked == true){
                arrDimension.push(arrayDimension[i].value);
            }
        }
        
        for (var i = 0; i < arrayLesson.length; i++) {
            if(arrayLesson[i].checked == true){
                arrLesson.push(arrayLesson[i].value);
            }
        }
        
        
        $.ajax({
            type: 'POST',
            url: "QuestionList",
            data: {
                arrayDimension: arrDimension.toString(),
                arrayLesson: arrLesson.toString(),
                status: stt,
                level: level
            },
            success: function (data) {
                $('#table').DataTable().destroy();
                var tables = document.getElementById("content-course");
                tables.innerHTML = data;
                $('#table').DataTable();
            }
        });
    });
}); 








$(document).ready(function () {
    $('#search-category').keyup( function (){
        var search = $(this).val().toString().toUpperCase();
        var txtTitle, txtsub;
        
        const title = document.getElementsByClassName('title');
        const sub = document.getElementsByClassName('sub-category');
        
        for (var i = 0; i < sub.length; i++) {
            txtsub = sub[i].childNodes[1].textContent || sub[i].childNodes[1].innerText;
            if(txtsub.toUpperCase().indexOf(search) > -1){
                sub[i].style.display = "";
            } else {
                sub[i].style.display = "none";
            }
        }
        for (var i = 0; i < title.length; i++) {
            txtTitle = title[i].childNodes[1].textContent || title[i].childNodes[1].innerText;
            if(txtTitle.toUpperCase().indexOf(search) > -1){
                title[i].style.display = "";
            } else {
                title[i].style.display = "none";
            }
        }
    });
});



$(document).ready(function () {
    $("input[type='checkbox']").click(function () {
        const arrayDimension = document.getElementsByClassName('subd-a');
        const arrayLesson = document.getElementsByClassName('subl-a');
        const stt = $('#status').val();
        const level = $('#level').val();
        var arrDimension = [];
        var arrLesson = [];
        
        for (var i = 0; i < arrayDimension.length; i++) {
            if(arrayDimension[i].checked == true){
                arrDimension.push(arrayDimension[i].value);
            }
        }
        
        for (var i = 0; i < arrayLesson.length; i++) {
            if(arrayLesson[i].checked == true){
                arrLesson.push(arrayLesson[i].value);
            }
        }
        
        
        $.ajax({
            type: 'POST',
            url: "QuestionList",
            data: {
                arrayDimension: arrDimension.toString(),
                arrayLesson: arrLesson.toString(),
                status: stt,
                level: level
            },
            success: function (data) {
                $('#table').DataTable().destroy();
                var tables = document.getElementById("content-course");
                tables.innerHTML = data;
                $('#table').DataTable();
            }
        });
    });
});

function deleteQuestion(Questionid, btn){
    if (confirm("Do you want to delete this course?")) {
        $.ajax({
                type: 'delete',
                url: "QuestionList?Qid=" + Questionid,
                success: function (data) {
                    $(btn).closest('tr').fadeOut();
                }
        });
    }
}