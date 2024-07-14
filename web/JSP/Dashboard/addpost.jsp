<!DOCTYPE html>
<html lang="en">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/addpost.css">
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <section class="content">
                    <div class="containing">
                        <h2>New Post</h2>
                        <c:if test="${not empty addError}">
                            <div class="form-alert">
                                <p>${addError}</p>
                            </div>
                        </c:if>
                        <c:if test="${not empty addSuccess}">
                            <div class="form-success">
                                <p>${addSuccess}</p>
                            </div>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/PostManagement" method="post" enctype="multipart/form-data" >
                            <label for="product-name">Post name</label>
                            <input type="text" id="product-name" placeholder="Title" name="title" required maxlength="200">
                            <label for="image">Image</label>
                            <input type="file" id="image" name="image" onchange="readURL(this)" required>
                            <img id="blah" src="http://placehold.it/180" alt="your image" style="max-width:180px;margin-top: 10px"/>
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
                            <label for="description">Body</label>
                            <textarea id="description" placeholder="Body" name="detail" maxlength="1000"></textarea>
                            <label for="category">Category</label>
                            <select id="category" name="category">
                                <option value="0">Announcement</option>
                                <option value="1">Promotional</option>
                            </select>
                            <button type="submit" name="action" value="add">Add Post</button>
                        </form>
                    </div>
                </section>
            </div>
        </div>
    </body>
</html>
