<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Add</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/js/standalone/selectize.min.js" integrity="sha256-+C0A5Ilqmu4QcSPxrlGpaZxJ04VjsRjKu+G82kl5UJk=" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/css/selectize.bootstrap3.min.css" integrity="sha256-ze/OEYGcFbPRmvCnrSeKbRTtjG4vGLHXgOqsyLFTRjg=" crossorigin="anonymous" />

        <link rel="stylesheet" href="../css/slide-view.css" />
        <style>
            .selectize-input.items {
                padding: 8px 12px;
            }
        </style>
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

                            <form class="form-submit" action="./registration-add" method="POST" onsubmit="return submitForm()">
                                <c:choose>
                                    <c:when test="${th != null}">
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend" style="width: 10%">
                                                <label class="input-group-text" >Subject</label>
                                            </div>
                                            <select name="subject" style="width: 90%" placeholder="Select a subject ..." class="custom-select select-search" onchange="changeCourse()" id="select-subject">
                                                <option value="">Select a subject ...</option>
                                                <c:forEach items="${courses}" var="c">
                                                    <option value="${c.courseId}" ${c.courseId == th.courseID.courseId ? "selected" : ""}>${c.name}</option>
                                                </c:forEach>                                  
                                            </select>
                                        </div>
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend" style="width: 10%">
                                                <label class="input-group-text" >Email</label>
                                            </div>
                                            <select id="select-email" name="email" style="width: 90%" placeholder="Select a email ..." class="custom-select select-search" >
                                                <option value="">Select a email ...</option>
                                                <c:forEach items="${accounts}" var="a">
                                                    <option value="${a.accountID}" ${a.accountID == th.accountID.accountID ? "selected" : ""}>${a.email}</option>                                         
                                                </c:forEach>                                  
                                            </select>
                                        </div> 
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend" style="width: 10%">
                                                <label class="input-group-text" >Package</label>
                                            </div>
                                            <select name="package" class="custom-select" style="width: 90%" id="select-package" onchange="changePackageEdit()">
                                                <c:forEach items="${packages}" var="p">
                                                    <option value="${p.priceId}" ${p.priceId == th.coursePackageID.priceId ? "selected" : ""}>${p.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group mb-3">
                                            <table class="table table-striped" style="width: 50%">
                                                <thead>
                                                    <tr>
                                                        <th>Price</th>
                                                        <th>Sale Price</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <c:forEach items="${packages}" var="p">
                                                            <c:if test="${p.priceId == th.coursePackageID.priceId}">
                                                                <td style="text-decoration: line-through; height: 41px" id="price">${p.listPrice}</td>
                                                                <td id="sale-price" style="height: 41px">${p.salePrice}</td>
                                                            </c:if>
                                                        </c:forEach>
                                                    </tr>     
                                                </tbody>
                                            </table>
                                        </div>

                                        <div class="form-group mb-3">
                                            <label for="notes">Notes</label>
                                            <textarea name="note" class="form-control" id="notes" rows="3">${th.note}</textarea>
                                        </div> 
                                            <input type="hidden" value="${th.id}" name="transId">
                                        <div class="form-group mb-3">
                                            <button class="btn btn-primary" name="button" value="Save">Save</button>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="input-group mb-3">
                                            <div role="alert" id="alert" style="width: 100%">

                                            </div>
                                        </div>
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend" style="width: 10%">
                                                <label class="input-group-text" >Subject</label>
                                            </div>
                                            <select name="subject" style="width: 90%" placeholder="Select a subject ..." class="custom-select select-search" onchange="changeCourse()" id="select-subject">
                                                <option value="">Select a subject ...</option>
                                                <c:forEach items="${courses}" var="c">
                                                    <option value="${c.courseId}">${c.name}</option>
                                                </c:forEach>                                  
                                            </select>
                                        </div>
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend" style="width: 10%">
                                                <label class="input-group-text" >Email</label>
                                            </div>
                                            <select id="select-email" name="email" style="width: 90%" placeholder="Select a email ..." class="custom-select select-search" >
                                                <option value="">Select a email ...</option>
                                                <c:forEach items="${accounts}" var="a">
                                                    <option value="${a.accountID}">${a.email}</option>                                         
                                                </c:forEach>                                  
                                            </select>
                                        </div>
                                        <c:forEach items="${accounts}" var="a">
                                            <input type="hidden" value="${a.balance}" id="acc-${a.accountID}">                                         
                                        </c:forEach>   
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend" style="width: 10%">
                                                <label class="input-group-text" >Package</label>
                                            </div>
                                            <select name="package" class="custom-select" style="width: 90%" id="select-package" onchange="changePackage()">
                                                <option selected value="">Selected package...</option>
                                            </select>
                                        </div>
                                        <div class="form-group mb-3">
                                            <table class="table table-striped" style="width: 50%">
                                                <thead>
                                                    <tr>
                                                        <th>Price</th>
                                                        <th>Sale Price</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td style="text-decoration: line-through; height: 41px" id="price"></td>
                                                        <td id="sale-price" style="height: 41px"></td>
                                                    </tr>     
                                                </tbody>
                                            </table>
                                        </div>

                                        <div class="form-group mb-3">
                                            <label for="notes">Notes</label>
                                            <textarea name="note" class="form-control" id="notes" rows="3"></textarea>
                                        </div>                            
                                        <div class="form-group mb-3">
                                            <button class="btn btn-primary" name="button" value="Register">Register</button>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
        <script src="../js/registration-add.js"></script>

    </body>

</html>
