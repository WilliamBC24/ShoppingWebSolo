<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Forgot/InputCode/Reset/reset.css">
    </head>

    <body>
        <div class="smallbox">
            <div class="container">
                <div class="form">
                    <div class="sign">
                        <form action="${pageContext.request.contextPath}/Forgot" method="post">
                            <h2 class="title">Input your new password</h2>
                            <div class="input">
                                <i class="fas fa-key"></i>
                                <input type="text" name="password" placeholder="Password" class="password" required>
                            </div>
                            <div class="input">
                                <i class="fas fa-check"></i>
                                <input type="text" name="confirm" placeholder="Confirm password" class="confirm" required>
                            </div>
                            <input type="submit" name="action" value="Reset" class="button">
                            <p class="error" style="color: red;">
                                <c:if test="${!empty errorz}">
                                    ${errorz}
                                </c:if>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/JSP/Forgot/InputCode/Reset/reset.js"></script>
    </body>
    </html>