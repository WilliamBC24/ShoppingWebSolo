<!DOCTYPE html>
<html lang="en">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/editpost.css">
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <section class="content">
                    <div class="containing">
                        <h2>${onePost.title}</h2>
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
                        <form action="${pageContext.request.contextPath}/PostManagement" method="post" enctype="multipart/form-data" >
                            <label for="product-name">Post name</label>
                            <input type="text" id="product-name" name="title" placeholder="${onePost.title}"  maxlength="200">
                            <label for="image">Image</label>
                            <input type="file" id="image" name="image" onchange="readURL(this)" >
                            <img id="blah" src="${onePost.postImg}" alt="your image" style="max-width:180px;margin-top: 10px"/>
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
                            <textarea id="description" name="detail" placeholder="${onePost.detail}" maxlength="1000"></textarea>
                            <label for="category">Category</label>
                            <select id="category" name="category">
                                <option value="0" ${onePost.category == 0 ? 'selected' : ''} >Announcement</option>
                                <option value="1" ${onePost.category == 1 ? 'selected' : ''}>Promotional</option>
                            </select>
                            <button type="submit" name="action" value="editing">Edit Post</button>
                        </form>
                    </div>
                </section>
            </div>
        </div>
    </body>
</html>
