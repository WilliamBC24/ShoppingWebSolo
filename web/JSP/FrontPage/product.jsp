<!DOCTYPE html><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <!-- basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- mobile metas -->
        <!-- site metas -->
        <title>sStore</title>
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="author" content="">
        <!-- bootstrap css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/FrontPage/css/bootstrap.min.css">
        <!-- style css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/FrontPage/css/style.css">
        <!-- fevicon -->
        <link rel="icon" href="${pageContext.request.contextPath}/JSP/FrontPage/images/fevicon.png" type="image/gif" />
        <!-- Scrollbar Custom CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/FrontPage/css/jquery.mCustomScrollbar.min.css">
        <!-- Tweaks for older IEs-->
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.css" media="screen">
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
    </head>
    <!-- body -->
    <body class="main-layout position_head">
        <!-- loader  -->
        <!-- end loader -->
        <!-- header -->
        <header>
            <!-- header inner -->
            <div class="header">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-xl-3 col-lg-3 col-md-3 col-sm-3 col logo_section">
                            <div class="full">
                                <div class="center-desk">
                                    <div class="logo">
                                        <a href="${pageContext.request.contextPath}/Homepage"><img src="${pageContext.request.contextPath}/JSP/FrontPage/images/logo.png" alt="#" /></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-9 col-lg-9 col-md-9 col-sm-9">
                            <nav class="navigation navbar navbar-expand-md navbar-dark ">
                                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample04" aria-controls="navbarsExample04" aria-expanded="false" aria-label="Toggle navigation">
                                    <span class="navbar-toggler-icon"></span>
                                </button>
                                <div class="collapse navbar-collapse" id="navbarsExample04">
                                    <ul class="navbar-nav mr-auto">
                                        <li class="nav-item ">
                                            <a class="nav-link" href="${pageContext.request.contextPath}/Homepage">Home</a>
                                        </li>
                                        <li class="nav-item active">
                                            <a class="nav-link" href="${pageContext.request.contextPath}/ProductListing">Our Products</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="${pageContext.request.contextPath}/PostListing">Our Posts</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="${pageContext.request.contextPath}/JSP/FrontPage/contact.jsp">Contact Us</a>
                                        </li>
                                        <c:if test="${empty loggedinuser}">
                                            <li class="nav-item d_none login_btn">
                                                <a class="nav-link" href="${pageContext.request.contextPath}/Login">Login</a>
                                            </li>
                                            <li class="nav-item d_none">
                                                <a class="nav-link" href="${pageContext.request.contextPath}/JSP/Register/register.jsp">Register</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${not empty sessionScope.loggedinuser}">
                                            <li class="nav-item d_none">
                                                <a class="nav-link" href="#">Cart</a>
                                            </li>
                                            <li class="nav-item d_none">
                                                <a class="nav-link" href="${pageContext.request.contextPath}/LogOut">Logout</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </div>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </header>
        <!-- end header inner -->
        <!-- end header -->
        <!-- Our  Glasses section -->
        <div class="glasses">
            <div class="container">
                <div class="row">
                    <div class="col-md-10 offset-md-1">
                        <div class="titlepage">
                            <h2>Our Products</h2>
                            <p>Classic, Timeless, Modern
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container-fluid">
                <div class="row">
                    <c:forEach var="product" items="${productList}">
                        <div class="col-xl-3 col-lg-3 col-md-6 col-sm-6">
                            <div class="glasses_box">
                                <figure><img style="width:300px;height:300px" src="${product.productImg}" alt="#"/></figure>
                                <p><span class="blu">$</span>${product.priceOut}</p>
                                <h3>${product.title}</h3>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <!-- end Our  Glasses section -->
        <!-- Javascript files-->
        <script src="${pageContext.request.contextPath}/JSP/FrontPage/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/JSP/FrontPage/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/JSP/FrontPage/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/JSP/FrontPage/js/jquery-3.0.0.min.js"></script>
        <!-- sidebar -->
        <script src="${pageContext.request.contextPath}/JSP/FrontPage/js/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="${pageContext.request.contextPath}/JSP/FrontPage/js/custom.js"></script>
    </body>
</html>

