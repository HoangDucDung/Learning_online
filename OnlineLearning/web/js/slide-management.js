$(function () {
    $("#collection").select2();
});

$(document).ready( function () {
    $('#table').DataTable();
} );

function changeStatus(button, id) {
    if (button.innerHTML.indexOf('on') > -1) {
        hideSlide(button, id);
    } else {
        showSlide(button, id);
    }
}

function hideSlide(button, id) {
    if (confirm('Are you sure you want to hide this slide?')) {
        $(function () {
            $.ajax({
                type: "post",
                url: `slide-list?id-hide=${id}`,
                cache: false,
                success: function () {
                    button.innerHTML = "";
                    button.innerHTML += '<i class="fa-solid fa-toggle-off"></i>';
                }
            });
        });
    }
}

function showSlide(button, id) {
    if (confirm('Are you sure you want to show this slide?')) {
        $(function () {
            $.ajax({
                type: "post",
                url: `slide-list?id-show=${id}`,
                cache: false,
                success: function () {
                    button.innerHTML = "";
                    button.innerHTML += '<i class="fa-solid fa-toggle-on"></i>';
                }
            });
        });
    }
}

function deletePricePackage(id, btn) {
    if (confirm('Are you sure you want to delete this slide?')) {
        $(function () {
            $.ajax({
                type: "delete",
                url: `slide-list?id=${id}`,
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