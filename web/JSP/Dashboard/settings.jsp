<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/settings.css">
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <section class="content">
                    <jsp:include page="overview.jsp" />
                    <div class="form-section">
                        <h2>Settings</h2>
                        <p>The title i guess</p>
                        <c:if test="${not empty settingsError}">
                            <div class="form-alert">

                                <p>Display when theres an alert</p>

                            </div>
                        </c:if>
                        <div class="settings">

                            <c:forEach var="settings" items="${settingsList}">
                                <div class="setting">
                                    <p>${settings.settingsType}</p>
                                    <select class="dropdown">
                                        <option value="option1" ${settings.settingsStatus eq 1 ? selected : ''}>${settings.settingsValue}</option>
                                        <option value="option2">Option 2</option>
                                    </select>
                                </div>
                            </c:forEach> 
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </body>
</html>
