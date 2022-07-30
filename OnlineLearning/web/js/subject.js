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

function checkedCategory(element) {
    var listSubCate = element.parentNode.parentNode.parentNode.childNodes[3].getElementsByClassName("search-sub-category-name");
    for (i = 0; i < listSubCate.length; i++) {
        if (element.checked === true)
            listSubCate[i].childNodes[1].checked = true;
        else {
            listSubCate[i].childNodes[1].checked = false;
        }
    }
    var pageNum = document.getElementById("page-num");
    pageNum.value = 1;
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
    var pageNum = document.getElementById("page-num");
    pageNum.value = 1;
    searchByValue();
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
            url: "./course",
            cache: false,
            data: {
                status: document.getElementById("status-option").value,
                txtSearch: document.getElementById("search-course").value,
                arraySearchId: data,
                pageNum: pageNum
            },
            success: function (result) {
                document.getElementsByClassName("table-content")[0].innerHTML = "";
                document.getElementsByClassName("table-content")[0].innerHTML += result;
            }
        });
    });
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

    var holderSearchCourse = document.getElementById("search-course");
    holderSearchCourse.value = "";


    var status = document.getElementById("All");
    status.selected = true;
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

function deleteSubject(id, btn) {
    if (confirm('Are you sure you want to delete this subject?')) {
        $(function () {
            $.ajax({
                type: "delete",
                url: `course?courseID=${id}`,
                cache: false,
                success: function () {
                    $(btn).closest('tr').fadeOut("slow");
                }
            });
        });
    }
}

var btn = document.querySelector(".fa-solid.fa-bars").parentNode;
var sideNav = document.querySelector(".col-sm-2.min-vh-100.bg-dark.p-0");
var container = document.querySelector(".col-sm-10.p-0");
sideNav.style.transition = "all ease-in .2s";
container.style.transition = "all ease-in .2s";

btn.onclick = function () {

    var sideNav = document.querySelector(".col-sm-2.min-vh-100.bg-dark.p-0");
    var container = document.querySelector(".col-sm-10.p-0");
    if (sideNav.style.width !== '0px') {
        sideNav.style.width = 0;
        container.style.width = "100%";
    } else {
        sideNav.style.width = "16.66666667%";
        container.style.width = "calc(100% - 16.66666667%)";
    }

};