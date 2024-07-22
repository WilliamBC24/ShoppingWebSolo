<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                        <li class="nav-item ">
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
                                <a class="nav-link" href="${pageContext.request.contextPath}/CartManager">Cart <span class="badge">${itemincart}</span></a>
                            </li>
                            <style>
                                .nav-item .badge {
    background-color: #ff4747;
    color: white;
    padding: 2px 8px;
    border-radius: 50%;
    font-size: 12px;
    margin-left: 5px;
    vertical-align: middle;
}
                            </style>
                            
                            <li class="nav-item d_none">
                                <a class="nav-link" href="${pageContext.request.contextPath}/ProfileManagement">Dashboard</a>
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