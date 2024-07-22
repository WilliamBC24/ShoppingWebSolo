<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Login/login.css">
    </head>

    <body>
        <div class="smallbox">
            <div class="container">
                <div class="form">
                    <div class="sign">
                        <form action="${pageContext.request.contextPath}/Login" method="post" class="signin">
                            <h2 class="title">Sign in</h2>
                            <div class="input">
                                <i class="fas fa-user"></i>
                                <input type="text" name="username" placeholder="Username" required class="logUser">
                            </div>
                            <div class="input">
                                <i class="fas fa-lock"></i>
                                <input type="password" name="password" placeholder="Password" required class="logPass">
                            </div>
                            <input type="submit" id="loginbutton" name="action" value="Login" class="button logButton">
                            <p class="error">
                                <c:if test="${!empty loginstatus}">
                                    ${loginstatus}
                                </c:if>
                            </p>
                            <p class="social">Or continue with social platform accounts</p>
                            <div class="socialmedia">
                                <!--                            <a href="#" class="socialicon">
                                                                <i class="fab fa-facebook-f"></i>
                                                            </a>-->
                                <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/stbcStore/OAuth&response_type=code&client_id=906858915468-a8dshf351e88vccqkv3qdf2m3kt5pme1.apps.googleusercontent.com&approval_prompt=force" class="socialicon">
                                    <i class="fab fa-google"></i>
                                </a>
                                <!--                            <a href="#" class="socialicon">
                                                                <i class="fab fa-linkedin-in"></i>
                                                            </a>
                                                            <a href="#" class="socialicon">
                                                                <i class="fab fa-twitter"></i>
                                                            </a>-->
                            </div>
                            <br>
                            <a href="${pageContext.request.contextPath}/JSP/Forgot/forgot.jsp">Forgot your password?</a>
                        </form>

                        <form action="${pageContext.request.contextPath}/Login" method="post" class="signup">
                            <h2 class="title">Sign up</h2>
                            <div class="input reguser">
                                <i class="fas fa-user"></i>
                                <input type="text" placeholder="Username" name="username" required>
                            </div>
                            <div class="input regmail">
                                <i class="fas fa-envelope"></i>
                                <input type="email" placeholder="Email" name="email" required>
                            </div>
                            <div class="input regpass">
                                <i class="fas fa-lock"></i>
                                <input type="password" placeholder="Password" name="password" required>
                            </div>
                            <div class="input fade regfirst">
                                <i class="fas fa-user"></i>
                                <input type="text" placeholder="First name" name="firstname" required>
                            </div>
                            <div class="input fade reglast">
                                <i class="fas fa-user"></i>
                                <input type="text" placeholder="Last name" name="lastname" required>
                            </div>
                            <div class="input fade reggen">
                                <i class="fas fa-mars"></i>
                                <select name="gender" id="gender" required>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                    <option value="Other">Other</option>
                                </select>
                            </div>
                            <div class="input fade regphone">
                                <i class="fas fa-phone"></i>
                                <input type="tel" placeholder="Phone number" name="phonenumber" required>
                            </div>
                            <input type="button" value="Continue" class="button regcont">
                            <input type="submit" name="action" value="Register" class="button fade regsub">
                            <input type="button" value="Go back" class=" fade regback">
                            <p class="error" style="color: red;">
                                <c:if test="${!empty error}">
                                    ${error}
                                </c:if>
                            </p>
                        </form>
                    </div>
                </div>
                <div class="panels">
                    <div class="panel left">
                        <div class="content">
                            <h3>Don't have an account yet?</h3>
                            <p>Sign up to join us!</p>
                            <button class="button" id="signupbutton">Sign up</button>
                        </div>
                        <img src="${pageContext.request.contextPath}/JSP/Login/login.svg" class="image">
                    </div>

                    <div class="panel right">
                        <div class="content">
                            <h3>Already have an account?</h3>
                            <p>Log in!</p>
                            <button class="button" id="signinbutton">Sign in</button>
                        </div>
                        <img src="${pageContext.request.contextPath}/JSP/Login/register.svg" class="image">
                    </div>
                </div>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/JSP/Login/login.js"></script>
    </body>

</html>