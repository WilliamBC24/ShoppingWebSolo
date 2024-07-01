<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Nav</title>
        <link rel="stylesheet" href="nav.css">
        <script src="nav.js"></script>
    </head>
    <body>
    <c:if test="${empty loggedinuser}">
        <div class="promote1">
            <p>Sign up and get 0% off to your first order</p>
            <a href="./nav.html">Sign Up Now</a>
            <button class="remove1" onclick="remove()">X</button>
        </div>
    </c:if>
    <div class="container1">
        <div class="nav1">
            <div class="logo1">
                <img src="${pageContext.request.contextPath}/JSP/Nav/logo.svg" class="logo1">
            </div>
            <div class="menu1">
                <a href="./nav.html" class="one1">One</a>
                <a href="./nav.html" class="two1">Two</a>
                <a href="./nav.html" class="three1">Three</a>
                <a href="./nav.html" class="four1">Four</a>
            </div>
            <div class="srch1">
                <input type="text" class="search1">
            </div>
            <div class="cart1">
                <img src="${pageContext.request.contextPath}/JSP/Nav/cart.svg" class="crt1">
            </div>
            <!-- <div class="human">
                <nav>
                    <ul>
                        <li><a href="">Profile</a></li>
                        <li><a href="">Log out</a></li>
                    </ul>
                    <img class="avatar" src="avatar.png">
                    
                    <div class="wrap">
                        <div class="menu">
                            <img class="avatar" src="avatar.png">
                            <hr>
                            <a href="" class="link">
                                <p>Profile</p>
                                <span>></span>
                            </a>
                            <a href="" class="link">
                                <p>Log out</p>
                                <span>></span>
                            </a>
                        </div>
                    </div>
                </nav>
            </div> -->
        </div>
    </div>
</body>
</html>