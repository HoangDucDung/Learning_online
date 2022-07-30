<nav class="navbar navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">${account.role}</a>
        <hr>
        <div class="">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link ${(param["page"] == "Dashboard") ? "active" : ""}"
                        href="./dashboard">
                        <i class="fa-solid fa-gauge"></i>
                        Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${(param["page"] == "Manage Course") ? "active" : ""}" href="./course">
                        <i class="fa-solid fa-bars-progress"></i>
                        Manage Course
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${(param["page"] == "Manage Post") ? "active" : ""}" href="./post">
                        <i class="fa-solid fa-bars-progress"></i>
                        Manage Post
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${(param["page"] == "Manage Slide") ? "active" : ""}" href="./slide">
                        <i class="fa-solid fa-bars-progress"></i>
                        Manage Slide
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${(param["page"] == "Manage Account") ? "active" : ""}" href="./account">
                        <i class="fa-solid fa-user"></i>
                        Manage Account
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${(param["page"] == "Manage Access") ? "active" : ""}" href="./rolepermission">
                        <i class="fa-solid fa-gears"></i>
                        Manage Access And Request
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${(param["page"] == "Manage Permission") ? "active" : ""}" href="./permission">
                        <i class="fa-solid fa-gears"></i>
                        Manage Permission
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
