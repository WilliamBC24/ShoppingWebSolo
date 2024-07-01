<!DOCTYPE html>
<html lang="en">
    <%@ page import="ObjectModel.User" %>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/user.css">
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <section class="content">
                    <jsp:include page="overview.jsp" />
                    <div class="form-section">
                        <h2>User</h2>
                        <p>The title i guess</p>
                        <c:if test="${not empty userError}">
                            <div class="form-alert">

                                <p>Display when theres an alert</p>

                            </div>
                        </c:if>
                        <table>
                            <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>Email</th>
                                    <th>Phone number</th>
                                    <th>Access Level</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${userList}">
                                    <tr>
                                        <td>${user.username}</td>
                                        <td>${user.email}</td>
                                        <td>${user.phoneNumber}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.accessLevel == 1}">
                                                    Customer
                                                </c:when>
                                                <c:when test="${user.accessLevel == 2}">
                                                    Staff
                                                </c:when>
                                                <c:when test="${user.accessLevel == 3}">
                                                    Admin
                                                </c:when>
                                                <c:otherwise>
                                                    unknown
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <form action="UserManagement" method="post">
                                                <input type="hidden" name="username" value="${user.userID}">
                                                <button type="submit" name="action" value="edit">Edit</button>
                                            </form>
                                            <form action="UserManagement" method="post">
                                                <input type="hidden" name="username" value="${user.userID}">
                                                <button type="submit" name="action" value="delete">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="pagination">
                            <button><a href="http://localhost:8080/stbcStore/UserManagement?page-=1"><i class="fas fa-angle-left"></i></a></button>
                            <button>1</button>
                            <button><a href="http://localhost:8080/stbcStore/UserManagement?page+=1"><i class="fas fa-angle-right"></i></a></button>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </body>
</html>
