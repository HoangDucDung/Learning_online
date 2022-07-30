function customAlert(title, content, type) {
    $.alert({
        title: title,
        content: content,
        type: type,
        typeAnimated: true,
        buttons: {
            close: function () {
            }
        }
    });
}

$("#updateBtn").click(function () {
    let roleId = this.value;
    let permissionIds = [];
    $(".permission-check").each(function (e) {
        if (this.checked)
            permissionIds.push(this.value);
    });
    let data = {
        roleId: roleId,
        permissionIds: permissionIds
    }

    $.ajax({
        url: './rolepermission',
        type: 'PUT',
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (data, textStatus, jqXHR) {
            $.alert({
                type: "green",
                title: "",
                content: "Update permission successfully !"
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 403) {
                customAlert("Encountered an error!", "You are not allowed execute this action.", "red");
            } else {
                customAlert("Encountered an error!", "Update permission fail.", "red");
            }
        }
    });
});
$("#deleteBtn").click(function () {
    let roleId = this.value;
    $.confirm({
        title: 'Confirm',
        content: `Do you want to delete the role ${this.getAttribute("data-role-name")} ?`,
        buttons: {
            confirm: {
                btnClass: 'btn-blue',
                action: function () {
                    $.ajax({
                        url: `./rolepermission?roleId=${roleId}`,
                        type: 'DELETE',
                        success: function (data, textStatus, jqXHR) {
                            location.reload();
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            if (jqXHR.status == 403) {
                                customAlert("Encountered an error!", "You are not allowed execute this action.", "red");
                            } else {
                                customAlert("Encountered an error!", "Delete role fail.", "red");
                            }
                        }
                    });
                }
            },
            cancel: {
                btnClass: 'btn-red'
            }
        }
    });
});
$("#btnCreateRole").click(function () {
    let role = {
        name: $("#inputRoleName").val().trim()
    };
    if (!role.name) {
        $.alert({
            type: 'red',
            title: 'Error',
            content: 'Role name is required'
        });
        return;
    }

    $.ajax({
        url: './rolepermission',
        type: 'POST',
        data: JSON.stringify(role),
        contentType: 'application/json',
        success: function (data, textStatus, jqXHR) {
            console.log(data);
            location.href = "./rolepermission?roleId=" + data.id;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 403) {
                $("#createNewRoleModal").modal('hide')
                customAlert("", "You don't have Create New Role Permission.", "red");
            } else {
                customAlert("Encountered an error!", "Create new role fail.", "red");
            }
        }
    });
});

$("#checkAllBtn").on("click", function () {
    $.each($(".permission-check"), function (i, e) {
        e.checked = true;
    });
});

$("#uncheckAllBtn").on("click", function () {
    $.each($(".permission-check"), function (i, e) {
        e.checked = false;
    })
});
//$.each(function ($(".permission-check"), e) {
//console.log(e.value);
//        });
//}
//});