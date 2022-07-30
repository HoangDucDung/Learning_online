<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./css/slide-management.css">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="table-paging">
            <ul class="pagging-list">
                <li class="previous">
                    <a href="${request}page=${page-1==0?"1":page-1}">Previous</a>
                </li>
                <c:forEach begin="1" end="${totalpage}" var="p">
                    <li ${p==page?"class='pagging-active'":""}><a href="${request}page=${p}">${p}</a></li>
                    </c:forEach>
                <li class="next">
                    <a href="${request}page=${page+1>totalpage?totalpage:page+1}">Next</a>
                </li>
            </ul>
        </div>
    </body>
</html>
