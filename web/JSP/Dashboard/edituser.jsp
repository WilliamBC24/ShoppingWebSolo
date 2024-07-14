<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <script src="https://kit.fontawesome.com/64d58efce2.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/JSP/Dashboard/css/edituser.css">
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp" />
            <div class="main-content">
                <section class="profile">
                    <div class="header">
                        <img src="${editchosenuser.avatarImg}" class="pic" onclick="triggerFileInput()">
                        <h1>${editchosenuser.username}</h1>
                        <form action="${pageContext.request.contextPath}/AvatarChangeUser" id="uploadForm" method="post" enctype="multipart/form-data" style="display:none">
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
                    </div>
                    <div class="details">
                        <form action="${pageContext.request.contextPath}/UserManagement" method="post">
                            <div class="card">
                                <div class="item">
                                    <label class="label" for="username">Username:</label>
                                    <input type="text" maxlength="100" id="username" name="username" class="input-field" placeholder="${editchosenuser.username}">
                                </div>                                <div class="item">
                                    <label class="label" for="email">Email:</label>
                                    <input type="email" maxlength="150" id="email" name="email" class="input-field" placeholder="${editchosenuser.email}">
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
                                        <option value="Male" ${editchosenuser.gender eq 'Male' ? 'selected' : ''}>Male</option>
                                        <option value="Female" ${editchosenuser.gender eq 'Female' ? 'selected' : ''}>Female</option>
                                    </select>
                                </div>
                                <div class="item">
                                    <label class="label" for="phoneNumber">Phone Number:</label>
                                    <input type="text" id="phoneNumber" name="phoneNumber" class="input-field" placeholder="${editchosenuser.phoneNumber}">
                                </div>
                                <div class="item">
                                    <label class="label" for="firstName">Full Name:</label>
                                    <div style="display: flex; width: 100%; justify-content: space-between;">
                                        <input style="width:48%" type="text" id="firstName" name="firstName" class="input-field" placeholder="${editchosenuser.firstName}" style="flex-grow: 0.48; padding-left: 5px;">
                                        <input style="width:48%" type="text" id="lastName" name="lastName" class="input-field" placeholder="${editchosenuser.lastName}" style="flex-grow: 0.48;">
                                    </div>
                                </div>
                                <div class="item">
                                    <label class="label" for="accessLevel">Access Level</label>
                                    <select id="accessLevel" name="accessLevel" class="input-field">
                                        <option value="1" ${editchosenuser.accessLevel eq 1 ? 'selected' : ''}>Customer</option>
                                        <option value="2" ${editchosenuser.accessLevel eq 2 ? 'selected' : ''}>Staff</option>
                                        <option value="3" ${editchosenuser.accessLevel eq 3 ? 'selected' : ''}>Admin</option>
                                    </select>
                                </div>

                            </div>
                            <div class="item" style="margin-bottom:0px">
                                <button id="button" type="submit" name="action" value="change" class="button">Save changes</button>
                            </div>
                        </form>
                        <div class="item" >
                            <button class="button" onclick="window.location.href='${pageContext.request.contextPath}/UserManagement'" style="text-decoration: none; color: black;">Back</button>
                        </div>
                        <c:if test="${not empty userEditError}">
                            <div class="form-alert">
                                <p>${userEditError}</p>
                            </div>
                        </c:if>
                        <c:if test="${not empty userEditSuccess}">
                            <div class="form-success">
                                <p>${userEditSuccess}</p>
                            </div>
                        </c:if>
                    </div>
                </section>
            </div>
        </div>           
    </body>
</html>
