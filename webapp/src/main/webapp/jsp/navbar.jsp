<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <!-- Custom styles for this template -->
    <link href="<c:url value="/resources/css/navbar.css"/>" rel="stylesheet" type="text/css"/>
    <title>Account Social net!</title>
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top changeZ">
    <a class="navbar-brand" href="<c:url value="/"/>">Социальная сеть</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
            aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/viewFriends"/>">Мои друзья</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/viewGroups"/>">Мои группы</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/viewAccountMess"/>">Мои сообщения</a>
            </li>
        </ul>
        <div class="col-md-4 navs">
            <form class="form-inline my-2 my-lg-0" action="<c:url value="/logout"/>">
                <div class="text-login mr-sm-2">
                    Пользователь: <c:out value="${sessionScope.userName}"/>
                </div>
                <c:set var="email" scope="page" value="${sessionScope.email}"/>
                <c:if test="${email != null}">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Выйти!</button>
                </c:if>
            </form>
        </div>
        <jsp:include page="/jsp/search.jsp"/>
    </div>
</nav>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
</body>
</html>