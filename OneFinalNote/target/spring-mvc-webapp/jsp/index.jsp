<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://ofn.com/functions" prefix="cf" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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
                <div class="col-12 text-left">
                    <div id="linktitle">Content</div>
                </div>
            </div>

            <div id="staticpagelinkdiv">
                <ul id="links-ul">
                    <li><a class="hlink" href="./">Blog</a></li>
                    <c:forEach var="link" items="${pageLinks}">
                        <li class="staticpages" id="staticpage${link.key}">${link.value}</li>
                    </c:forEach>
                    <li class="staticpages">Music</li>
                    <li class="staticpages">About Me</li>
                </ul>
            </div>
        </div>
        <div class="col-sm-10 text-center center-offset-editor">
            <div id="staticdiv" style="display: none">
            </div>
            <c:set var="isOwnerLoggedIn" value="False"/>
            <sec:authorize access="hasRole('ROLE_OWNER')">
                <c:set var="isOwnerLoggedIn" value="True"/>
            </sec:authorize>
            <form>
                <input type="hidden" name="ownerLoggedIn" id="ownerLoggedIn" value="${isOwnerLoggedIn}"/>
            </form>
            <div id="mainblogdiv">
                <c:forEach var="blog" items="${allBlogs}">
                    <c:if test="${isOwnerLoggedIn || blog.published}">
                        <div class="blogposts staticpages" id="blogPost${blog.blogPostId}"><c:out
                                value="${blog.title}"/></div>
                        <br>
                        <c:out value="Posted by ${blog.userName}"/><br>
                        <c:out value="Last updated: ${cf:formatLocalDateTime(blog.updateTime, 'dd.MM.yyyy hh:mm')}"/><br>
                        <c:out value="${blog.body}" escapeXml="false"/><br>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <a href="deleteBlogPost?blogId=${blog.blogPostId}">Delete</a>
                        </sec:authorize>
                        <hr/>
                    </c:if>
                </c:forEach>
            </div>
            <div id="singleblogdiv" style="display: none">
            </div>
            <div id="commentbuttondiv" style="display: none">
                <sec:authorize access="isAuthenticated()">
                    <form action="addComment" method="POST">
                        <c:set var="isApprovalNeeded" value="True"/>
                        <c:set var="isPublishing" value="False"/>
                        <sec:authorize access="hasRole('ROLE_OWNER')">
                            <c:set var="isApprovalNeeded" value="False"/>
                            <c:set var="isPublishing" value="True"/>
                        </sec:authorize>
                        <textarea name="commentBody" id="commentBody">Comment here</textarea><br>
                        <input type="hidden" id="hiddenBlogPostID"
                               name="hiddenBlogPostID" value="-1"/>
                        <c:choose>
                            <c:when test="${isApprovalNeeded}">
                                <c:out value="Your comment must be approved before posting"/><br>
                            </c:when>
                        </c:choose>
                        <input type="hidden" name="isPublishing" value="${isPublishing}"/>
                        <input type="hidden" name="userLoggedIn" value="${pageContext.request.userPrincipal.name}"/>
                        <input type="submit" class="btn btn-success" value="ADD COMMENT"/>
                    </form>
                </sec:authorize>
            </div>

        </div>
    </div>


</div>
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/tether.min.js"></script>
<script src="./js/moment.min.js"></script>
<script src="./js/jquery-3.2.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/ofn.js"></script>
<script src="./js/index.js"></script>

</body>
</html>


<%--<sec:authorize access="isAuthenticated()">--%>
<%--<p>  This is only visible to users who are logged in.  </p>--%>
<%--</sec:authorize>--%>

<%--<sec:authorize access="hasRole('ROLE_ADMIN')">           <p>--%>
<%--This is only visible to users who also have the ADMIN role.--%>
<%--</p>--%>
<%--<a href="displayuserlist">Display list of users</a>--%>
<%--</sec:authorize>--%>
