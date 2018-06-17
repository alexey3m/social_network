<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">

    <!-- Custom styles for this template -->
    <link href="resources/css/login.css" rel="stylesheet" type="text/css" />

    <title>Login to Social net!</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main" class="container">

    <c:if test="${sessionScope.email != null}">
        <c:redirect url="account.jsp?id=${sessionScope.id}"/>
    </c:if>
    <form class="form-signin" action="LoginServlet" method="post">
        <c:set var="message" scope="session" value="${param.message}"/>
        <c:if test="${message == 'alert'}">
            <div class="alert alert-danger form-signin-alert" role="alert">
                <strong>Error!</strong> <br>You login or password do not recognized!
            </div>
        </c:if>
        <c:if test="${message == 'reg'}">
            <div class="alert alert-success text-alert" role="alert">
                <strong>Congratulations!</strong> <br>Are you registered! You can login.
            </div>
        </c:if>
        <h2 class="form-signin-heading">Social network</h2>
        <label for="inputEmail" class="sr-only">Email</label>
        <input type="email" id="inputEmail" name="inputEmail" class="form-control" placeholder="Email" autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="Password">
        <div class="checkbox">
            <label>
                <input type="checkbox" name="rememberMe" value="active"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
    <div class="form-signin-alert">
        or<br>
        <a href="reg.jsp">
            <button type="button" class="btn btn-lg btn-link">Registration</button>
        </a>
    </div>


</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="resources/js/jquery-3.3.1.slim.min.js"></script>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>