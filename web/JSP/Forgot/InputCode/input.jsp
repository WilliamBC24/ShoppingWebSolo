<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Forgot/InputCode/input.css">
    </head>

    <body>
        <div class="smallbox">
            <div class="container">
                <div class="form">
                    <div class="sign">
                        <form action="${pageContext.request.contextPath}/Forgot" method="post">
                            <h2 class="title">Enter the code we sent to your email</h2>
                            <div class="input">
                                <i class="fas fa-key"></i>
                                <input type="text" name="code" placeholder="Code" class="code" required>
                            </div>
                            <p>Didn't receive the code? <a href="${pageContext.request.contextPath}/ResendCode"
                                    class="link">Click to resend</a>
                                <span class="countdown"></span>
                            </p>
                            <input type="submit" name="action" value="Submit" class="button">
                            <p class="error" style="color: red;">
                                <c:if test="${!empty error}">
                                    ${error}
                                </c:if>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/JSP/Forgot/InputCode/input.js"></script>
    </body>

    </html>