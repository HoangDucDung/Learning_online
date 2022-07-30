$(document).ready(function () {
    $('#permissionTable').DataTable({
        "pageLength": 50
    });
});

function deletePermissionById(id, btn) {
    $.confirm({
        title: 'Confirm!',
        content: 'Delete this permission!',
        type: 'orange',
        buttons: {
            delete: {
                btnClass: 'btn-red',
                action: function () {
                    $.ajax({
                        url: `./permission?id=${id}`,
                        type: 'DELETE',
                        success: function (data, textStatus, jqXHR) {
                            $(btn).closest('tr').fadeOut("slow");
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            let msg = (jqXHR.status === 403) ? "You do not have permission to delete this permission."
                                    : "Delete permisson fail."
                            $.alert({
                                type: "red",
                                title: "Error",
                                content: msg
                            });
                        }
                    });
                }
            },
            cancel: function () {
            }
        }
    });
}

$(".btn-edit-permisson").on("click", function () {
    let permissionId = $(this).attr("data-permission-id");
    let permissionName = $(this).attr("data-permission-name");
    let permissionRequestUrl = $(this).attr("data-permission-requestUrl");
    let permissionMethod = $(this).attr("data-permission-method");
    console.log(permissionName);

    $("#permission-id").val(permissionId);
    $("#permission-name").val(permissionName);
    $("#permission-request-url").val(permissionRequestUrl);
    $("#permission-method").val(permissionMethod);


    $("#editPermissionModal").modal("show");
});

$('#editPermissionModal').on('hidden.bs.modal', function (e) {
    $("#permission-id").val("");
    $("#permisson-name").val("");
    $("#permission-request-url").val("");
    $("#permission-method").val("");
});

$("#editPermissionBtn").on("click", function () {
    let permission = {
        "id": $("#permission-id").val(),
        "name": $("#permission-name").val().trim(),
        "requestUrl": $("#permission-request-url").val().trim(),
        "method": $("#permission-method").val()
    };

    if (!permission.name || !permission.requestUrl) {
        $.alert({
            title: "",
            content: "Permission name and request url are not empty.",
            type: "orange"
        });
        return;
    }

    $.ajax({
        method: "PUT",
        url: "/OnlineLearning/management/permission",
        data: JSON.stringify(permission),
        contentType: "application/json",
        success: function (data, textStatus, jqXHR) {
            location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            let msg = "Update permission fail!"
            if (jqXHR.status === 403) {
                msg = "You are not allowed edit permission.";
            }
            $.alert({
                title: "Error",
                content: msg,
                type: "red"
            })
        }
    });
});