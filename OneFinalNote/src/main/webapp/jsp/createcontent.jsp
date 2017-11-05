<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Index Page</title>
    <!-- Bootstrap core CSS -->
    <link href="./css/bootstrap.min.css" rel="stylesheet">

    <!-- Include external CSS. -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.css">
    <!-- Include Editor style. -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.7.1/css/froala_editor.pkgd.min.css"
          rel="stylesheet" type="text/css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.7.1/css/froala_style.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="./css/main.css" rel="stylesheet" type="text/css">
    <link href="./css/forms.css" rel="stylesheet" type="text/css">
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


    <c:set var="isApprovalNeeded" value="True"/>
    <sec:authorize access="hasRole('ROLE_OWNER')">
        <c:set var="isApprovalNeeded" value="False"/>
    </sec:authorize>

    <form action="save" method="POST">
        <div class="row">
            <div class="col-sm-3" id="links-bar" >
                <div class="row">
                    <div class="col-12 text-center">
                        <div id="linktitle">Properties</div>
                    </div>
                </div>
                <div id="contentformlinks">
                    <table id="contentform-table">
                        <tbody>
                        <tr><td class="form-left" >Title:</td><td class="form-right" >
                            <input class = "contentform" type="text" placeholder="Title" id="newBlogPostTitle" required/></td></tr>
                        <tr><td class="form-left" >Category:</td><td class="form-right">
                            <select class = "contentform" id="categorySelector" required>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.categoryName}">${cat.categoryName}</option>
                                </c:forEach>
                            </select>
                        </td></tr>
                        <tr>
                            <td class="form-left">Start Date:</td>
                            <td class="form-right">
                            <input class = "contentform" type="datetime-local" id="startDateSelector"/></td></tr>
                        <tr>
                            <td class="form-left">End Date:</td>
                            <td class="form-right">
                            <input  class = "contentform" type="datetime-local" id="endDateSelector"/></td></tr>
                        <tr>
                            <td class="form-left">Post Type:</td>
                            <td class="form-right">
                            Blog <input class="ebox" type="radio" value="blog" name="typeRadio" checked>&nbsp;&nbsp;
                            Page <input class="ebox" type="radio" value="page" name="typeRadio"></td></tr>
                        <tr>
                            <td class="form-left">Publish:</td><td class="form-right">
                            <input type="checkbox" class="ebox"/></td></tr>
                        <tr>
                            <td id="cf-buttonrow" colspan="2">
                            <button>Save</button>&nbsp;&nbsp;
                            <button>Discard</button></td> </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-sm-9 text-center">
                <div id="newBlogPost" placeholder="New Content"></div><br>
            </div>
        </div>
    </form>
</div>

<script src="./js/tether.min.js"></script>
<script src="./js/moment.min.js"></script>
<script src="./js/jquery-3.2.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/ofn.js"></script>
<script src="./js/createcontent.js"></script>

<script src="./js/codemirror.min.js"></script>
<script src="./js/xml.min.js"></script>
<script src="./js/froala_editor.pkgd.min.js"></script>
</body>
</html>

<%--<div id="blogOptions">--%>
<%--</div>--%>
<%--<label for="publishedSelector">Publish new post?</label>--%>
<%--<br>--%>
<%--<label for="blogPostRadio">Type of post:</label><br>--%>
<%--<input type="radio" id="blogPostRadio" checked>--%>
<%--<label for="blogPostRadio">Blog post</label><br>--%>
<%--<sec:authorize access="hasRole('ROLE_OWNER')">--%>
<%--<input type="radio" id="staticPageRadio">--%>
<%--<label for="staticPageRadio">Static page</label><br>--%>
<%--</sec:authorize>--%>
<%--<c:choose>--%>
<%--<c:when test="${isApprovalNeeded}">--%>
<%--<c:out value="The owner must approve your post before it is published"/>--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--<c:out value="When you're ready, press SUBMIT"/>--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>
<%--<br>--%>
<%--<button>SUBMIT</button>--%>