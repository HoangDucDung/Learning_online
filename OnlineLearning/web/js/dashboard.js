function dropdown() {
    var dropdownMenu = document.querySelector('#dropdown-menu');

    if (dropdownMenu.style.display == 'flex') {
        dropdownMenu.style.display = 'none';
    } else {
        dropdownMenu.style.display = 'flex';
    }
}


function deleteBlog(id)
{
    var result = confirm("Do you want to delete this post?");
    if(result)
    {
        window.location.href = "delete-post?id=" + id;
    }
}