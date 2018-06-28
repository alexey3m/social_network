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
    <link href="resources/css/navbar.css" rel="stylesheet" type="text/css"/>
    <title>Account Social net!</title>
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
    <a class="navbar-brand" href="./">Social-Network</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
            aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="FriendsViewServlet">My friends</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="GroupsViewServlet">My groups</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="AccountMessViewServlet">My messages</a>
            </li>
        </ul>
        <div class="col-md-4 navs">
            <form class="form-inline my-2 my-lg-0" action="LogoutServlet">
                <div class="text-login mr-sm-2">
                    Current user: <c:out value="${sessionScope.userName}"/>
                </div>
                <c:set var="email" scope="page" value="${sessionScope.email}"/>
                <c:if test="${email != null}">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Logout!</button>
                </c:if>
            </form>
        </div>
        <jsp:include page="search.jsp"/>
    </div>
</nav>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="resources/js/jquery-3.3.1.slim.min.js"></script>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>