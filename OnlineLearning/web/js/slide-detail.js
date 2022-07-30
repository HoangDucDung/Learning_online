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

function isSubmitAvailable() {
    let check = true;
    if (document.querySelector('#input-title').value.trim() == '') {
        document.querySelector('.text-danger.title').style.display = 'block';
        check = false;
    } else {
        document.querySelector('.text-danger.title').style.display = 'none';
    }
    if (document.querySelector('#input-subtitle').value.trim() == '') {
        document.querySelector('.text-danger.subtitle').style.display = 'block';
        check = false;
    } else {
        document.querySelector('.text-danger.subtitle').style.display = 'none';
    }
    if (document.querySelector('#input-description').value.trim() == '') {
        document.querySelector('.text-danger.description').style.display = 'block';
        check = false;
    } else {
        document.querySelector('.text-danger.description').style.display = 'none';
    }
    if (document.querySelector('#input-backlink').value.trim() == '') {
        document.querySelector('.text-danger.backlink').style.display = 'block';
        check = false;
    } else {
        document.querySelector('.text-danger.backlink').style.display = 'none';
    }
    return check;
}