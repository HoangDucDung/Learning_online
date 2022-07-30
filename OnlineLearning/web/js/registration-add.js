$(document).ready(function () {
    $('.select-search').selectize({
        sortField: 'text'
    });
});
var arrayOption = null;
function changeCourse() {
    var courseId = document.getElementById("select-subject").value;
    $(function () {
        $.ajax({
            type: "PUT",
            url: "./registration-add?courseId=" + courseId,
            cache: false,
            success: function (result) {
                var arr = JSON.parse(result);
                arrayOption = arr;
                document.getElementById("select-package").innerHTML = "";
                for (var item of arr) {
                    document.getElementById("select-package").innerHTML += "<option value='" + item.id + "'>" + item.name + "</option>";
                }
                document.getElementById("price").innerHTML = arr[0].price == 0 ? "Free" : arr[0].price;
                document.getElementById("sale-price").innerHTML = arr[0].salePrice == 0 ? "Free" : arr[0].salePrice;
                document.querySelectorAll("#select-package option")[0].selected = true;
            }
        });
    });
}

function submitForm() {
    var subject = document.getElementById("select-subject").value;
    var email = document.getElementById("select-email").value;
    var package = document.getElementById("select-package").value;
    var note = document.getElementById("notes").value;

    var alert = document.getElementById("alert");
    if (subject === "") {
        alert.innerHTML = "";
        alert.classList.add("alert");
        alert.classList.add("alert-danger");
        alert.innerHTML = "Please select subject";
        return false;
    }
    if (email === "") {
        alert.innerHTML = "";
        alert.classList.add("alert");
        alert.classList.add("alert-danger");
        alert.innerHTML = "Please select email";
        return false;
    }
    return true;
}

function changePackageEdit() {
    var courseId = document.getElementById("select-subject").value;
    $(function () {
        $.ajax({
            type: "PUT",
            url: "./registration-add?courseId=" + courseId,
            cache: false,
            success: function (result) {
                var arr = JSON.parse(result);
                arrayOption = arr;
                changePackage();
            }
        });
    });
    
}

function changePackage() {
    var id = document.getElementById("select-package").value;
    for (var item of arrayOption) {
        if (id === item.id) {
            document.getElementById("price").innerHTML = item.price;
            document.getElementById("sale-price").innerHTML = item.salePrice;
        }
    }
}