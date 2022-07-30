<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <button class="btn" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo01">
            <i class="fa-solid fa-bars"></i>
        </button>

        <ul class="navbar-nav me-auto mb-lg-0">
        </ul>

        <form class="d-flex">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                ${account.email}
            </a>
            <ul class="dropdown-menu dropdown-menu-end me-4" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="../logout">Logout</a></li>
            </ul>
        </form>
    </div>
</nav>

<div class="d-flex p-2 bd-highlight mb-4 border-bottom align-items-center bg-white">
    <div class="fs-4 font-weight-bold">${param["page"]}</div>
</div>
