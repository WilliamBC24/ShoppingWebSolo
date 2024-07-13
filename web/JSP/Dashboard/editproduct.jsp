<!DOCTYPE html>
<html lang="en">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/addproduct.css">
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <section class="content">
                    <div class="containing">
                        <h2>${oneProduct.title}</h2>
                        <form>
                            <label for="product-name">Product name</label>
                            <input type="text" id="product-name" name="title" placeholder="${oneProduct.title}">
                            <label for="image">Image</label>
                            <input type="file" id="image" name="image">
                            <label for="description">Description</label>
                            <textarea id="description" name="details" placeholder="${oneProduct.details}"></textarea>
                            <label for="quantity">Quantity</label>
                            <input type="text" id="description" name="quantityInStock" placeholder="${oneProduct.quantityInStock}">
                            <label for="quantity">Purchase Price</label>
                            <input type="text" id="description" name="priceIn" placeholder="$${oneProduct.priceIn}">
                            <label for="quantity">Selling Price</label>
                            <input type="text" id="description" name="priceOut" placeholder="$${oneProduct.priceOut}">
                            <label for="gender">Gender</label>
                            <select id="gender" name="gender">
                                <option value="0" ${oneProduct.gender == 0 ? 'selected' : ''}>Male</option>
                                <option value="1" ${oneProduct.gender == 1 ? 'selected' : ''}>Female</option>
                            </select>
                            <label for="season">Season</label>
                            <select id="season" name="season">
                                <option value="2" ${oneProduct.season == 2 ? 'selected' : ''}>SS</option>
                                <option value="3" ${oneProduct.season == 3 ? 'selected' : ''}>FW</option>
                            </select>
                            <label for="category">Category</label>
                            <select id="category" name="category">
                                <option value="4" ${oneProduct.category == 4 ? 'selected' : ''}>Top</option>
                                <option value="5" ${oneProduct.category == 5 ? 'selected' : ''}>Bottom</option>
                                <option value="6" ${oneProduct.category == 6 ? 'selected' : ''}>Outerwear</option>
                                <option value="7" ${oneProduct.category == 7 ? 'selected' : ''}>Underwear</option>
                            </select>
                            <button type="submit">Save changes</button>
                        </form>
                    </div>
                </section>
            </div>
        </div>
    </body>
</html>
