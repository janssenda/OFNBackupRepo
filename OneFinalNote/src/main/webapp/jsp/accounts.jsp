<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Index Page</title>
    <!-- Bootstrap core CSS -->
    <link href="./css/bootstrap.min.css" rel="stylesheet">
    <link href="./css/main.css" rel="stylesheet">
    <link href="./css/forms.css" rel="stylesheet">
</head>
<body>
<div class="container" id="page">


    <hr/>
    <div class="row" id="title-row">
        <div class="col-sm-6 text-left" id="title-col-left">

            <ul id="m" class="nav nav-pills" style="display: inline-block; width:50px">
                <li id="menudrop" class="dropdown">
                    <a data-toggle="dropdown" href="#"><img id="menu-button" src="./images/ofn-menu.png"/></a>
                    <ul class="dropdown-menu" id="linksdropdown">
                        <li><a class="hlink" href="./">Home</a></li>
                        <li class="dropdown-divider"></li>
                        <li><a class="hlink" href="./signup">Sign Up</a></li>
                        <li><a class="hlink" href="./createcontent">New Post</a></li>
                        <li><a class="hlink" href="accounts">Accounts</a></li>
                    </ul>
                </li>
            </ul>
            <span id="title"> One Final Note</span>&nbsp;

        </div>
        <div class="col-sm-6 text-right" id="title-col">
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <c:if test="${param.login_error == 1}">
                    <span class="errmsg"> Wrong id or password!</span><br/>
                </c:if>
                <form role="form" action="j_spring_security_check" style="display: inline" method="post">
                    <input class="login-form" style="width:20%" name="j_username" placeholder="Username"/>&nbsp;
                    <input class="login-form" style="width:20%" type="password" placeholder="Password "
                           name="j_password"/>&nbsp;
                    <button type="submit">Login</button>
                </form>
                <form role="form" style="display: inline" action="signup" method="get">
                    <button type="submit">Sign up</button>
                </form>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <span id="welcome">
                    Hello : <span id="hello">${pageContext.request.userPrincipal.name}</span>
                    | <a href="<c:url value="/j_spring_security_logout" />"> Logout</a>
                </span>
            </c:if>
        </div>
    </div>
    <hr/>


    <div class="row">
        <div class="col-sm-2 text-left" id="links-bar">
            <div class="row">
                <div class="col-12 text-center">
                    <div id="linktitle">Content</div>
                </div>
            </div>

            <div id="staticpagelinkdiv">
                <ul id="links-ul">
                    <li class="staticpages">View Users</li>
                    <li class="staticpages">Search Users</li>
                </ul>
            </div>
        </div>
        <div class="col-sm-10 text-center center-offset">
            <div class="row">
                <div class="col-12">
                    <h2>Manage Users</h2>
                </div>
            </div>
            <br/>


            <div style="display: inline-block; width: 90%">

                <c:forEach var="user" items="${users}">

                    <div class="user-role-div text-left">
                        <form action="./manageuser?userid=${user.userId}" method="post">
                            <div class="row">
                                <div class="col-sm-7">
                                    Username: <span class="edit-username-font"><c:out
                                        value="${user.userName}"/></span><br/>
                                    Authorities:
                                    <c:forEach var="auth" items="${user.authorities}" varStatus="status">
                                        ${auth}<c:if test="${not status.last}">, </c:if>
                                    </c:forEach>

                                </div>
                                <div class="col-sm-5 text-right" style="margin-top: -7px">
                                    <input name="enabledbox" class="ebox" value="true" type="checkbox"
                                           <c:if test="${user.isEnabled}">checked="checked"</c:if>
                                           style="margin-bottom: 2px"/>
                                    Enabled<br/>

                                    <select name="roleselect" class="roleselect">
                                        <option value="false">Change Role</option>
                                        <option value="owner">Owner</option>
                                        <option value="admin">Admin</option>
                                        <option value="user">User</option>
                                    </select>
                                    <button class="editbutton" type="submit" name="editbutton" value="delete">Delete
                                    </button>
                                    <button class="editbutton" type="submit" name="editbutton" value="update">Update
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <br/>

                </c:forEach>

            </div>
        </div>


    </div>
</div>
<script src="./js/tether.min.js"></script>
<script src="./js/moment.min.js"></script>
<script src="./js/jquery-3.2.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/ofn.js"></script>
</body>
</html>
