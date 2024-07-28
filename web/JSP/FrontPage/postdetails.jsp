<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/FrontPage/css/postdetails.css">
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
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
</head>
<body class="main-layout position_head">
    <div class="header">
        <jsp:include page="navbar.jsp" />
    </div>

    <div class="containerx">
        <h1>${post.title}</h1>
        <div class="product-detailsx">
                <img style="width: 400px;height: 400px;" src="${post.postImg}" alt="Product Image">
            <div class="product-infox">
                <div class="product-descriptionx">
                    <p>${post.detail}</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>