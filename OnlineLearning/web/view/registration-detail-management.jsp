<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Detail</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <link href="../css/slide-view.css" rel="stylesheet" type="text/css"/>

    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Registration"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Registration Detail"/>

                    <div class="container">
                        <div class="container-table post">
                            <a class="back" href="./registration"><i class="fa-solid fa-angle-left"></i>Back</a>
                            <div class="form-submit">
                                <h4 class="title">Subject</h4>
                                <span class="input-box">${trans.courseID.name}</span>
                                <h4 class="title">Package</h4>
                                <span class="input-box">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Package</th>
                                                <th>Price</th>
                                                <th>Sale price</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>${trans.coursePackageID.name}</td>
                                                <td style="text-decoration: line-through">${trans.coursePackageID.listPrice}</td>
                                                <td>${trans.coursePackageID.salePrice}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </span>
                                <h4 class="title">Full name</h4>
                                <span class="input-box">${trans.accountID.firstName} ${trans.accountID.lastName}</span>
                                <h4 class="title">Gender</h4>
                                <span class="input-box">${trans.accountID.gender}</span>
                                <h4 class="title">Email</h4>
                                <span class="input-box">${trans.accountID.email}</span>
                                <h4 class="title">Mobile</h4>
                                <span class="input-box">${trans.accountID.phone}</span>
                                <h4 class="title">Registration time</h4>
                                <span class="input-box">${trans.transactionTime}</span>
                                <h4 class="title">Sale</h4>
                                <span class="input-box">${trans.saleID.firstName} ${trans.saleID.lastName}</span>
                                <h4 class="title">Status</h4>
                                <c:choose>
                                    <c:when test="${now <= trans.validTo || trans.validTo == unlimited}">
                                        <span class="text-success input-box">Active</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-danger input-box">Expired</span>
                                    </c:otherwise>
                                </c:choose>
                                <h4 class="title">Valid from</h4>
                                <span class="input-box">${trans.validFrom}</span>
                                <h4 class="title">Valid to</h4>
                                <c:if test="${trans.validTo == unlimited}">
                                    <span class="input-box">Unlimited</span>
                                </c:if>
                                <c:if test="${trans.validTo != unlimited}">
                                    <span class="input-box">${trans.validTo}</span>
                                </c:if>
                                <h4 class="title">Note</h4>
                                <span class="input-box">${trans.note}</span>
                                <c:if test="${sessionScope.account.accountID == trans.saleID.accountID}">
                                    <a href="./registration-add?id=${trans.id}" class="btn btn-primary">Edit</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
    </body>

</html>
