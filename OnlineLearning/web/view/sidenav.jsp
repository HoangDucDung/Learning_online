<nav class="navbar navbar-dark bg-dark">
    <div class="navbar-collapse">

        <div class="text-center border-bottom pb-3 d-none d-md-block">
            <img src="../img/${account.profilePictureUrl}" height="40" width="40">
            <a class="navbar-brand d-none d-md-inline px-2" href="#">${account.role}</a>
        </div>

        <ul class="navbar-nav fs-5">
            <li class="nav-item m-auto m-md-1">
                <a style="font-size: 15px;padding-left: 10px" class="nav-link ${(param["page"] == "Dashboard") ? "active" : ""}"
                   href="./dashboard">
                    <i class="fa-solid fa-gauge"></i>
                    <span class="d-none d-md-inline px-2">Dashboard</span>
                </a>
            </li>

            <li class="nav-item m-auto m-md-1">
                <a style="font-size: 15px;padding-left: 10px" class="nav-link ${(param["page"] == "Manage Post") ? "active" : ""}" href="./post">
                    <i class="fa-solid fa-bars-progress"></i>
                    <span class="d-none d-md-inline px-2">Manage Post</span>
                </a>
            </li>
            <li class="nav-item m-auto m-md-1">
                <a style="font-size: 15px;padding-left: 10px" class="nav-link ${(param["page"] == "Manage Slide") ? "active" : ""}" href="./slide-list">
                    <i class="fa-solid fa-bars-progress"></i>
                    <span class="d-none d-md-inline px-2">Manage Slide</span>

                </a>
            </li>
            <li class="nav-item m-auto m-md-1">
                <a style="font-size: 15px;padding-left: 10px" class="nav-link ${(param["page"] == "Manage Course") ? "active" : ""}" href="./course">
                    <i class="fa-solid fa-bars-progress"></i>
                    <span class="d-none d-md-inline px-2">Manage Course</span>
                </a>
            </li>
            <li class="nav-item">
                <a style="font-size: 15px;padding-left: 10px" class="nav-link ${(param["page"] == "Manage Quiz") ? "active" : ""}" href="./quizsetting">
                    <i class="fa-solid fa-bars-progress"></i>
                    <span class="d-none d-md-inline px-2">Manage Quiz</span>
                </a>
            </li>
            <li class="nav-item m-auto m-md-1">
                <a style="font-size: 15px;padding-left: 10px" class="nav-link ${(param["page"] == "Manage Account") ? "active" : ""}" href="./account">
                    <i class="fa-solid fa-user"></i>
                    <span class="d-none d-md-inline px-2">Manage Account</span>
                </a>
            </li>
            <li class="nav-item m-auto m-md-1">
                <a style="font-size: 15px;padding-left: 10px" class="nav-link ${(param["page"] == "Manage Registration") ? "active" : ""}" href="./registration">
                    <i class="fa-solid fa-user"></i>
                    <span class="d-none d-md-inline px-2">Manage Registration</span>
                </a>
            </li>
            <li class="nav-item m-auto m-md-1">
                <a style="font-size: 15px;padding-left: 10px" class="nav-link ${(param["page"] == "Setting") ? "active" : ""}" href="./setting">
                    <i class="fa-solid fa-gears"></i>
                    <span class="d-none d-md-inline px-2">Setting</span>
                </a>
            </li>
            <li class="nav-item m-auto m-md-1">
                <a style="font-size: 15px;padding-left: 10px" class="nav-link ${(param["page"] == "Manage Access") ? "active" : ""}" href="./rolepermission">
                    <i class="fa-solid fa-user-gear"></i>
                    <span class="d-none d-md-inline px-2">Role & Permission</span>
                </a>
            </li>
            <li class="nav-item m-auto m-md-1">
                <a style="font-size: 15px;padding-left: 10px" class="nav-link ${(param["page"] == "Manage Permission") ? "active" : ""}" href="./permission">
                    <i class="fa-solid fa-lock"></i>
                    <span class="d-none d-md-inline px-2">Manage Permission</span>
                </a>
            </li>
        </ul>

    </div>
</nav>
