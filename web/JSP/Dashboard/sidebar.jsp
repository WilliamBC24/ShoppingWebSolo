<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/sidebar.css">
<nav class="sidebar">
    <div class="user-profile">
        <img src="${loggedinuser.avatarImg}" class="pic">
        <p>${loggedinuser.username}</p>
    </div>
    <ul>
        <li onclick="window.location.href = '${pageContext.request.contextPath}/ProfileManagement'">Profile</li>
        <li onclick="window.location.href = '${pageContext.request.contextPath}/MyOrders'">My Orders</li>
        
        <c:if test="${loggedinuser.accessLevel eq '2'||loggedinuser.accessLevel eq '3'}"> 
            <li onclick="window.location.href = '${pageContext.request.contextPath}/ProductManagement'">Product</li>
            <li onclick="window.location.href = '${pageContext.request.contextPath}/OrderManagement'">Order</a></li>
            <li onclick="window.location.href = '${pageContext.request.contextPath}/PostManagement'">Post</li>
            </c:if>
            <c:if test="${loggedinuser.accessLevel eq '3'}">
            <li onclick="window.location.href = '${pageContext.request.contextPath}/UserManagement'">User</li>
            <li onclick="window.location.href = '${pageContext.request.contextPath}/SettingsManagement'">Settings</li>
            </c:if>

        <li onclick="window.location.href = '${pageContext.request.contextPath}/Homepage'">Back</li>
    </ul>
</nav>
