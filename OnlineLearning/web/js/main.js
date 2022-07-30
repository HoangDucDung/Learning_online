
function deletePermissionById(id, btn) {
    if (confirm("Delete this permission? ")) {
        $.ajax({
            url: `./permission?id=${id}`,
            type: 'DELETE',
            success: function (data, textStatus, jqXHR) {
                console.log("delete ok");
                $(btn).closest('tr').fadeOut("slow");
            }
        });
    }
}

function submitPermission() {
    let permisisonName = $("[name = permissionName]").val();
    let requestUrl = $("[name = requestUrl]").val();
    let method = $("[name = method]").val();

    let permission = {
        id: 0,
        name: permisisonName,
        requestUrl: requestUrl,
        method: method
    };

    $.ajax({
        url: `./permission`,
        type: 'POST',
        data: JSON.stringify(permission),
        contentType: 'application/json',
        success: function (data, textStatus, jqXHR) {
            console.log("create ok");
        },
        error: function () {
            console.log('Create Failed');
        }
    });
}