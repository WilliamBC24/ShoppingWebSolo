<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/FrontPage/css/productdetails.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/FrontPage/css/bootstrap.min.css">
        <!-- style css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/FrontPage/css/style.css">
        <!-- fevicon -->
        <link rel="icon" href="${pageContext.request.contextPath}/JSP/FrontPage/images/fevicon.png" type="image/gif" />
        <!-- Scrollbar Custom CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/FrontPage/css/jquery.mCustomScrollbar.min.css">
        <!-- Tweaks for older IEs-->
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.css" media="screen">
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
</head>
<body class="main-layout position_head">
    <div class="header">
        <jsp:include page="navbar.jsp" />
    </div>

    <div class="containerx">
        <div class="product-detailsx">
            <div class="product-imagex">
                <img src="${product.productImg}" alt="Product Image">
            </div>
            <div class="product-infox">
                <h1>${product.title}</h1>
                <div class="product-pricex">$${product.priceOut}</div>
                <div class="product-descriptionx">
                    <p>${product.details}</p>
                </div>
                <div class="buy-buttonsx">
                    <form action="${pageContext.request.contextPath}/ProductDetail">
                        <input type="hidden" name="productID" value="${product.productID}">
                        <div class="quantity-container">
                            <button type="button" class="quantity-button" id="decreaseButton">-</button>
                            <input type="number" id="quantityInput" name="quantity" value="1" min="1" max="${product.quantityInStock}">
                            <button type="button" class="quantity-button" id="increaseButton">+</button>
                        </div>
                        
                        <script>
                            document.getElementById('increaseButton').addEventListener('click', function () {
    let input = document.getElementById('quantityInput');
    let value = parseInt(input.value, 10);
    let max = parseInt(input.max, 10);

    if (value < max) {
        input.value = value + 1;
    }
});

document.getElementById('decreaseButton').addEventListener('click', function () {
    let input = document.getElementById('quantityInput');
    let value = parseInt(input.value, 10);
    let min = parseInt(input.min, 10);

    if (value > min) {
        input.value = value - 1;
    }
});

document.getElementById('quantityInput').addEventListener('input', function () {
    let input = this;
    let value = parseInt(input.value, 10);
    let min = parseInt(input.min, 10);
    let max = parseInt(input.max, 10);

    if (value > max) {
        input.value = max;
    } else if (value < min) {
        input.value = min;
    }
});

                        </script>
                        <button class="button button-secondaryx" type="submit" name="action" value="add"  style="background:none;">
                            <img src="${pageContext.request.contextPath}/JSP/FrontPage/images/add.svg" width="30px" height="50px" alt="add to cart">
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <c:if test="${canReview eq 'yes'}">
            <div class="review-form">
                <h2>Add a Review</h2>
                <form action="#" method="POST" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="reviewText">Review Text:</label>
                        <textarea id="reviewText" name="reviewText" rows="4" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="reviewImage">Upload Image:</label>
                        <input type="file" id="reviewImage" name="reviewImage" accept="image/*">
                    </div>
                    <div class="form-group">
                        <label for="starRating">Star Rating:</label>
                        <div id="starRating">
                            <!-- Star rating input can be implemented using JavaScript or a library like Font Awesome -->
                            <!-- Example: -->
                            <span class="star">&#9733;</span>
                            <span class="star">&#9733;</span>
                            <span class="star">&#9733;</span>
                            <span class="star">&#9733;</span>
                            <span class="star">&#9733;</span>
                        </div>
                    </div>
                    <button type="submit" class="buttonx">Submit Review</button>
                </form>
            </div>
        </c:if>
    </div>
</body>
</html>