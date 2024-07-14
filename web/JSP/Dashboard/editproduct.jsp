<!DOCTYPE html>
<html lang="en">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/addproduct.css">
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <section class="content">
                    <div class="containing">
                        <h2>${oneProduct.title}</h2>
                        <c:if test="${not empty editError}">
                            <div class="form-alert">
                                <p>${editError}</p>
                            </div>
                        </c:if>
                        <c:if test="${not empty editSuccess}">
                            <div class="form-success">
                                <p>${editSuccess}</p>
                            </div>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/ProductManagement" method="post" enctype="multipart/form-data" >
                            <label for="product-name">Product name</label>
                            <input type="text" id="product-name" name="title" placeholder="${oneProduct.title}" maxlength="200">
                            <label for="image">Image</label>
                            <input type="file" accept=".png, .jpg, .jpeg" id="image" name="image" onchange="readURL(this)">
                            <img id="blah" src="${oneProduct.productImg}" alt="your image" style="max-width:180px;margin-top: 10px"/>
                            <script>
                                function readURL(input) {
                                    if (input.files && input.files[0]) {
                                        var reader = new FileReader();

                                        reader.onload = function (e) {
                                            $('#blah')
                                                    .attr('src', e.target.result);
                                        };

                                        reader.readAsDataURL(input.files[0]);
                                    }
                                }
                            </script>
                            <label for="description">Description</label>
                            <textarea id="description" name="details" placeholder="${oneProduct.details}" maxlength="500"></textarea>
                            <label for="quantity">Quantity</label>
                            <input type="text" id="description" name="quantityInStock" placeholder="${oneProduct.quantityInStock}">
                            <label for="quantity" >Purchase Price</label>
                            <input type="text" id="description" name="priceIn" placeholder="${oneProduct.priceIn}">
                            <label for="quantity">Selling Price</label>
                            <input type="text" id="description" name="priceOut" placeholder="${oneProduct.priceOut}">
                            <label for="gender">Gender</label>
                            <select id="gender" name="gender">
                                <option value="0" ${oneProduct.gender == 0 ? 'selected' : '' }>Male</option>
                                <option value="1" ${oneProduct.gender == 1 ? 'selected' : '' }>Female</option>
                            </select>
                            <label for="season">Season</label>
                            <select id="season" name="season">
                                <option value="2" ${oneProduct.season == 2 ? 'selected' : '' }>SS</option>
                                <option value="3" ${oneProduct.season == 3 ? 'selected' : '' }>FW</option>
                            </select>
                            <label for="category">Category</label>
                            <select id="category" name="category">
                                <option value="4" ${oneProduct.category == 4 ? 'selected' : '' }>Top</option>
                                <option value="5" ${oneProduct.category == 5 ? 'selected' : '' }>Bottom</option>
                                <option value="6" ${oneProduct.category == 6 ? 'selected' : '' }>Outerwear</option>
                                <option value="7" ${oneProduct.category == 7 ? 'selected' : '' }>Underwear</option>
                            </select>
                            <button type="submit" name="action" value="editing">Edit Product</button>
                        </form>
                    </div>
                </section>
            </div>
        </div>
    </body>
</html>
