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
                        <jsp:include page="searchdropdown/order.jsp"/>
                        <c:if test="${not empty orderError}">
                            <div class="form-alert">

                                <p>Display when theres an alert</p>

                            </div>
                        </c:if>
                        <table>
                            <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>Order date</th>
                                    <th>Order status</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="order" items="${orderList}">
                                    <tr>
                                        <td>${order.username}</td>
                                        <td>${order.orderDate}</td>
                                <form action="OrderManagement" method="post">
                                    <td>
                                        <select name="status">
                                            <option value="0" ${order.status == 0 ? 'selected' : ''}>Preparing</option>
                                            <option value="1" ${order.status == 1 ? 'selected' : ''}>Delivering</option>
                                            <option value="2" ${order.status == 2 ? 'selected' : ''}>Delivered</option>
                                            <option value="3" ${order.status == 3 ? 'selected' : ''}>Returned</option>
                                            <option value="4" ${order.status == 4 ? 'selected' : ''}>Cancelled</option>
                                        </select>
                                    </td>

                                    <td style="display: flex;justify-content: center">

                                        <input type="hidden" name="orderID" value="${order.orderID}">
                                        <button type="submit" name="action" value="edit">Edit</button>

                                    </td>
                                </form>
                                </tr>
                            </c:forEach>    
                            </tbody>
                        </table>
                    </div>
                    <div class="pagination">
                        <c:if test="${currentPage != 1}">
                            <button onclick="window.location.href = 'http://localhost:8080/stbcStore/OrderManagement?page=${currentPage-1}&sort=${param.sort}&order=${param.order}&action=search'">
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
                            <button onclick="window.location.href = 'http://localhost:8080/stbcStore/OrderManagement?page=${currentPage+1}&sort=${param.sort}&order=${param.order}&action=search'">
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
