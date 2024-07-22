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
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
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
                                <div class="glasses_box" onclick="window.location.href='http://localhost:8080/stbcStore/ProductListing?product=${product.productID}&action=details'">
                                    <figure><img style="width:300px;height:300px" src="${product.productImg}" alt="#"/></figure>
                                    <p><span class="blu">$</span>${product.priceOut}</p>
                                    <h3>${product.title}</h3>
                                    <form action="${pageContext.request.contextPath}/ProductListing">
                                        <input type="hidden" name="productID" value="${product.productID}">
                                        <button type="submit" name="action" value="add"  style="background:none;">
                                            <img src="${pageContext.request.contextPath}/JSP/FrontPage/images/add.svg" width="30px" height="50px" alt="add to cart">
                                        </button>
                                    </form>
                                </div>
                            </div>
                    </c:forEach>
                </div>
                <div class="pagination">
                    <c:if test="${currentPage != 1}">
                        <button onclick="window.location.href = 'http://localhost:8080/stbcStore/ProductListing?page=${currentPage-1}'">
                            <i class="fas fa-angle-left"></i>
                        </button>
                    </c:if>
                    <c:if test="${currentPage == 1}">
                        <button>
                            <i class="fas fa-angle-left"></i>
                        </button>
                    </c:if>
                    <button>${currentPage}</button>
                    <c:if test="${currentPage != totalPages}">
                        <button onclick="window.location.href = 'http://localhost:8080/stbcStore/ProductListing?page=${currentPage+1}'">
                            <i class="fas fa-angle-right"></i>
                        </button>
                    </c:if>
                    <c:if test="${currentPage == totalPages}">
                        <button>
                            <i class="fas fa-angle-right"></i>
                        </button>
                    </c:if>
                    <style>
                        .pagination {
                            display: flex;
                            justify-content: center;
    text-align: center;
    button {
        padding: 10px 15px;
        margin: 5px;
        border: 1px solid #ccc;
        background-color: #fff;
        cursor: pointer;
        &:hover {
            background-color: #f5f5f5;
        }
    }
}
                    </style>
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

