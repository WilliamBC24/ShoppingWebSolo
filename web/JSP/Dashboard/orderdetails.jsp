<!DOCTYPE html>
<html lang="en">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/order.css">
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
                            <h2>${username}</h2>
                            <h3>Total $${totalAmount}</h3>
                        </div>
                        <c:if test="${not empty orderError}">
                            <div class="form-alert">

                                <p>Display when theres an alert</p>

                            </div>
                        </c:if>
                        <table>
                            <thead>
                                <tr>
                                    <th>Product</th>
                                    <th>Product Image</th>
                                    <th>Amount</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="order" items="${orderDetails}" varStatus="loop">
                                    <tr>
                                        <td style="width: 450px">${order.productName}</td>
                                        <td style="width: 500px">
                                            <img src="${images[loop.index]}" width="100px" height="100px" alt="Product Image">
                                        </td>
                                        <td style="width: 400px">${order.amount}</td>
                                        <td style="width: 450px">$${order.price}</td>
                                    </tr>
                                </c:forEach> 
                            </tbody>
                        </table>
                    </div>
                    <div class="pagination">
                        <c:if test="${currentPage != 1}">
                            <button onclick="window.location.href = 'http://localhost:8080/stbcStore/OrderManagement?page=${currentPage-1}&action=view'">
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
                            <button onclick="window.location.href = 'http://localhost:8080/stbcStore/OrderManagement?page=${currentPage+1}&action=view'">
                                <i class="fas fa-angle-right"></i>
                            </button>
                        </c:if>
                        <c:if test="${currentPage == totalPages}">
                            <button>
                                <i class="fas fa-angle-right"></i>
                            </button>
                        </c:if>
                    </div>
                </section>
            </div>
        </div>
    </body>
</html>
