<%-- 
    Document   : index
    Created on : May 8, 2024, 9:49:09 PM
    Author     : SonBui
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="./JSP/Nav/nav.css">
        <link rel="stylesheet" href="index.css">
    </head>
    <body>
        <%@include file="JSP/Nav/nav.jsp"%>
        <script src="./JSP/Nav/nav.js"></script>
        <c:if test="${empty loggedinuser}">
            <a class="button" href="JSP/Login/login.jsp">Go to login</a>
            <a class="button" href="JSP/Register/register.jsp">Go to register</a>
            <a class="button" href="ProfileManagement">Go to dashboard</a>
        </c:if>
        <c:if test="${not empty loggedinuser}">
            <a class="button" href="LogOut">Log out</a>
            <a class="button" href="ProfileManagement">Go to dashboard</a>
        </c:if>
            <a class="button" href="JSP/VNPay/index.jsp">Go to payment</a>
        <c:if test="${not empty loginstatus}">
            <p>${loginstatus}</p>
        </c:if>
    </body>
</html>
