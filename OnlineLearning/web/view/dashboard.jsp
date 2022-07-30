<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Dashboard</title>

        <!-- Bootstrap CSS -->
        <link href="../node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="../node_modules/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--JQuery-->
        <script src="../node_modules/jquery/dist/jquery.min.js"></script>

    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <!--Sidebar-->
                <div class="col-1 col-sm-2 bg-dark p-0 collapse-horizontal overflow-auto vh-100" id="navbarTogglerDemo01">
                    <jsp:include page="sidenav.jsp?page=Dashboard"/>
                </div>

                <!--Page Content-->
                <div class="col p-0 vh-100 overflow-auto bg-light">
                    <jsp:include page="navbar-header.jsp?page=Dashboard"/>

                    <div class="d-flex justify-content-end row px-3">
                        <div class="col-sm-4">
                            <form method="get" action="./dashboard">
                                <div class="input-group mb-3 input-group-sm">
                                    <span class="input-group-text">From</span>
                                    <input name="startDate" type="date" class="form-control" value="${startDate}">

                                    <span class="input-group-text">To</span>
                                    <input name="endDate" type="date" class="form-control" value="${endDate}">
                                    <button type="submit" value="Search" class="btn btn-info">
                                        <i class="fa-solid fa-magnifying-glass"></i>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="container">
                        <div class="row">
                            <!--New Account Card-->
                            <div class="col-6 col-lg-3 mb-3">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">Accounts</h5>
                                        <div class="row">
                                            <div class="col-9">
                                                <h3 class="mb-0 mr-3 font-weight-bold">+${amountAccount} </h3>
                                            </div>
                                            <div class="col-1 fs-4">
                                                <i class="fa-solid fa-user"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-footer text-white bg-primary">
                                        ${timeLine}
                                    </div>
                                </div>
                            </div>

                            <!--Revenue Card-->
                            <div class="col-6 col-lg-3 mb-3">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">Revenue</h5>
                                        <div class="d-flex align-items-center">
                                            <h2 class="mb-0 mr-3 font-weight-bold">
                                                <fmt:setLocale value = "en_US"/>
                                                <fmt:formatNumber value="${revenue}" type="currency" />
                                            </h2>
                                            <span class="font-size-md font-weight-bold text-success ms-2">
                                                <i class="fa-solid fa-arrow-trend-up"></i>
                                            </span>

                                        </div>
                                    </div>
                                    <div class="card-footer bg-primary text-white">
                                        ${timeLine}
                                    </div>
                                </div>
                            </div>

                            <!--Amount Visit Page-->
                            <div class="col-6 col-lg-3 mb-3">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">Visit Page</h5>

                                        <div class="row">
                                            <div class="col-9">
                                                <h3 class="mb-0 font-weight-bold">${visitPage}</h3>
                                            </div>
                                            <div class="col-1 fs-4">
                                                <i class="fa-solid fa-eye"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-footer text-white bg-primary">
                                        ${timeLine}
                                    </div>
                                </div>
                            </div>

                            <!--Total Revenue Card-->
                            <div class="col-6 col-lg-3 mb-1 mb-3">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">Total Earning</h5>

                                        <div class="row">
                                            <div class="col-9">
                                                <h3 class="mb-0 mr-3 font-weight-bold">
                                                    <fmt:setLocale value="en_US"/>
                                                    <fmt:formatNumber value="${totalEarning}" type="currency" />
                                                </h3>
                                            </div>
                                            <div class="col-1 fs-4">
                                                <i class="fa-solid fa-chart-simple"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-footer text-white bg-primary">
                                        Lastest
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row p-3">
                            <!--Revenue Chart-->
                            <h5 class="card-title">Revenue Statistics</h5>
                            <div class="col col-sm-5">
                                <div class="input-group mb-3 input-group-sm">
                                    <span class="input-group-text">From</span>
                                    <input id="revenueStartDate" type="date" class="form-control" value="${startDate}">

                                    <span class="input-group-text">To</span>
                                    <input id="revenueEndDate" type="date" class="form-control" value="${endDate}">
                                </div>
                            </div>
                            <div class="col pt-1">
                                <p class="fw-bold align-text-bottom">
                                    Total: $<span class="" id="revenueInTime"></span>
                                </p>
                            </div>
                            <!--Revenue Chart-->
                            <div class="card p-4">
                                <canvas id="revenueChart"></canvas>
                            </div>
                        </div>

                        <!--Revenue Base On Subject Chart-->
                        <div class="row p-3">
                            <div class="col-sm-6 col-md-5">
                                <div class="input-group mb-3 input-group-sm">
                                    <span class="input-group-text">From</span>
                                    <input type="date" id="subjectRevenueStartDate" min="2000-01-01" class="form-control" value="${startDate}">
                                    <span class="input-group-text">To</span>
                                    <input type="date" id="subjectRevenueEndDate" min="2000-01-01" class="form-control" value="${endDate}">
                                </div>
                            </div>

                            <div class="card p-4">
                                <canvas id="subjectRevenueChart"></canvas>
                            </div>
                        </div>

                        <!--Registration Chart-->
                        <div class="row p-3">
                            <div class=" col-md-5">
                                <div class="input-group mb-3 input-group-sm">
                                    <span class="input-group-text">From</span>
                                    <input type="date" id="registrationStartDate" min="2000-01-01" class="form-control" value="${startDate}">
                                    <span class="input-group-text">To</span>
                                    <input type="date" id="registrationEndDate" min="2000-01-01" class="form-control" value="${endDate}">
                                </div>
                            </div>
                            <div class="card p-4">
                                <canvas id="registrationChart"></canvas>
                            </div>
                        </div>

                        <!--Subject And Course Chart-->
                        <div class="row p-3">
                            <div class="col-12 col-md-8">
                                <div class="card">
                                    <canvas id="myChart"></canvas>
                                </div>
                            </div>
                            <div class="col-10 col-md-4">
                                <div class="card p-2">
                                    <canvas id="subjectChart"></canvas>
                                </div>
                            </div>
                        </div>

                        <div class="row mt-3 p-3">
                            <div class="col-md-6 card">
                                <canvas id="subjectEnrollTrend"></canvas>
                            </div>

                            <!--Most Enrolled Courses Table-->
                            <div class="col-md-5 mt-3">
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">
                                            <i class="fa-solid fa-star text-warning"></i>
                                            Most Enrolled Courses
                                        </h4>
                                        <div class="overflow-auto" style="height: 70vh">
                                            <table id="courseEnrollTable" class="table">
                                                <thead>
                                                <th>#Top</th>
                                                <th>Course</th>
                                                <th>Total</th>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${mostEnrolledCourse}" var="c" varStatus="loop">
                                                        <tr>
                                                            <td>
                                                                <span class="${loop.count <= 3 ? "fw-bold" : ""}">
                                                                    ${loop.count}
                                                                </span>
                                                            </td>
                                                            <td>
                                                                ${c.courseName}
                                                            </td>
                                                            <td>${c.numberOfEnroll}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--Chart JS-->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="${pageContext.request.contextPath}/js/statistics.js"></script>
    </body>
</html>