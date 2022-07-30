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

function changeStatusPricePackage(button, id, account) {
    if (account == 4) {
        if (button.innerHTML.indexOf('on') > -1) {
            deactivePricePackage(button, id);
        } else {
            activePricePackage(button, id);
        }
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

window.onload = function () {
    checkedCategoryOnLoad();
};

function checkedCategoryOnLoad() {
    var elements = document.querySelectorAll('.category');
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].children[0].checked) {
            checkedCategory(elements[i].children[0]);
        }
    }
}

function showFilterBox() {
    var searchCategory = document.getElementById('filter-category-box');
    searchCategory.style.display = "flex";
}

function hideFilterBox() {
    var searchCategory = document.getElementById('filter-category-box');
    searchCategory.style.display = "none";
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
        for (var j = 1; j < items[i].children.length; j++) {
            if (items[i].children[j].querySelector('label').innerText.toUpperCase().indexOf(searchValue) > -1) {
                items[i].children[j].style.display = 'block';
            } else {
                items[i].children[j].style.display = 'none';
            }
        }
    }
    for (var i = 0; i < items.length; i++) {
        if (items[i].children[0].querySelector('label').innerText.toUpperCase().indexOf(searchValue) > -1) {
            items[i].children[0].style.display = 'block';
        } else {
            items[i].children[0].style.display = 'none';
        }
    }
}

function searchExpert(element) {
    let searchValue = element.value.toUpperCase();
    let items = document.querySelector('#expert-checkbox').children;
    for (var i = 0; i < items.length; i++) {
        if (items[i].children[1].innerText.toUpperCase().indexOf(searchValue) > -1) {
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

function isSubmitavailable() {
    let bool1 = checkInputObjective();
    let bool2 = checkSelectCategory();
    if (bool1 === true && bool2 === true) {
        return true;
    }
    return false;
}

function checkInputObjective() {
    let inputElements = document.querySelectorAll('.objective-item');
    let check = true;
    for (var i = 0; i < inputElements.length; i++) {
        if (inputElements[i].children[0].children[0].value.trim() == '') {
            inputElements[i].children[1].style.display = 'block';
            check = false;
        } else {
            inputElements[i].children[1].style.display = 'none';
        }
    }
    if (document.querySelector('#input-box-name').value.trim() == '') {
        document.querySelector('.text-danger.name').style.display = 'block';
        check = false;
    } else {
        document.querySelector('.text-danger.name').style.display = 'none';
    }
    if (document.querySelector('#input-box-description').value.trim() == '') {
        document.querySelector('.text-danger.description').style.display = 'block';
        check = false;
    } else {
        document.querySelector('.text-danger.description').style.display = 'none';
    }
    if (document.querySelector('#input-box-video').value.trim() == '') {
        document.querySelector('.text-danger.video').style.display = 'block';
        check = false;
    } else {
        document.querySelector('.text-danger.video').style.display = 'none';
    }
    return check;
}

function checkSelectCategory() {
    let inputElements = document.querySelectorAll('#category-checkbox input');
    for (var i = 0; i < inputElements.length; i++) {
        if (inputElements[i].checked) {
            document.querySelector('#category-item .text-danger').style.display = 'none';
            return true;
        }
    }
    document.querySelector('#category-item .text-danger').style.display = 'block';
    return false;
}

function addNewObjective() {

    var container = document.querySelectorAll('.objective-item')[0].cloneNode();
    var input = document.querySelector('.objective-item input').cloneNode();
    input.value = '';
    var btn = document.querySelector('.objective-item i').cloneNode();
    var div = document.querySelector('.objective-item div').cloneNode();
    var danger = document.querySelector('.objective-item span').cloneNode();
    danger.innerText = 'This objective cannot be left blank';
    danger.style.display = 'none';
    div.appendChild(input);
    div.appendChild(btn);
    container.appendChild(div);
    container.appendChild(danger);
    document.querySelector('#objectives').appendChild(container);
}

function deleteObjective(element) {
    if (element.parentElement.parentElement.children.length > 1) {
        if (confirm('Are you sure you want to delete this objective?')) {
            element.parentElement.outerHTML = "";
        }
    } else {
        alert("Must exist at least 1 objective!");
    }
}

function checkedSubcategory(element) {
    let listSubCategory = element.parentElement.parentElement.getElementsByTagName('div');
    for (var i = 1; i < listSubCategory.length; i++) {
        if (element.checked === true) {
            listSubCategory[i].children[0].checked = true;
        } else {
            listSubCategory[i].children[0].checked = false;
        }
    }
}

function checkedCategory(element) {
    let category = element.parentElement.parentElement.children[0].children[0];
    let listSubCategory = element.parentElement.parentElement.getElementsByTagName('div');
    let numberSubCategory = listSubCategory.length;
    let count = 1;
    for (var i = 1; i < listSubCategory.length; i++) {
        if (listSubCategory[i].children[0].checked) {
            count++;
        }
    }
    if (count === numberSubCategory) {
        category.indeterminate = false;
        category.checked = true;
    }
    if (count > 1 && count < numberSubCategory) {
        category.checked = false;
        category.indeterminate = true;
    }
    if (count == 1) {
        category.indeterminate = false;
        category.checked = false;
    }
}

