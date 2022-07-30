
$(document).ready(function () {
    $('#submit-btn').click( function (){
        console.log($('.lessonID').val()); 
    });
    
})

$(document).ready(function () {
    $.ajax({
            type: 'POST',
            url: "lesson-detail",
            success: function (data) {
                console.log(data);
            }
        });
});