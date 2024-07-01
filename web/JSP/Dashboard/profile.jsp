<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/profile.css">
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <section class="profile">
                    <div class="header">
                        <img src="${loggedinuser.avatarImg}"  class="pic">
                        <h1>${loggedinuser.username}</h1>
                        <c:if test="${loggedinuser.verifiedStatus eq '0'}">
                            <a href="${pageContext.request.contextPath}/VerifyAccountMail">Verify your account</a>
                        </c:if>
                        <c:if test="${loggedinuser.verifiedStatus eq '1'}">
                            <p style="color:green;">Account verified <i class="fas fa-check"></i></p>
                            </c:if>
                    </div>
                    <div class="details">
                        <div style="display: flex;justify-content: space-between;">
                            <h2>Profile Details</h2>
                            <a href="${pageContext.request.contextPath}/JSP/Dashboard/editprofile.jsp" class="button">Edit profile</a>
                        </div>
                        <div class="card">
                            <div class="item">
                                <span class="label">Username:</span>
                                <span class="value">${loggedinuser.username}</span>
                            </div>
                            <div class="item">
                                <span class="label">Email:</span>
                                <span class="value">${loggedinuser.email}</span>
                            </div>
                            <div class="item">
                                <span class="label">Password:</span>
                                <span class="value">**********</span>
                            </div>
                            <div class="item">
                                <span class="label">Gender:</span>
                                <span class="value">${loggedinuser.gender}</span>
                            </div>
                            <div class="item">
                                <span class="label">Phone Number:</span>
                                <span class="value">${loggedinuser.phoneNumber}</span>
                            </div>
                            <div class="item">
                                <span class="label">Full Name:</span>
                                <div style="display:flex;width:100%;justify-content: space-between">
                                    <span class="value" style="flex-grow:0.48;padding-left:5px">${loggedinuser.firstName}</span>
                                    <span class="value" style="flex-grow:0.48;">${loggedinuser.lastName}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </body>
</html>
