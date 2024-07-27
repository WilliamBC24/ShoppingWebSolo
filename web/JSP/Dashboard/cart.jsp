<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/cart.css">
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <section class="content">
                    <div class="form-section">
                        <div style="display: flex;justify-content: space-between;">
                            <h2>My Cart</h2>
                            <h3>Total $${total}</h3>
                            <a class="addButton" href="${pageContext.request.contextPath}/JSP/VNPay/index.jsp?total=${realTotal}">Complete order</a>
                        </div>
                        <c:if test="${not empty myOrderError}">
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
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="cart" items="${cartList}" varStatus="loop">
                                    <tr>
                                        <td width="400px">${productList[loop.index].title}</td>
                                        <td width="400px"><img width="100px" src="${productList[loop.index].productImg}" alt="" onclick="window.location.href='http://localhost:8080/stbcStore/ProductListing?product=${productList[loop.index].productID}&action=details'"></td>
                                        <td width="400px">${cart.quantity}</td>
                                        <td width="300px">$${cart.quantity * productList[loop.index].priceOut}</td>
                                        <td width="200px" style="display:flex; justify-content: space-around;">
                                            <form action="${pageContext.request.contextPath}/CartManager" method="post">
                                                <input type="hidden" name="productID" value="${productList[loop.index].productID}">
                                                <button type="submit" name="action" value="minus">
                                                    <i class="fas fa-minus"></i>
                                                </button>
                                            </form>
                                            <form action="${pageContext.request.contextPath}/CartManager" method="post">
                                                <input type="hidden" name="productID" value="${productList[loop.index].productID}">
                                                <button type="submit" name="action" value="plus">
                                                    <i class="fas fa-plus"></i>
                                                </button>
                                            </form>
                                            <form action="${pageContext.request.contextPath}/CartManager" method="post">
                                                <input type="hidden" name="productID" value="${productList[loop.index].productID}">
                                                <button type="submit" name="action" value="remove">
                                                    <i class="fas fa-trash"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            
                        </table>
                    </div>
                </section>
            </div>
        </div>
    </body>
</html>
