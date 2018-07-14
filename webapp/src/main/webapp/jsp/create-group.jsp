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
    <link href="<c:url value="/resources/css/createGroup.css"/>" rel="stylesheet" type="text/css"/>
    <title>Создание новой группы Social-Network</title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<main role="main" class="container">
    <c:set var="infoMessage" scope="page" value="${param.infoMessage}"/>
    <c:set var="oldName" scope="page" value="${param.name}"/>
    <c:if test="${infoMessage == 'nameFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Ошибка!</strong> <br>Имя группы "${oldName}" уже используется!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'smFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Ошибка!</strong> <br>Что-то пошло не так..
        </div>
    </c:if>
    <form class="form-reg" method="post" action="<c:url value="/createGroup"/>" enctype="multipart/form-data">
        <h2 class="form-reg-heading">Создание новой группы</h2>
        <label for="inputName" class="sr-only">Имя группы</label>
        <input type="text" id="inputName" name="inputName" class="form-control" placeholder="Имя группы" required>
        <label for="inputInfo" class="sr-only">Информация</label>
        <input type="text" id="inputInfo" name="inputInfo" class="form-control" placeholder="Информация">
        <label for="uploadPhoto" class="sr-only">Аватар группы</label>
        <input type="file" id="uploadPhoto" name="uploadPhoto" class="form-control"><br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Создать группу!</button>
    </form>
</main><!-- /.container -->

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<c:url value="/resources/js/jquery-3.3.1.slim.min.js"/>"></script>
<script src="<c:url value="/resources/js/popper.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
</body>
</html>