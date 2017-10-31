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
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/forms.css" rel="stylesheet">
</head>
<body>
<div class="container" id="page">

    <br/>
    <div class="row row-fluid" id="title-row">
        <div class="col-md-4 text-lef" id="title-col">
            <span id="title"> One Final Note</span>
        </div>
        <div class="col-md-8 text-right">
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <c:if test="${param.login_error == 1}">
                    Wrong id or password!
                </c:if><br/>
                <form role="form" action="j_spring_security_check" method="post">
                    <span class="form-label">Username:</span>&nbsp;
                    <input class="login-form" name="j_username"/>&nbsp;
                    <span class="form-label">Password:</span>&nbsp;
                    <input class="login-form" type="password" name="j_password"/>&nbsp;
                    <button type="submit">Login</button>
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
        <div class="col-sm-3 text-center">
            Links over here
        </div>
        <div class="col-sm-8 text-center">
            Sign up


            <form method="POST" action="newuser">
                Username: <input type="text"
                                 name="username"/><br/>
                Password: <input type="password"
                                 name="password"/><br/>
                <input type="submit" value="Add User"/><br/>
            </form>


        </div>
    </div>


</div>
<!-- Placed at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>

