
$(document).ready(function () {
    $('#table').DataTable();
});

$(document).ready(function () {
    $(".text-danger").click(function () {
        //this là cái mà vừa click
        var id = $(this).attr('id');
        if (confirm("Do you want to delete this course?")) {
            $.ajax({
                type: 'delete',
                url: "../management/course-list?Cid=" + id,
                success: function (data) {
                    $(this).closest('tr').fadeOut();
                }
            });
        }
    });
});


$(document).ready(function () {
    $(".title-price").click(function () {
        var check = $(this).is(":checked");
        if (check == true) {
            for (var i = 0; i < document.getElementsByClassName("type-price").length; i++) {
                document.getElementsByClassName("type-price")[i].checked = true;
            }
        } else {
            for (var i = 0; i < document.getElementsByClassName("type-price").length; i++) {
                document.getElementsByClassName("type-price")[i].checked = false;
            }
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

$(document).ready(function () {
    $(".type-price").click(function () {
        var priceRange = document.getElementsByClassName("type-price");
        var count = 0;

        for (var i = 0; i < priceRange.length; i++) {
            if (document.getElementsByClassName("type-price")[i].checked == true) {
                count++;
            }
        }
        if (count == priceRange.length) {
            document.getElementsByClassName("title-price")[0].checked = true;
        } else {
            document.getElementsByClassName("title-price")[0].checked = false;
        }
    });
});
//
//$(document).ready(function () {
//    $(".type-price").click(function () {
//        var priceRange = document.getElementsByClassName("type-price");
//        var count = 0;
//
//        for (var i = 0; i < priceRange.length; i++) {
//            if (document.getElementsByClassName("type-price")[i].checked == true) {
//                count++;
//            }
//        }
//        if (count == priceRange.length) {
//            document.getElementsByClassName("title-price")[0].checked = true;
//        } else {
//            document.getElementsByClassName("title-price")[0].checked = false;
//        }
//    });
//});


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
    $("input[type='checkbox']").click(function () {
        var arrayPrice = $(".type-price").tooltip();
        var arrayCategory = $(".type-subject").tooltip();
        const stt = $('#status').val();
        var arrPrice = [];
        var arrCategory = [];
        
        for (var i = 0; i < arrayCategory.length; i++) {
            if(arrayCategory[i].checked == true){
                arrCategory.push(arrayCategory[i].value);
            }
        }
        for (var i = 0; i < arrayPrice.length; i++) {
            if(arrayPrice[i].checked == true){
                arrPrice.push(arrayPrice[i].value);
            }
        }
        $.ajax({
            type: 'POST',
            url: "course-list",
            data: {
                arrayPrice: arrPrice.toString(),
                arrayCategory: arrCategory.toString(),
                status: stt
            },
            success: function (data) {
                var table = document.getElementById("content-course");
                table.innerHTML = data;
                console.log(data);
            }
        });
    });
});


$(document).ready(function () {
    $("#status").on('change', function () {
        var arrayPrice = $(".type-price").tooltip();
        var arrayCategory = $(".type-subject").tooltip();
        const stt = $('#status').val();
        var arrPrice = [];
        var arrCategory = [];
        
        for (var i = 0; i < arrayCategory.length; i++) {
            if(arrayCategory[i].checked == true){
                arrCategory.push(arrayCategory[i].value);
            }
        }
        for (var i = 0; i < arrayPrice.length; i++) {
            if(arrayPrice[i].checked == true){
                arrPrice.push(arrayPrice[i].value);
            }
        }
        $.ajax({
            type: 'POST',
            url: "course-list",
            data: {
                arrayPrice: arrPrice.toString(),
                arrayCategory: arrCategory.toString(),
                status: stt
            },
            success: function (data) {
                var table = document.getElementById("content-course");
                table.innerHTML = data;
                console.log(data);
            }
        });
    });
});

$(document).ready(function () {
    $('#search-category').keyup( function (){
        var search = $(this).val().toString().toUpperCase();
        var txtPrice, txtsub, valueC, valueP;
        
        const price = document.getElementsByClassName('element-filter price');
        const category = document.getElementsByClassName('element-filter category');
        const titleCat = document.getElementsByClassName('filter-subject');
        const titlePrice = document.getElementsByClassName('filter-price');
        
        for (var i = 0; i < price.length; i++) {
            txtPrice = price[i].childNodes[3].textContent || price[i].childNodes[3].innerText;
            if(txtPrice.toUpperCase().indexOf(search) > -1){
                price[i].style.display = "";
            } else {
                price[i].style.display = "none";
            }
        }
        for (var i = 0; i < category.length; i++) {
            txtsub = category[i].childNodes[3].textContent || category[i].childNodes[3].innerText;
            if(txtsub.toUpperCase().indexOf(search) > -1){
                category[i].style.display = "";
            } else {
                category[i].style.display = "none";
            }
        }
        for (var i = 0; i < titleCat.length; i++) {
            valueC = titleCat[i].childNodes[1].childNodes[3].textContent || titleCat[i].childNodes[1].childNodes[3].innerText;
            if(valueC.toUpperCase().indexOf(search) > -1){
                titleCat[i].style.display = "";
            } else {
                titleCat[i].style.display = "none";
            }
        }
        for (var i = 0; i < titlePrice.length; i++) {
            valueP = titlePrice[i].childNodes[1].childNodes[3].textContent || titlePrice[i].childNodes[1].childNodes[3].innerText;
            if(valueP.toUpperCase().indexOf(search) > -1){
                titlePrice[i].style.display = "";
            } else {
                titlePrice[i].style.display = "none";
            }
        }
    });
});