<!DOCTYPE html>
<html lang="en">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/product.css">
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
                            <h2>Product</h2>
                            <a class="addButton" href="${pageContext.request.contextPath}/JSP/Dashboard/addproduct.jsp">New Product</a>
                        </div>

                        <jsp:include page="searchdropdown/product.jsp"/>


                        <c:if test="${not empty productError}">
                            <div class="form-alert">

                                <p>Display when theres an alert</p>

                            </div>
                        </c:if>
                        <table>
                            <thead>
                                <tr>
                                    <th>Product title</th>
                                    <th>Product price</th>
                                    <th>Product quantity</th>
                                    <th>Product image</th>
                                    <th>Action</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="product" items="${productList}">
                                    <tr>
                                        <td style="width:300px">${product.title}</td>
                                        <td style="width:300px">$${product.priceOut}</td>
                                        <td style="width:300px">${product.quantityInStock}</td>
                                        <td style="width:400px"><img src="${product.productImg}" width="50px" height="50px" alt="product img"/></td>
                                        <td style="display:flex; justify-content: space-evenly;">
                                            <form action="ProductManagement" method="post">
                                                <input type="hidden" name="productID" value="${product.productID}">
                                                <button type="submit" name="action" value="edit"><img src="${pageContext.request.contextPath}/JSP/Dashboard/pic/edit.svg" width="30px" height="30px" alt="edit"></button>
                                            </form>
                                            <form action="ProductManagement" method="post">
                                                <input type="hidden" name="productID" value="${product.productID}">
                                                <button type="submit" name="action" value="delete"><img src="${pageContext.request.contextPath}/JSP/Dashboard/pic/delete.svg" width="30px" height="30px" alt="delete"></button>
                                            </form>
                                            <form action="ProductManagement" method="post">
                                                <input type="hidden" name="productName" value="${product.title}">
                                                <button type="submit" name="action" value="feedback"><img src="${pageContext.request.contextPath}/JSP/Dashboard/pic/feedback.svg" width="30px" height="30px" alt="feedback"></button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>                                
                            </tbody>
                        </table>
                        <div class="pagination">
                            <c:if test="${currentPage != 1}">
                                <button onclick="window.location.href = 'http://localhost:8080/stbcStore/ProductManagement?page=${currentPage-1}&sort=${param.sort}&order=${param.order}&action=search&searchProduct=${searchProduct}'">
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
                                <button onclick="window.location.href = 'http://localhost:8080/stbcStore/ProductManagement?page=${currentPage+1}&sort=${param.sort}&order=${param.order}&action=search&searchProduct=${searchProduct}'">
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
