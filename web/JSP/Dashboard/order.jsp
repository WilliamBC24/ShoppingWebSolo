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
                    <h2>Order</h2>
                    <p>The title i guess</p>
                   <c:if test="${not empty orderError}">
                            <div class="form-alert">

                                <p>Display when theres an alert</p>

                            </div>
                        </c:if>
                    <table>
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Order date</th>
                                <th>Order status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${orderList}">
                                    <tr>
                                        <td>${order.orderID}</td>
                                        <td>${order.orderDate}</td>
                                        <td>${order.status}</td>
                                        <td>
                                            <form action="OrderManagement" method="post">
                                                <input type="hidden" name="order" value="${order.orderID}">
                                                <button type="submit" name="action" value="edit">Edit</button>
                                            </form>
                                            <form action="OrderManagement" method="post">
                                                <input type="hidden" name="order" value="${order.orderID}">
                                                <button type="submit" name="action" value="delete">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>    
                        </tbody>
                    </table>
                    <div class="pagination">
                            <c:if test="${currentPage != 1}">
                                <button onclick="window.location.href = 'http://localhost:8080/stbcStore/OrderManagement?page=${currentPage-1}'">
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
                                <button onclick="window.location.href = 'http://localhost:8080/stbcStore/OrderManagement?page=${currentPage+1}'">
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
