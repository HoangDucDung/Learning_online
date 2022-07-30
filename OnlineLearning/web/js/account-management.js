function changeStatus(button, id) {
    if (button.innerHTML.indexOf('on') > -1) {
        hideAccount(button, id);
    } else {
        showAccount(button, id);
    }
}

function hideAccount(button, id) {
    if (confirm('Are you sure you want to hide this account?')) {
        $(function () {
            $.ajax({
                type: "post",
                url: `account?id-hide=${id}`,
                cache: false,
                success: function () {
                    button.innerHTML = "";
                    button.innerHTML += '<i class="fa-solid fa-toggle-off"></i>';
                }
            });
        });
    }
}

function showAccount(button, id) {
    if (confirm('Are you sure you want to show this account?')) {
        $(function () {
            $.ajax({
                type: "post",
                url: `account?id-show=${id}`,
                cache: false,
                success: function () {
                    button.innerHTML = "";
                    button.innerHTML += '<i class="fa-solid fa-toggle-on"></i>';
                }
            });
        });
    }
}