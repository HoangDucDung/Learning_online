var filterIcon = document.getElementsByClassName("afterTemp")[0];

filterIcon.onmouseover = function () {
    var searchCategory = document.getElementById("search-category");
    searchCategory.style.display = "block";
};

filterIcon.onmouseout = function () {
    var searchCategory = document.getElementById("search-category");
    searchCategory.style.display = "none";
};

function searchCategory() {

    var input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('holder-search-category');

    filter = input.value.toUpperCase();
    ul = document.getElementById("form-select-category");
    li = ul.getElementsByClassName("search-category-item");

    for (i = 0; i < li.length; i++) {
        a = li[i].childNodes[1].childNodes[1].childNodes[3];
        txtValue = a.textContent || a.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
}

function openOption(element) {
    var noticeUnenroll = element.parentNode.childNodes[2];
    noticeUnenroll.style.opacity = 1;
    noticeUnenroll.style.zIndex = 3;
}

function closeNotice(element) {
    var noticeUnenroll = element.parentNode.parentNode.parentNode;
    noticeUnenroll.style.opacity = 0;
    noticeUnenroll.style.zIndex = -1;
}

function searchByValue() {
    var datas = document.querySelectorAll(".search-sub-category-name input");
    var arraySearchId = [];
    datas.forEach(function (data) {
        if (data.checked === true) {
            arraySearchId.push(data.value);
        }
    });
    var data = arraySearchId.join();
    var pageNum = document.getElementById("page-num").value;
    $(function () {
        $.ajax({
            type: "POST",
            url: "my-course",
            cache: false,
            data: {
                datejoin: document.getElementById("date-join").value,
                progress: document.getElementById("progress-bar").value,
                txtSearch: document.getElementById("search-my-course").value,
                arraySearchId: data,
                pageNum: pageNum
            },
            success: function (result) {
                document.getElementsByClassName("my-course")[0].innerHTML = "";
                document.getElementsByClassName("my-course")[0].innerHTML += result;
            }
        });
    });
}

function unenrollCourse(element) {
    $(function () {
        $.ajax({
            type: "DELETE",
            url: "./my-course?courseId=" + element.parentNode.childNodes[1].value,
            success: function (result) {
                element.parentNode.parentNode.parentNode.parentNode.parentNode.remove();
            }
        });
    });
}

function checkedCategory(element) {
    var listSubCate = element.parentNode.parentNode.parentNode.childNodes[3].getElementsByClassName("search-sub-category-name");
    for (i = 0; i < listSubCate.length; i++) {
        if (element.checked === true)
            listSubCate[i].childNodes[1].checked = true;
        else {
            listSubCate[i].childNodes[1].checked = false;
        }
    }
    searchByValue();
}

function checkedSubCategory(element) {
    var category = element.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].childNodes[1];
    var isChecked = false;
    var subCategories = element.parentNode.parentNode.getElementsByClassName("search-sub-category-name");
    var count = 0;
    for (var i = 0; i < subCategories.length; i++) {
        if (subCategories[i].childNodes[1].checked === true) {
            isChecked = true;
            category.checked = true;
            count++;
        }
    }
    if(count !== subCategories.length) {
        category.indeterminate = true;
    } else {
        category.indeterminate = false;
    }
    if (isChecked === false) {
        category.checked = false;
        category.indeterminate = false;
    }
    searchByValue();
}

function searchProperty() {
    var pageNum = document.getElementById("page-num");
    pageNum.value = 1;
    
    searchByValue();
}

var text = document.getElementById("form-select-category").innerHTML;

function clearValueSearch() {
    var holderSearchCate = document.getElementById("holder-search-category");
    holderSearchCate.value = "";

    document.getElementById("form-select-category").innerHTML = text;
    var checkbox = document.querySelectorAll("input[type='checkbox']");
    checkbox.forEach(function (check) {
        check.checked = false;
    });

    var holderSearchCourse = document.getElementById("search-my-course");
    holderSearchCourse.value = "";

    var dateJoin = document.getElementById("date-join");
    dateJoin.value = "";

    var progress = document.getElementById("All");
    progress.selected = true;
    searchByValue();
}

function pagination(page) {
    window.scroll({
        top: 350,
        behavior: 'smooth'
    });
    var pageNum = document.getElementById("page-num");
    pageNum.value = page;
    searchByValue();
}

function voteStar(element, courseId) {

    var index = 0;
    var parentStar = element.parentNode;
    var listStar = parentStar.getElementsByClassName('star');

    for (var i = 0; i < listStar.length; i++) {
        if (listStar[i].classList.contains('selected'))
            listStar[i].classList.remove('selected');
    }

    for (var i = 0; i < listStar.length; i++) {
        if (listStar[i] === element) {
            index = i + 1;
        }
    }

    for (var i = 0; i < index; i++) {
        if (listStar[i].classList.contains('selected') === false)
            listStar[i].classList.add('selected');
    }

    $(function () {
        $.ajax({
            type: "POST",
            url: "voted",
            data: {
                star: index,
                courseId: courseId
            },
            success: function (result) {
            }
        });
    });
}

function dropDownSubCate(icon) {
    
    if (icon.classList.contains("fa-angle-up")) {
            icon.classList.toggle("fa-angle-up");
            icon.classList.toggle("fa-angle-down");
            icon.parentNode.parentNode.childNodes[3].style.height = "0";
        } else {
            icon.classList.toggle("fa-angle-up");
            icon.classList.toggle("fa-angle-down");
            icon.parentNode.parentNode.childNodes[3].style.height = 'auto';
        }
}