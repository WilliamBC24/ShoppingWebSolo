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
    <body class="main-layout">
        <header>
            <!-- header inner -->
            <div class="header">
                <jsp:include page="navbar.jsp" />
            </div>
        </header>
        <section class="banner_main">
            <div id="banner1" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#banner1" data-slide-to="0" class="active"></li>
                    <li data-target="#banner1" data-slide-to="1"></li>
                    <li data-target="#banner1" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                    <c:forEach var="banner" items="${banners}" varStatus="status">
                        <div class="carousel-item ${status.first ? 'active' : ''}">
                            <div class="container">
                                <div class="carousel-caption">
                                    <div class="text-bg">
                                        <h1> <span>Popular <br></span>Now</h1>
                                        <figure><img style="width: 500px;height:500px" src="${banner.productImg}" alt="#"/></figure>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <a class="carousel-control-prev" href="#banner1" role="button" data-slide="prev">
                    <i class="fa fa-arrow-left" aria-hidden="true"></i>
                </a>
                <a class="carousel-control-next" href="#banner1" role="button" data-slide="next">
                    <i class="fa fa-arrow-right" aria-hidden="true"></i>
                </a>
            </div>
        </section>
        <!-- end banner -->

        <!-- Our  Glasses section -->
        <div class="glasses">
            <div class="container">
                <div class="row">
                    <div class="col-md-10 offset-md-1">
                        <div class="titlepage">
                            <h2>Popular products</h2>
                            <p>Most favoured by customers</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container-fluid">
                <div class="row">
                    <c:forEach var="product" items="${products}">
                        <div class="col-xl-3 col-lg-3 col-md-6 col-sm-6">
                            <div class="glasses_box" onclick="window.location.href='http://localhost:8080/stbcStore/ProductListing?product=${product.productID}&action=details'">
                                <figure><img style="width:400px;height:400px" src="${product.productImg}" alt="#"/></figure>
                                <h3><span class="blu">$</span>${product.priceOut}</h3>
                                <p>${product.title}</p>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="col-md-12">
                        <a class="read_more" href="${pageContext.request.contextPath}/ProductListing">Read More</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="glasses">
            <div class="container">
                <div class="row">
                    <div class="col-md-10 offset-md-1">
                        <div class="titlepage">
                            <h2>New posts</h2>
                            <p>Our latest posts</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container-fluid">
                <div class="row">
                    <c:forEach var="post" items="${posts}">
                        <div class="col-xl-3 col-lg-3 col-md-6 col-sm-6">
                        <div class="glasses_box">
                            <figure><img style="width:400px;height:400px" src="${post.postImg}" alt="#"/></figure>
                            <p>${post.title}</p>
                        </div>
                    </div>
                    </c:forEach>
                    
                    <div class="col-md-12">
                        <a class="read_more" href="${pageContext.request.contextPath}/PostListing">Read More</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- end contact section -->
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

