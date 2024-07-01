<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/VerifyAccount/verify.css">
    </head>

    <body>
        <div class="smallbox">
            <div class="container">
                <div class="form">
                    <div class="sign">
                        <form>
                            <h2 class="title">We sent the verification link to your email</h2>
                            <a href="${pageContext.request.contextPath}/JSP/Dashboard/profile.jsp" class="button">Back to profile</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/JSP/VerifyAccount/verify.js"></script>
    </body>

</html>