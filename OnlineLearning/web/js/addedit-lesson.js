$(document).ready(function () {
    $(".lesson-type").on('change', function () {
        var values = $('.lesson-type').val();
        let params = (new URL(document.location)).searchParams;
        let Lid = params.get("Lid");
        if(values == 1){
            $('.type-contentv').attr('class', "type-contentv lestype");
            document.getElementsByClassName('type-contentv')[0].childNodes[0].required = true;
        } else{
            $('.type-contentv').attr('class', "type-contentv");
            document.getElementsByClassName('type-contentv')[0].childNodes[0].required = false;
        }
        if(values == 2 && Lid != null){
            $('.type-contentq').attr('class', "type-contentq lestype");
        } else{
            $('.type-contentq').attr('class', "type-contentq");
        }
    });
});

$(document).ready(function () {
    $("#submit-btn").click(function () {
        var inputCheck = document.getElementsByTagName('input');
        for (var i = 0; i < inputCheck.length; i++) {
            inputCheck[i].value = inputCheck[i].value.trim();
        }
    });
});

$(document).ready(function () {
    $(".sublesson").on('change', function () {
        var lessontype = $(this).val();
        var lorder = $('.lesson-order').val();
        if(lorder == null){
            lorder = "";
        }
        $.ajax({
            type: 'get',
            url: "../order",
            data: {
                orderId: lessontype,
                lessonOrder: lorder,
            },
            success: function (data) {
                var order = document.getElementById("order");
                order.innerHTML = data;
            }
        });
    });
});
    