$(document).ready(function () {
    $(".title").click(function (){
        const valueClass =  $('#type-show').attr("class").toString().toLocaleLowerCase();
        if(valueClass.indexOf("contain-category-notshow") > -1){
            document.getElementById('type-show').setAttribute("class","contain-category-show");
            document.getElementsByClassName('fa-solid fa-angle-down')[0].setAttribute("class", "fa-solid fa-angle-up");
        } else{
            document.getElementById('type-show').setAttribute("class","contain-category-notshow");
            document.getElementsByClassName('fa-solid fa-angle-up')[0].setAttribute("class", "fa-solid fa-angle-down");
        }
    });
});

$(document).ready(function () {
    $(".title-subject").click(function () {
        var value = $(this).val();
        var check = $(this).is(":checked");
        if (check == true) {
            for (var i = 0; i < document.getElementsByClassName("type-subject " + value).length; i++) {
                document.getElementsByClassName("type-subject " + value)[i].checked = true;
            }
        } else {
            for (var i = 0; i < document.getElementsByClassName("type-subject " + value).length; i++) {
                document.getElementsByClassName("type-subject " + value)[i].checked = false;
            }
        }
    });
});

function checkCategory(value, categoryId) {
    var valueClass = categoryId;

    var range = document.getElementsByClassName("type-subject " + valueClass);
    var count = 0;

    for (var i = 0; i < range.length; i++) {
        if (document.getElementsByClassName("type-subject " + valueClass)[i].checked == true) {
            count++;
        }
    }

    if (count == range.length) {
        document.getElementsByClassName("title-subject " + valueClass)[0].checked = true;
    } else {
        document.getElementsByClassName("title-subject " + valueClass)[0].checked = false;
    }
}

$(document).ready(function () {
    $(".add-tr").click(function () {
        $('#table > tbody:last-child').append(' <tr> \n\
                                                    <td><input class="tbinput" type="text"></td> \n\
                                                    <td><input class="tbinput numb" type="number"></td> \n\
                                                    <td><input class="tbinput" type="text"></td>\n\
                                                    <td><input class="tbinput" type="text"></td>\n\
                                                </tr>');
    });
});
$(document).ready(function () {
    $(".delete-tr").click(function () {
        $('#table > tbody > tr:last-child').remove();
    });
});