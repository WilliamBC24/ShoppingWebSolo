<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/myorders.css">
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <section class="content">
                    <div class="form-section">
                        <h2>My Orders</h2>
                        <p>The title i guess</p>
                        <c:if test="${not empty myOrderError}">
                            <div class="form-alert">

                                <p>Display when theres an alert</p>

                            </div>
                        </c:if>
                        <table>
                            <thead>
                                <tr>
                                    <th>Order date</th>
                                    <th>Total price</th>
                                    <th>Order status</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="myOrder" items="${myOrderList}">
                                    <tr>
                                        <td>${myOrder.orderDate}</td>
                                        <td>$${myOrder.totalAmount}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${myOrder.status == 0}">
                                                    Preparing
                                                </c:when>
                                                <c:when test="${myOrder.status == 1}">
                                                    Delivering
                                                </c:when>
                                                <c:when test="${myOrder.status == 2}">
                                                    Delivered
                                                </c:when>
                                                <c:when test="${myOrder.status == 3}">
                                                    Returned
                                                </c:when>
                                                <c:when test="${myOrder.status == 4}">
                                                    Cancelled
                                                </c:when>
                                                <c:otherwise>
                                                    Unknown
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="display: flex; justify-content: center;align-items: center">
                                            <form action="MyOrders" method="post">
                                                <input type="hidden" name="order" value="${myOrder.orderID}">
                                                <button type="submit" name="action" value="view">Edit</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>    
                            </tbody>
                        </table>
                        <div class="pagination">
                            <c:if test="${currentPage != 1}">
                                <button onclick="window.location.href = 'http://localhost:8080/stbcStore/MyOrders?page=${currentPage-1}'">
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
                                <button onclick="window.location.href = 'http://localhost:8080/stbcStore/MyOrders?page=${currentPage+1}'">
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
