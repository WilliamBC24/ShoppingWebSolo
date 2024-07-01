<!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/OAuthInfo/oauth.css">
    </head>

    <body>
        <div class="smallbox">
            <div class="container">
                <div class="form">
                    <div class="sign">
                        <form action="${pageContext.request.contextPath}/OAuthLogin" method="post">
                            <h2 class="title">Fill the form</h2>
                            <div class="input">
                                <i class="fas fa-user"></i>
                                <input type="text" name="username" placeholder="Username" class="username" required>
                            </div>
                            <div class="input">
                                <i class="fas fa-user"></i>
                                <input type="text" name="firstname" placeholder="First Name" class="first" required>
                            </div>
                            <div class="input">
                                <i class="fas fa-user"></i>
                                <input type="text" name="lastname" placeholder="Last Name" class="last" required>
                            </div>
                            <div class="input">
                                <i class="fas fa-phone"></i>
                                <input type="text" name="phonenumber" placeholder="Phone Number" class="phone" required>
                            </div>
                            <div class="input fade reggen">
                                <i class="fas fa-mars"></i>
                                <select name="gender" id="gender" required>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                    <option value="Other">Other</option>
                                </select>
                            </div>
                            <p class="error">
                                <c:if test="${!empty oauthstatus}">
                                    ${oauthstatus}
                                </c:if>
                            </p>
                            <input type="submit" value="Continue" class="button">
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/JSP/OAuthInfo/oauth.js"></script>
    </body>

    </html>
  