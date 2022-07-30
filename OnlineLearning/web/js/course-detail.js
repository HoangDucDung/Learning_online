var overlay = document.getElementById("cd-overlay");
var notice = document.getElementById("notice-buy-course");
var closeBtn = document.getElementById("close-btn");
var btn = document.querySelector(".course-enroll-button");

btn.onclick = function () {
    if(btn.getAttribute("href") === "#") {
        overlay.style.display = "block";
        notice.style.transform = "translateY(0)";
    }
};

function closeNotice() {
    overlay.style.display = "none";
    notice.style.transform = "translateY(calc(-100% - 500px))";
}
overlay.onclick = closeNotice;
closeBtn.onclick = closeNotice;

var topics = document.querySelectorAll(".topic");
var icons = document.querySelectorAll(".fa-angle-up");
var listGroup = document.querySelectorAll(".list-group-flush");
topics.forEach(function (topic, index) {
    topic.onclick = function () {
        console.log(listGroup[index].style.height);
        if (listGroup[index].style.height === "auto") {
            listGroup[index].style.height = 0;
            icons[index].classList.toggle("fa-angle-down");
            icons[index].classList.toggle("fa-angle-up");
        }
        else if(listGroup[index].style.height !== 'auto'){
            listGroup[index].style.height = "auto";
            icons[index].classList.toggle("fa-angle-down");
            icons[index].classList.toggle("fa-angle-up");
        } 
    };
});

function checkBalance(balance) {
    var list = document.querySelectorAll(".list-price");
    list.forEach(function(i) {
        if(i.checked === true) {
            var price = i.parentNode.childNodes[1].value;
            if(balance >= price) {
                return true;
            } else {
                document.getElementById("notice-not-enough").innerHTML = "Your balance is not enough to pay this course";
                return false;
            }
        }
    });
    return false;
}