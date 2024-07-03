<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="sidebar">
    <div class="user-profile">
        <img src="${loggedinuser.avatarImg}" class="pic">
        <script>
            document.getElementByClass('pic').onerror = function () {
                this.src = 'http://localhost:8080/stbcStore/img/placeholder.png';
            };
        </script>
        <p>${loggedinuser.username}</p>
    </div>
    <ul>
        <li><a href="${pageContext.request.contextPath}/ProfileManagement">Profile</a></li>

        <c:if test="${loggedinuser.accessLevel eq '2'||loggedinuser.accessLevel eq '3'}"> 
            <li onclick="window.location.href = '${pageContext.request.contextPath}/ProductManagement'">Product</li>
            <li onclick="window.location.href = '${pageContext.request.contextPath}/OrderManagement'">Order</a></li>
            <li onclick="window.location.href = '${pageContext.request.contextPath}/PostManagement'">Post</li>

            <li><a href="#">Slider</a></li>
            </c:if>
            <c:if test="${loggedinuser.accessLevel eq '3'}">
            <li onclick="window.location.href = '${pageContext.request.contextPath}/UserManagement'">User</li>
            <li onclick="window.location.href = '${pageContext.request.contextPath}/SettingsManagement'">Settings</li>
            </c:if>

        <li><a href="${pageContext.request.contextPath}/index.jsp">Back</a></li>
    </ul>
</nav>
