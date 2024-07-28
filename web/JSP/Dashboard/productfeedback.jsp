<!DOCTYPE html>
<html lang="en">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/productfeedback.css">
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <section class="content">
                    <jsp:include page="overview.jsp" />
                    <div class="form-section">
                        <div style="display: flex;justify-content: space-between;">
                            <h2>${productName}</h2>
                            <h3>${rating}<i style="color:gold" class="fas fa-star star-icon"></i></h3>
                        </div>

                        <jsp:include page="searchdropdown/productfeedback.jsp"/>


                        <c:if test="${not empty productError}">
                            <div class="form-alert">
                                <p>Display when theres an alert</p>

                            </div>
                        </c:if>
                        <table>
                            <thead>
                                <tr>
                                    <th>User</th>
                                    <th>Details</th>
                                    <th>Star</th>
                                    <th>Attached Image</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="feedback" items="${feedbackList}">
                                    <tr>
                                        <td style="width:200px">${feedback.username}</td>
                                        <td style="width:500px">${feedback.feedbackDetail}</td>
                                        <td style="width:350px"><c:forEach begin="1" end="${feedback.star}">
                                                <i style="color:gold" class="fas fa-star star-icon"></i>
                                            </c:forEach></td>
                                        <td style="width:200px"><img src="${feedback.attachedImg}" width="50px" height="50px" alt="feedback img"/></td>
                                        
                                    </tr>
                                </c:forEach>                                
                            </tbody>
                        </table>
                        <div class="pagination">
                            <c:if test="${currentPage != 1}">
                                <button onclick="window.location.href = 'http://localhost:8080/stbcStore/ProductManagement?page=${currentPage-1}&productName=${productName}&sort=${param.sort}&order=${param.order}&action=feedback&searchProduct=${searchProduct}'">
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
                                <button onclick="window.location.href = 'http://localhost:8080/stbcStore/ProductManagement?page=${currentPage+1}&productName=${productName}&sort=${param.sort}&order=${param.order}&action=feedback&searchProduct=${searchProduct}'">
                                    <i class="fas fa-angle-right"></i>
                                </button>
                            </c:if>
                            <c:if test="${currentPage == totalPages}">
                                <button>
                                    <i class="fas fa-angle-right"></i>
                                </button>
                            </c:if>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </body>
</html>
