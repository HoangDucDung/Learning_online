<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manage Registration</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <link href="../css/paging.css" rel="stylesheet" />
        <link href="../css/registration.css" rel="stylesheet" />
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Manage Registration"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Manage Registration"/>
                    <div id="search"> 
                        <div class="d-flex justify-content-end">
                            <i class="fa-solid fa-xmark h3" id="close-icon"></i>
                        </div>
                        <div class="margin-bottom-20"></div>
                        <label class="margin-bottom-7">From</label>
                        <div class="d-flex">
                            <input type="date" id="from" class="form-control">
                        </div>
                        <div class="margin-bottom-20"></div>
                        <label class="margin-bottom-7">To</label>
                        <div class="d-flex">
                            <input type="date" id="to" class="form-control">
                        </div>
                        <div class="margin-bottom-20"></div>
                        <label class="margin-bottom-7">Status</label>
                        <select id="status" class="form-select" aria-label="Default select example"  onchange="searchByValue()">
                            <option value="all" id="all">All</option>
                            <option value="active">Active</option>
                            <option value="expired">Expired</option>
                        </select>
                        <div class="margin-bottom-20"></div>
                        <div class="d-flex justify-content-between">
                            <button type="button" class="btn btn-primary" onclick="searchByValue()">
                                <i class="fas fa-search"></i>
                            </button>
                            <button type="button" class="btn btn-danger" onclick="clearValueSearch()">
                                Clear
                            </button>
                        </div>
                    </div>
                    <div id="overlay"></div>
                    <div class="container">
                        <div class="d-flex justify-content-between" style="margin-bottom: 20px">
                            <div class="d-flex justify-content-between" style="width: 90%">
                                <a href="./registration-add" class="btn btn-primary" style="width: 140px">Add</a>

                                <div class="input-group" style="width: auto">
                                    <div class="form-outline">
                                        <input type="search" id="search-title-email" class="form-control" placeholder="Search for email or course"/>
                                    </div>
                                    <button type="button" class="btn btn-primary" onclick="searchByValue()">
                                        <i class="fas fa-search"></i>
                                    </button>
                                </div>
                            </div>
                            <i class="fa-solid fa-filter h3 text-primary" id="filter-icon"></i>

                        </div>
                        <div class="container-table">
                            <div class="table-content">  
                                <table class="table table-striped" id="table">
                                    <thead>
                                        <tr>
                                            <th>ID <i class="fa-solid fa-sort" onclick="sortTable(0)"></i></th>
                                            <th>Email <i class="fa-solid fa-sort" onclick="sortTable(1)"></i></th>
                                            <th>Registration Time <i class="fa-solid fa-sort" onclick="sortTable(2)"></i></th>
                                            <th style="width: 150px">Course <i class="fa-solid fa-sort" onclick="sortTable(3)"></i></th>
                                            <th>Package <i class="fa-solid fa-sort" onclick="sortTable(4)"></i></th>
                                            <th>Total cost <i class="fa-solid fa-sort" onclick="sortTable(5)"></i></th>
                                            <th>Status <i class="fa-solid fa-sort" onclick="sortTable(6)"></i></th>
                                            <th>Valid from <i class="fa-solid fa-sort" onclick="sortTable(7)"></i></th>
                                            <th>Valid to <i class="fa-solid fa-sort" onclick="sortTable(8)"></i></th>
                                            <th>Last updated by <i class="fa-solid fa-sort" onclick="sortTable(9)"></i></th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody id="myTable">
                                        <c:forEach items="${listTrans}" var="trans">
                                            <tr>
                                                <td>${trans.id}</td>
                                                <td>${trans.accountID.email}</td>
                                                <td>${trans.transactionTime}</td>
                                                <td>${trans.courseID.name}</td>
                                                <td>${trans.coursePackageID.name}</td>
                                                <td>${trans.amount}</td>
                                                <c:choose>
                                                    <c:when test="${now <= trans.validTo || trans.validTo == unlimited}">
                                                        <td class="text-success">Active</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="text-danger">Expired</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td>${trans.validFrom}</td>
                                                <c:if test="${trans.validTo == unlimited}">
                                                    <td>Unlimited</td>
                                                </c:if>
                                                <c:if test="${trans.validTo != unlimited}">
                                                    <td>${trans.validTo}</td>
                                                </c:if>
                                                <td>${trans.updatedBySaleID.firstName} ${trans.updatedBySaleID.lastName}</td>    
                                                <td><a href="./registration-detail?id=${trans.id}" class="text-primary">View</a>
                                                    <c:if test="${sessionScope.account.accountID == trans.saleID.accountID}">
                                                        / <span onclick="deleteRegistration(${trans.id}, this)" class="text-danger link-delete">Delete</span>
                                                    </c:if>
                                                    
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <div id="pagination-page">
                                    <div class="pagination">
                                        <ul class="pagination-list">
                                            <li>
                                                <button onclick="pagination(${page-1==0?1:page-1})" class="previous-btn">Previous</button>
                                            </li>
                                            <c:forEach begin="1" end="${totalpage}" var="p">
                                                <li>
                                                    <button onclick="pagination(${p})" ${p==page?"class='paging-active page-num'":"class='page-num'"} >${p}</button>
                                                </li>
                                            </c:forEach>
                                            <li>
                                                <button onclick="pagination(${page+1>totalpage?totalpage:page+1})" class="next-btn" >Next</button>
                                            </li>
                                        </ul>
                                        <input type="hidden" id="page-num" value="${page}">
                                    </div>    
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JavaScript -->    
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
        <script src="../js/table.js"></script>
        <script src="../js/registration.js"></script>
    </body>

</html>