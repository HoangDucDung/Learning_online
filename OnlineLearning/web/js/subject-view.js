function deleteDimension(id, btn) {
    if (confirm('Are you sure you want to delete this dimension?')) {
        $(function () {
            $.ajax({
                type: "delete",
                url: `subject-detail?dimensionID=${id}`,
                cache: false,
                success: function () {
                    $(btn).closest('tr').fadeOut("slow");
                }
            });
        });
    }
}

function deletePricePackage(id, btn) {
    if (confirm('Are you sure you want to delete this price package?')) {
        $(function () {
            $.ajax({
                type: "delete",
                url: `subject-detail?priceID=${id}`,
                cache: false,
                success: function () {
                    $(btn).closest('tr').fadeOut("slow");
                }
            });
        });
    }
}

function changeStatusPricePackage(button, id) {
    if (button.innerHTML.indexOf('on') > -1) {
        deactivePricePackage(button, id);
    } else {
        activePricePackage(button, id);
    }
}

function deactivePricePackage(button, id) {
    if (confirm('Are you sure you want to deactive this price package?')) {
        $(function () {
            $.ajax({
                type: "post",
                url: `subject-detail?id-deactive=${id}`,
                cache: false,
                success: function () {
                    button.innerHTML = "";
                    button.innerHTML += '<i class="fa-solid fa-toggle-off"></i>';
                }
            });
        });
    }
}

function activePricePackage(button, id) {
    if (confirm('Are you sure you want to active this price package?')) {
        $(function () {
            $.ajax({
                type: "post",
                url: `subject-detail?id-active=${id}`,
                cache: false,
                success: function () {
                    button.innerHTML = "";
                    button.innerHTML += '<i class="fa-solid fa-toggle-on"></i>';
                }
            });
        });
    }
}

$(document).ready(function () {
    $('#table1').DataTable();
});

$(document).ready(function () {
    $('#table2').DataTable();
});

function openCity(evt, cityName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

function showFilterBox() {
    var searchCategory = document.getElementById('filter-category-box');
    searchCategory.style.display = "flex";
}

function hideFilterBox() {
    var searchCategory = document.getElementById('filter-category-box');
    searchCategory.style.display = "none";
}

function checkedCategory(element) {
    if (element.checked) {
        uncheckedSubcategory();
        uncheckedCategory();
        element.checked = true;
        textDisplay = element.parentNode.children[1].innerText;
        document.querySelector('#current-category').innerText = textDisplay;
    }
    let count = 0;
    let checkBoxes = document.querySelectorAll('#category-checkbox input');
    for (let i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked === false) {
            count++;
        }
    }
    if (count === checkBoxes.length) {
        document.querySelector('#current-category').innerText = '';
    }
}

function uncheckedSubcategory() {
    let listSubCategory = document.querySelectorAll('.category');
    for (var i = 0; i < listSubCategory.length; i++) {
        listSubCategory[i].children[0].checked = false;
    }
}

function uncheckedCategory() {
    let listCategory = document.querySelectorAll('.main-category');
    for (var i = 0; i < listCategory.length; i++) {
        listCategory[i].children[0].checked = false;
    }
}

function searchCategory(element) {
    let searchValue = element.value.toUpperCase();
    let items = document.querySelector('#category-checkbox').children;
    for (var i = 0; i < items.length; i++) {
        if (items[i].children[0].innerText.toUpperCase().indexOf(searchValue) > -1) {
            items[i].style.display = 'block';
        } else {
            items[i].style.display = 'none';
        }
    }
}

function searchExpert(element) {
    let searchValue = element.value.toUpperCase();
    let items = document.querySelector('#expert-checkbox').children;
    for (var i = 0; i < items.length; i++) {
        if (items[i].children[0].innerText.toUpperCase().indexOf(searchValue) > -1) {
            items[i].style.display = 'block';
        } else {
            items[i].style.display = 'none';
        }
    }
}

function showExpertBox() {
    var searchCategory = document.getElementById('filter-expert-box');
    searchCategory.style.display = "flex";
}

function hideExpertBox() {
    var searchCategory = document.getElementById('filter-expert-box');
    searchCategory.style.display = "none";
}

function checkSelectCategory() {
    let inputElements = document.querySelectorAll('#category-checkbox input');
    for (var i = 0; i < inputElements.length; i++) {
        if (inputElements[i].checked) {
            return true;
        }
    }
    document.querySelector('#category-item .text-danger').style.display = 'block';
    return false;
}