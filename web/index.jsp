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
       <script type="text/javascript">
        window.location.href = "${pageContext.request.contextPath}/JSP/FrontPage/index.jsp";
    </script>
    </body>
</html>
