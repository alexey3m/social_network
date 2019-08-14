<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <link href="<c:url value="/resources/css/jquery-ui.css"/>" rel="stylesheet" type="text/css"/>
    <title>Создание новой группы Social-Network</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
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
    <%--@elvariable id="group" type="com.ershov.socialnet.common.Group"--%>
    <form:form class="form-reg" method="post" action="/createGroup" enctype="multipart/form-data" modelAttribute="group">
        <h2 class="form-reg-heading">Создание новой группы</h2>
        <form:label path="name" class="sr-only">Имя группы</form:label>
        <form:input type="text" path="name" class="form-control" placeholder="Имя группы" required="required" />
        <form:label path="info" class="sr-only">Информация</form:label>
        <form:input type="text" path="info" class="form-control mb-2" placeholder="Информация" />
        <label>Фото группы</label>
        <input type="file" name="uploadPhoto" class="form-control mb-2"/>
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Создать группу!</button>
    </form:form>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<c:url value="/resources/js/jquery-3.3.1.js"/>"></script>
<script src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/resources/js/popper.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/socnet-custom.js"/>"></script>
</body>
</html>