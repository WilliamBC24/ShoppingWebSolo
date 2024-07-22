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
                
                    <jsp:include page="navbar.jsp" />
            </div>
        </header>
        <!-- end header inner -->
        <!-- end header -->
        <!-- contact section -->
        <div id="contact" class="contact">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <form class="main_formz" action="${pageContext.request.contextPath}/CustomerSupport" method="post">
                            <div class="row">
                                <div class="col-md-12 ">
                                    <h3>Contact Us</h3>
                                </div>
                                <div class="col-md-12 ">
                                    <input class="contactus" placeholder="Name" type="type" name="name"> 
                                </div>
                                <div class="col-md-12">
                                    <input class="contactus" placeholder="Phone Number" type="type" name="phone" maxlength="12"> 
                                </div>
                                <div class="col-md-12">
                                    <input class="contactus" placeholder="Email" type="type" name="email">                          
                                </div>
                                <div class="col-md-12">
                                    <input class="contactusmess" placeholder="Message" type="type" name="message">
                                </div>
                                <div class="col-md-12">
                                    <button type="submit" class="send_btnz">Send</button>
                                </div>
                            </div>
                        </form>
                    </div>
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