<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet" type="text/css"/>

    <title>Вход в Social net!</title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<main role="main" class="container">
    <c:if test="${sessionScope.email != null}">
        <c:redirect url="/viewAccount?id=${sessionScope.id}"/>
    </c:if>
    <form class="form-signin" action="<c:url value="/loginUser"/>" method="post">
        <c:set var="infoMessage" scope="session" value="${param.infoMessage}"/>
        <c:if test="${infoMessage == 'alert'}">
            <div class="alert alert-danger form-signin-alert" role="alert">
                <strong>Ошибка!</strong> <br>Ваш логин/пароль не совпадает!
            </div>
        </c:if>
        <c:if test="${infoMessage == 'reg'}">
            <div class="alert alert-success text-alert" role="alert">
                <strong>Поздравляем!</strong> <br>Вы зарегистрированы! Можете войти.
            </div>
        </c:if>
        <h2 class="form-signin-heading">Социальная сеть</h2>
        <label for="inputEmail" class="sr-only">Email</label>
        <input type="email" id="inputEmail" name="inputEmail" class="form-control" placeholder="Email" autofocus>
        <label for="inputPassword" class="sr-only">Пароль</label>
        <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="Пароль">
        <div class="checkbox">
            <label>
                <input type="checkbox" name="rememberMe" value="true"> Запомнить меня
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
    </form>
    <div class="form-signin-alert">
        или<br>
        <a href="<c:url value="/regPage"/>">
            <button type="button" class="btn btn-lg btn-link">Зарегистрироваться!</button>
        </a>
    </div>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<c:url value="/resources/js/jquery-3.3.1.slim.min.js"/>"></script>
<script src="<c:url value="/resources/js/popper.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
</body>
</html>