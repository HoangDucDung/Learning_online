function deleteSetting(sid, id, type)
{
    var result = confirm("Do you want to delete this setting?");
    if (result)
    {
        window.location.href = "setting-delete?sid=" + sid + "&id=" + id + "&type=" + type;
    }
}

function checkExist(alert,id){
    if(alert !== ""){
        document.getElementById('infor').style.display = 'block';
    }else{
        window.location.href = "quizedit?quizID=" + id;
    }
}

function deleteQuiz(id)
{
        window.location.href = "quizdelete?quizid=" + id;
}

$(document).ready( function () {
    $('#table').DataTable();
} );

function genderChanged(obj)
{
    var message = document.getElementById('superblog');
    var message2 = document.getElementById('mainsubject');
    var value = obj.value;
    if (value === 'SUB_CATEGORY_POST'){
        message.style.display = 'block';
    }
    
    else if (value === 'SUBJECT'){
        message2.style.display = 'block';
    }
    
}
