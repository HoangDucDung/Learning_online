<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.Account"%>
<%@page import="model.Gender" %>

<!DOCTYPE html>
<html lang="en">

    <head>

        <jsp:include page="base-view/baseTag.jsp"></jsp:include>

            <link href="css/profile.css" rel="stylesheet" type="text/css"/>

            <title>Profile</title>

        </head>

        <body>

        <jsp:include page="base-view/headerUser.jsp"></jsp:include>

            <div id="container">
                <form action="profile" method="POST" onsubmit="return submitForm()" enctype="multipart/form-data">
                    <div id="table-header">
                        <span>My profile</span>
                    </div>

                    <div>
                        <table>                   

                        <c:if test="${isNoti != null}">
                            <tr>
                                <td colspan="2">
                                    <div id="notification">
                                        <span>Profile has been changed successfully!</span>
                                    </div>
                                </td>
                            </tr>
                        </c:if>
                        <tr>
                            <td>First name</td>
                            <td><input type="text" id="fname" name="firstName" value="${sessionScope.account.firstName}" required/></td>
                        </tr>
                        <tr><td colspan="2" id="error-fname"></td></tr>

                        <tr>
                            <td>Last name</td>
                            <td><input type="text" id="lname" name="lastName" value="${sessionScope.account.lastName}" required/></td>
                        </tr>
                        <tr><td colspan="2" id="error-lname" ></td></tr>

                        <tr>
                            <% Account account = (Account) request.getSession().getAttribute("account");%>
                            <td>Gender</td>                       
                            <td>
                                <input id="male" type="radio" name="gender" 
                                       <%= account.getGender().toString().equalsIgnoreCase("male") ? "checked" : ""%> value="male"                                      
                                       />
                                <label for="male">Male</label>
                                <input id="female" type="radio" name="gender" 
                                       <%= account.getGender().toString().equalsIgnoreCase("female") ? "checked" : ""%> value="female" 
                                       />
                                <label for="female">Female</label>
                            </td>
                        </tr>
                        <tr><td colspan="2"></td></tr>

                        <tr>
                            <td>Phone</td>
                            <td><input type="text" name="phone" value="${sessionScope.account.phone}" required/></td>
                        </tr>
                        <tr><td colspan="2" ></td></tr>

                        <tr>
                            <td>Email</td>
                            <td><input type="text" name="email" value="${sessionScope.account.email}" disabled="disabled"/></td>
                        </tr>
                        <tr><td colspan="2"></td></tr>

                        <tr>
                            <td>Password</td>
                            <td><a href="change-password">Change password</a></td>
                        </tr>
                        <tr><td></td></tr>

                        <tr>
                            <td>Address</td>
                            <td><input type="text" name="address" value="${sessionScope.account.address}" /></td>
                        </tr>
                        <tr><td colspan="2"></td></tr>

                        <tr>
                            <td>Avatar</td>
                            <td><input type="file" name="photo" value="" accept="image/*"/></td>
                        </tr>

                        <tr>
                            <td colspan="2"><input id="submit-btn" type="submit" value="Save" /></td>
                        </tr>
                    </table>
                </div>
            </form>

            <div id="avatar">
                <img src="img/${account.profilePictureUrl}" alt="Avatar" width="250" height="250">
            </div>  
        </div>

        <jsp:include page="base-view/footerUser.jsp"></jsp:include>


    </body>

</html>