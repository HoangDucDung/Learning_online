$(".btn-delete-question").on("click", function () {
    let id = $(this).attr("data-question-id");
    let clickedBtn = $(this);

    $.confirm({
        title: "Confirm!",
        content: "Do you want to delete this question ?",
        buttons: {
            delete: {
                btnClass: 'btn-red',
                action: function () {
                    $.ajax({
                        type: "DELETE",
                        url: `./question?questionId=${id}`,
                        success: function () {
                            location.reload();
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            $.alert({
                                title: 'Error!',
                                content: 'Delete question fail!',
                            });
                        }
                    });
                }
            },
            cancel: function () {
            }
        }
    });
});