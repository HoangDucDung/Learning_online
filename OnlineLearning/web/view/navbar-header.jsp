<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                   data-bs-toggle="dropdown" aria-expanded="false">
                    ${account.email}
                </a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="../logout">Logout</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>

<div class="col-12">
    <div class="d-flex alight-item-center p-2">
        <h4 class="mb-0">${param["page"]}</h4>
    </div>
</div>
