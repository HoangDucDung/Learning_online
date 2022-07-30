<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Manage Setting</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!--Font Awesome-->
        <script src="https://kit.fontawesome.com/7b806b5ab9.js" crossorigin="anonymous"></script>
        <!--Jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
        <script src="../js/setting.js" type="text/javascript"></script>   
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css">
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-2 min-vh-100 bg-dark p-0">
                    <jsp:include page="sidenav.jsp?page=Setting"/>
                </div>
                <div class="col-sm-10 p-0">
                    <jsp:include page="navbar-header.jsp?page=Setting"/>

                    <div class="container">
                        <div class="container-table">
                            <div class="table-content">
                                <div class="search d-flex justify-content-between" style="margin-bottom: 20px;">
                                    <a class="btn btn-primary" id="add-blog" href="setting-insert" ><i class="fa-solid fa-plus"></i> Add Setting</a>

                                    <form action="setting" method="GET" class="d-flex"> 
                                        <select name="cid" class="form-select form-select-sm" style="margin-right: 10px">
                                            <option value="-1" >Select Type</option>
                                            <c:forEach items="${allSetting_Type}" var="ctype" >
                                                <option value="${ctype.type}" ${cid == ctype.type ? "selected" : ""}>${ctype.type}</option>
                                            </c:forEach>
                                        </select>
                                        <select name="status" class="form-select form-select-sm" style="margin-right: 10px">
                                            <option value="-1" >Select Status</option>
                                            <option value="false" ${ status == "false" ? "selected" : ""}>Deactive</option>
                                            <option value="true" ${ status == "true" ? "selected" : ""}>Active</option>                                       
                                        </select>

                                        <button type="submit" class="btn btn-primary">Filter</button>
                                    </form>  

                                </div>


                                <table class="table table-striped mt-5" id="table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th class="width350">TYPE</th>
                                            <th>VALUE</th>
                                            <th>ORDER</th>
                                            <th>STATUS</th>
                                            <th>ACTION</th>
                                        </tr>
                                    </thead>
                                    <tbody id="myTable">
                                        <c:forEach items="${listSetting}" var="setting">
                                            <tr>
                                                <td>${setting.id}</td>
                                                <td>${setting.type}</td>
                                                <td>${setting.name}</td>
                                                <td>${setting.order}</td>
                                                <td>${setting.status ? 'Active' : 'Deactive'}</td>
                                                <td><a class="text-primary" href="setting-detail?SettingID=${setting.settingID}">Edit</a> / <a class="text-danger" href="#" onclick="deleteSetting(${setting.settingID},${setting.id}, '${setting.type}')">Delete</a></td>
                                            </tr>
                                        </c:forEach>    
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Bootstrap JavaScript -->    
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
            <script>
                $(document).ready(function () {
                    $('#table').DataTable();
                });
            </script>
    </body>

</html>