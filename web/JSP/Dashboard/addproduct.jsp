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
                        <h2>New Product</h2>
                        <form>
                            <label for="product-name">Product name</label>
                            <input type="text" id="product-name" name="title">
                            <label for="image">Image</label>
                            <input type="file" id="image" name="image">
                            <label for="description">Description</label>
                            <textarea id="description" name="details"></textarea>
                            <label for="quantity">Quantity</label>
                            <input type="text" id="description" name="quantityInStock">
                            <label for="quantity">Purchase Price</label>
                            <input type="text" id="description" name="priceIn">
                            <label for="quantity">Selling Price</label>
                            <input type="text" id="description" name="priceOut">
                            <label for="gender">Gender</label>
                            <select id="gender" name="gender">
                                <option value="0">Male</option>
                                <option value="1">Female</option>
                            </select>
                            <label for="season">Season</label>
                            <select id="season" name="season">
                                <option value="2">SS</option>
                                <option value="3">FW</option>
                            </select>
                            <label for="category">Category</label>
                            <select id="category" name="category">
                                <option value="4">Top</option>
                                <option value="5">Bottom</option>
                                <option value="6">Outerwear</option>
                                <option value="7">Underwear</option>
                            </select>
                            
<!--                            <label for="variation">Variation</label>
                            <div id="variation-container">
                                <div class="variation-field">
                                    <input type="text" id="variation" name="variation">
                                    <input type="file" id="variation-image" name="variation-image">
                                    <div class="buttons">
                                        <button type="button" id="add-variation" onclick="addVariationField()">+</button>
                                    </div>
                                </div>
                            </div>
                            <script>
                                function addVariationField() {
                                    const container = document.getElementById('variation-container');
                                    const newField = document.createElement('div');
                                    newField.className = 'variation-field';
                                    newField.innerHTML = `
                                        <input type="text" name="variation">
                                        <input type="file" name="variation-image">
                                        <div class="buttons">
                                            <button type="button" onclick="addVariationField()">+</button>
                                            <button type="button" id="add-variation" onclick="removeVariationField(this)">-</button>
                                        </div>
                                    `;
                                    container.appendChild(newField);
                                }

                                function removeVariationField(button) {
                                    const field = button.closest('.variation-field');
                                    field.remove();
                                }
                            </script>-->
                            <button type="submit">Add Product</button>
                        </form>
                    </div>
                </section>
            </div>
        </div>
    </body>
</html>
