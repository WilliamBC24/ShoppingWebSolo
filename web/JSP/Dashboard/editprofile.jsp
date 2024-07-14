<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/editprofile.css">
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <section class="profile">
                    <div class="header">
                        <img src="${loggedinuser.avatarImg}" class="pic" onclick="triggerFileInput()">
                        <h1>${loggedinuser.username}</h1>
                        <form action="${pageContext.request.contextPath}/AvatarChange" id="uploadForm" method="post" enctype="multipart/form-data" style="display:none">
                            <input type="file" accept=".png, .jpg, .jpeg" id="fileInput" name="file" onchange="handleFileChange()">
                        </form>
                        <script>
                            function triggerFileInput() {
                                document.getElementById('fileInput').click();
                            }

                            function handleFileChange() {
                                document.getElementById('uploadForm').submit();
                            }
                        </script>
                        <c:if test="${loggedinuser.verifiedStatus eq '0'}">
                            <a href="${pageContext.request.contextPath}/VerifyAccountMail">Verify your account</a>
                        </c:if>
                        <c:if test="${loggedinuser.verifiedStatus eq '1'}">
                            <p style="color:green;">Account verified <i class="fas fa-check"></i></p>
                            </c:if>
                    </div>
                    <div class="details">
                        <form action="${pageContext.request.contextPath}/ProfileManagement" method="post">
                            <div class="card">
                                <div class="item">
                                    <label class="label" for="username">Username:</label>
                                    <input type="text" id="username" name="username" class="input-field" placeholder="${loggedinuser.username}" maxlength="100">
                                </div>
                                <div class="item">
                                    <label class="label" for="email">Email:</label>
                                    <input type="email" id="email" name="email" class="input-field" placeholder="${loggedinuser.email}" maxlength="150">
                                </div>
                                <div class="item">
                                    <label class="label" for="password">Password:</label>
                                    <div style="display: flex; width: 100%; justify-content: space-between;">
                                        <input style="width:48%" maxlength="100" id="password" type="password" name="password" class="input-field" placeholder="New password" style="flex-grow: 0.48; padding-left: 5px;">
                                        <input style="width:48%" maxlength="100" id="rePassword" type="password" name="rePassword" placeholder="Re-enter password" class="input-field"style="flex-grow: 0.48;">
                                    </div>
                                </div>
                                <div class="item">
                                    <label class="label" for="gender">Gender:</label>
                                    <select id="gender" name="gender" class="input-field">
                                        <option value="Male" ${loggedinuser.gender eq 'Male' ? 'selected' : ''}>Male</option>
                                        <option value="Female" ${loggedinuser.gender eq 'Female' ? 'selected' : ''}>Female</option>
                                    </select>
                                </div>
                                <div class="item">
                                    <label class="label" for="phoneNumber">Phone Number:</label>
                                    <input type="text" id="phoneNumber" name="phoneNumber" class="input-field" placeholder="${loggedinuser.phoneNumber}">
                                </div>
                                <div class="item">
                                    <label class="label" for="firstName">Full Name:</label>
                                    <div style="display: flex; width: 100%; justify-content: space-between;">
                                        <input style="width:48%" maxlength="150" type="text" id="firstName" name="firstName" class="input-field" placeholder="${loggedinuser.firstName}" style="flex-grow: 0.48; padding-left: 5px;">
                                        <input style="width:48%" maxlength="150" type="text" id="lastName" name="lastName" class="input-field" placeholder="${loggedinuser.lastName}" style="flex-grow: 0.48;">
                                    </div>
                                </div>

                            </div>
                            <div class="item">
                                <button id="button" type="submit" name="action" value="infoChange" class="button">Save changes</button>
                            </div>
                        </form>
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
                    </div>
                </section>
            </div>
        </div>           
    </body>
</html>
