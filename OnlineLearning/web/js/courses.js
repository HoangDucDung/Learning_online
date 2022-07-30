var filterMain = document.querySelectorAll(".c-course-filter-main");
var filterChild = document.querySelectorAll(".c-course-filter-list");
var iconDown = document.querySelectorAll(".icon-down");
filterMain.forEach(function (item, index) {
    item.onclick = function () {
        if (iconDown[index].classList.contains("fa-angle-up")) {
            iconDown[index].classList.toggle("fa-angle-up");
            iconDown[index].classList.toggle("fa-angle-down");
            filterChild[index].style.height = "0";
        } else {
            iconDown[index].classList.toggle("fa-angle-up");
            iconDown[index].classList.toggle("fa-angle-down");
            filterChild[index].style.height = "auto";
        }
    };
});


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
    category.indeterminate = true;
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
    if (count !== subCategories.length) {
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

function searchByPrice() {
    var pageNum = document.getElementById("page-num");
    pageNum.value = 1;

    searchByValue();
}

function sortedCourse() {
    var pageNum = document.getElementById("page-num");
    pageNum.value = 1;

    searchByValue();
}

var text = document.getElementById("form-select-category").innerHTML;

function clearSearch() {
    var holderSearchCate = document.getElementById("holder-search-category");
    holderSearchCate.value = "";

    document.getElementById("form-select-category").innerHTML = text;
    var checkbox = document.querySelectorAll("input[type='checkbox']");
    checkbox.forEach(function (check) {
        check.checked = false;
    });

    var holderSearchCourse = document.querySelector(".search-bar>input");
    holderSearchCourse.value = "";


    var status = document.getElementById("all-course");
    status.selected = true;

    var pageNum = document.getElementById("page-num");
    pageNum.value = 1;

    searchByValue();
}

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

var filterButton = document.getElementsByClassName("filter-button")[0];
var leftContainer = document.getElementById("c-course-container-left");
var rightContainer = document.getElementById("c-course-container-right");

filterButton.onclick = function () {
    if (leftContainer.style.width === "70%") {
        leftContainer.style.width = "100%";
        rightContainer.style.width = "0%";
        rightContainer.style.padding = "0px";
    } else {
        leftContainer.style.width = "70%";
        rightContainer.style.width = "30%";
        rightContainer.style.padding = "0 20px";
    }
};

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

function searchByValue() {
    var subjectDatas = document.querySelectorAll(".search-sub-category-name input");
    var arraySubjectId = [];
    subjectDatas.forEach(function (data) {
        if (data.checked === true) {
            arraySubjectId.push(data.value);
        }
    });
    var subjectData = arraySubjectId.join();
    var categoryDatas = document.querySelectorAll(".search-category-name input");
    var arrayCategoryId = [];
    categoryDatas.forEach(function (data) {
        if (data.checked === true) {
            arrayCategoryId.push(data.value);
        }
    });
    var categoryData = arrayCategoryId.join();
    var priceDatas = document.querySelectorAll(".c-course-filter-item input");
    var arrayPrice = [];
    priceDatas.forEach(function (data) {
        if (data.checked === true) {
            arrayPrice.push(data.value);
        }
    });
    var priceData = arrayPrice.join();
    var pageNum = document.getElementById("page-num").value;
    $(function () {
        $.ajax({
            type: "POST",
            url: "courses",
            cache: false,
            data: {
                status: document.querySelector(".select-bar select").value,
                txtSearch: document.querySelector(".search-bar input").value,
                arraySubjectId: subjectData,
                arrayCategoryId: categoryData,
                arrayPrice: priceData,
                pageNum: pageNum
            },
            success: function (result) {
                document.getElementById("c-course-container-left").innerHTML = "";
                
                document.getElementById("c-course-container-left").innerHTML = result;
            }
        });
    });
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

function displayPrice(element) {
    var priceHolder = element.parentNode.childNodes[1];
    priceHolder.style.visibility = 'visible';
}
function hiddenPrice(element) {
    var priceHolder = element.parentNode.childNodes[1];
    priceHolder.style.visibility = 'hidden';
}