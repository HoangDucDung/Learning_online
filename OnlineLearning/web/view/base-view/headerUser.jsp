<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Navbar Start -->
<c:choose>
    <c:when test="${sessionScope.account == null}">
        <nav class="navbar">
            <a href="home" class="navbar-brand">
                <h2><i class="fa-solid fa-book"></i>eLEARNING</h2>
            </a>
            <div class="navbar-collapse" id="navbarCollapse">
                <div class="navbar-nav">
                    <a href="home" class="nav-item nav-link ${(param["page"] == "Home") ? "active" : ""}">Home</a>
                    <a href="courses" class="nav-item nav-link ${(param["page"] == "Courses") ? "active" : ""}">Courses</a>
                    <a href="blog" class="nav-item nav-link ${(param["page"] == "Blog") ? "active" : ""}">Blog</a>
                </div>
                <a href="login" class="btn-primary">Join Now <i class="fa-solid fa-arrow-right"></i></a>
            </div>
        </nav>
    </c:when>
    <c:otherwise >
        <nav class="navbar">
            <a href="home" class="navbar-brand">
                <h2><i class="fa-solid fa-book"></i>eLEARNING</h2>
            </a>
            <div class="navbar-collapse" id="navbarCollapse">
                <div class="navbar-nav">
                    <a href="home" class="nav-item nav-link ${(param["page"] == "Home") ? "active" : ""}">Home</a>
                    <a href="courses" class="nav-item nav-link ${(param["page"] == "Courses") ? "active" : ""}">Courses</a>
                    <a href="my-course" class="nav-item nav-link ${(param["page"] == "My Courses") ? "active" : ""}">My Courses</a>
                    <a href="blog" class="nav-item nav-link ${(param["page"] == "Blog") ? "active" : ""}">Blog</a>
                </div>
                <div id="top-bar">
                    <ul>
                        <div class="topbar-divider"></div>
                        <!-- Nav Item - User Information -->
                        <li>
                            <a href="#" id="dropdown-toggle" onclick="dropdown()">
                                <div id="name-balance">
                                    <span>${sessionScope.account.firstName} ${sessionScope.account.lastName}</span>
                                    <span>${sessionScope.account.balance} $</span>
                                </div>
                                <img src="img/${sessionScope.account.profilePictureUrl}">
                            </a>
                            <!-- Dropdown - User Information -->
                            <div id="dropdown-menu">
                                <a class="dropdown-item" href="profile">
                                    <i class="fa-solid fa-address-card"></i>
                                    Profile
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fa-solid fa-gear"></i>
                                    Settings
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fa-solid fa-list-check"></i>
                                    Activity Log
                                </a>
                                <c:choose>
                                    <c:when test="${sessionScope.account.role.id == 1}">
                                        <a class="dropdown-item" href="management/course">
                                            <i class="fa-solid fa-bars-progress"></i>
                                            Management
                                        </a>
                                    </c:when>
                                    <c:when test="${sessionScope.account.role.id == 3}">
                                        <a class="dropdown-item" href="management/dashboard">
                                            <i class="fa-solid fa-bars-progress"></i>
                                            Management
                                        </a>
                                    </c:when>
                                    <c:when test="${sessionScope.account.role.id == 4}">
                                        <a class="dropdown-item" href="management/dashboard">
                                            <i class="fa-solid fa-bars-progress"></i>
                                            Management
                                        </a>
                                    </c:when>
                                    <c:when test="${sessionScope.account.role.id == 5}">
                                        <a class="dropdown-item" href="management/dashboard">
                                            <i class="fa-solid fa-bars-progress"></i>
                                            Management
                                        </a>
                                    </c:when>
                                </c:choose>

                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="logout">
                                    <i class="fa-solid fa-right-from-bracket"></i>
                                    Logout
                                </a>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <script src="./js/base.js"></script>
    </c:otherwise>     
</c:choose>