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
                            <a class="addButton">New Product</a>
                        </div>
                        <p>The title i guess</p>
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
                                    <th>Action</th>
                                </tr>
                            </thead>
                            
                            <tbody>
                                <c:forEach var="product" items="${productList}">
                                    <tr>
                                        <td>${product.title}</td>
                                        <td>${product.price}</td>
                                        <td>${product.quantityInStock}</td>
                                        <td>
                                            <form action="ProductManagement" method="post">
                                                <input type="hidden" name="product" value="${product.productID}">
                                                <button type="submit" name="action" value="edit">Edit</button>
                                            </form>
                                            <form action="ProductManagement" method="post">
                                                <input type="hidden" name="product" value="${product.productID}">
                                                <button type="submit" name="action" value="delete">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>                                
                            </tbody>
                        </table>
                        <div class="pagination">
                            <button><a href="http://localhost:8080/stbcStore/ProductManagement?page-=1"><i class="fas fa-angle-left"></i></a></button>
                            <button>1</button>
                            <button><a href="http://localhost:8080/stbcStore/ProductManagement?page+=1"><i class="fas fa-angle-right"></i></a></button>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </body>
</html>
