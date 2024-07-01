<!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Forgot/forgot.css">
    </head>

    <body>
        <div class="smallbox">
            <div class="container">
                <div class="form">
                    <div class="sign">
                        <form action="${pageContext.request.contextPath}/Forgot" method="post">
                            <h2 class="title">Enter your email</h2>
                            <div class="input">
                                <i class="fas fa-user"></i>
                                <input type="text" name="email" placeholder="Email" required>
                            </div>
                            <input type="submit" name="action" value="Continue" class="button">
                            <p class="error" style="color: red;">
                                <c:if test="${!empty nouser}">
                                    ${nouser}
                                </c:if>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/JSP/Forgot/forgot.js"></script>
    </body>

    </html>