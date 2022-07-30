function dropdown() {
    var dropdownMenu = document.querySelector('#dropdown-menu');

    if (dropdownMenu.style.display == 'flex') {
        dropdownMenu.style.display = 'none';
    } else {
        dropdownMenu.style.display = 'flex';
    }
}