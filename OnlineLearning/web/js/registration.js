function search() {
    var valueSearch = document.getElementById("search-title-email").value;
    var status = document.getElementById("status").value;
    var from = document.getElementById("from").value;
    var to = document.getElementById("to").value;
    $(function () {
        $.ajax({
            type: "POST",
            url: "./registration",
            cache: false,
            data: {
                valueSearch: valueSearch,
                status: status,
                validFrom: from,
                validTo: to,
                pageNum: document.getElementById("page-num").value
            },
            success: function (result) {
                document.getElementsByClassName("table-content")[0].innerHTML = "";
                document.getElementsByClassName("table-content")[0].innerHTML = result;
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
    search();
}

function clearValueSearch() {
    document.getElementById("search-title-email").value = "";
    document.getElementById("from").value = "";
    document.getElementById("to").value = "";
    document.getElementById("page-num").value = 1;
    document.getElementById("all").selected = true;
    search();
}

function searchByValue() {
    document.getElementById("page-num").value = 1;
    search();
}

function deleteRegistration(id, element) {
    if (confirm('Are you sure you want to delete this registration?')) {
        $(function () {
            $.ajax({
                type: "delete",
                url: `./registration?id=${id}`,
                cache: false,
                success: function () {
                    $(element).closest('tr').fadeOut("slow");
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

var filter = document.getElementById("filter-icon");
var overlay = document.getElementById("overlay");
var formSearch = document.getElementById("search");
var closeIcon = document.getElementById("close-icon");
filter.onclick = function () {
    overlay.style.display = "block";
    formSearch.style.transform = "translateX(0)";
};

overlay.onclick = closeSearch;
closeIcon.onclick = closeSearch;

function closeSearch() {
    overlay.style.display = "none";
    formSearch.style.transform = "translateX(300px)";
}



