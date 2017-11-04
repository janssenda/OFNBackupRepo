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
    <link href="./css/main.css" rel="stylesheet">
    <link href="./css/forms.css" rel="stylesheet">
    <!-- Include external CSS. -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.css">
    <!-- Include Editor style. -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.7.1/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.7.1/css/froala_style.min.css" rel="stylesheet" type="text/css" />
</head>
<body>
        <div class="container" id="page">


            <hr/>
            <div class="row" id="title-row">
                <div class="col-sm-6 text-left" id="title-col-left">

                    <ul class="nav nav-pills" style="display: inline-block; width:50px">
                        <li class="dropdown">
                            <a data-toggle="dropdown" href="#"><img id="menu-button" src="./images/ofn-menu.png"/></a>
                            <ul class="dropdown-menu" id="linksdropdown">
                                <li><a class="hlink" href="./">Home</a></li>
                                <li class="dropdown-divider"></li>
                                <li><a class="hlink" href="./signup">Sign Up</a></li>
                                <li><a class="hlink" href="./createpost">New Post</a></li>
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
                            <input class="login-form" style="width:20%" type="password" placeholder="Password " name="j_password"/>&nbsp;
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
                            <c:forEach var="link" items="${pageLinks}">
                                <li class="staticpages" id="staticpage${link.key}">${link.value}</li>
                            </c:forEach>
                            <li class="staticpages" >Music</li>
                            <li class="staticpages" >About Me</li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-10 text-center center-offset-editor">
                    <form action="save" method="POST">
                        <textarea id="newBlogPost">Write a blog post here! Write the text in this box, and format/insert content using the toolbar!</textarea>
                        <button>SUBMIT</button>
                    </form>
                </div>
            </div>


        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/mode/xml/xml.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.7.1/js/froala_editor.pkgd.min.js"></script>
       <script> $(function() {$('#newBlogPost').froalaEditor({ heightMin:300, charCounterCount: false })});</script>

    </body>
</html>

