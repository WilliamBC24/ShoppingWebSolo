<!DOCTYPE html>
<html lang="en">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/post.css">
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
                            <h2>Post</h2>
                            <a class="addButton" href="${pageContext.request.contextPath}/JSP/Dashboard/addpost.jsp">New Post</a>
                        </div>
                        <jsp:include page="searchdropdown/post.jsp"/>
                        <c:if test="${not empty postError}">
                            <div class="form-alert">

                                <p>Display when theres an alert</p>

                            </div>
                        </c:if>
                        <table>
                            <thead>
                                <tr>
                                    <th>Post ID</th>
                                    <th>Post title</th>
                                    <th>Updated Date</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="post" items="${postList}">
                                    <tr>
                                        <td style="width:450px">${post.postID}</td>
                                        <td style="width:450px">${post.title}</td>
                                        <td style="width:450px">${post.updatedDate}</td>
                                        <td  style="display:flex; justify-content: space-around;">
                                            <form action="PostManagement" method="post">
                                                <input type="hidden" name="postID" value="${post.postID}">
                                                <button type="submit" name="action" value="edit"><img src="${pageContext.request.contextPath}/JSP/Dashboard/pic/edit.svg" width="30px" height="30px" alt="edit"></button>
                                            </form>
                                            <form action="PostManagement" method="post">
                                                <input type="hidden" name="postID" value="${post.postID}">
                                                <button type="submit" name="action" value="delete"><img src="${pageContext.request.contextPath}/JSP/Dashboard/pic/delete.svg" width="30px" height="30px" alt="delete"></button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>                                
                            </tbody>
                        </table>
                        <div class="pagination">
                            <c:if test="${currentPage != 1}">
                                <button onclick="window.location.href = 'http://localhost:8080/stbcStore/PostManagement?page=${currentPage-1}&sort=${param.sort}&order=${param.order}&action=search&searchPost=${searchPost}'">
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
                                <button onclick="window.location.href = 'http://localhost:8080/stbcStore/PostManagement?page=${currentPage+1}&sort=${param.sort}&order=${param.order}&action=search&searchPost=${searchPost}'">
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
