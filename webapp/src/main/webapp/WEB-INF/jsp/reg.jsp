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
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <!-- Custom styles for this template -->
    <link href="<c:url value="/css/reg.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/css/jquery-ui.css"/>" rel="stylesheet" type="text/css"/>
    <title>Регистрация аккаунта Social-Network</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
<main role="main" class="container">
    <c:set var="infoMessage" scope="page" value="${param.infoMessage}"/>
    <c:if test="${infoMessage == 'emailFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Ошибка!</strong> <br>Данная электронная почта уже используется!
        </div>
    </c:if>
    <%--@elvariable id="account" type="com.getjavajob.training.web1803.common.Account"--%>
    <form:form class="form-reg" method="post" action="/reg" enctype="multipart/form-data" modelAttribute="account" onsubmit="updateIndexedInputNames();">
        <h2 class="form-reg-heading">Форма регистрации</h2>
        <form:label path="email" class="sr-only">Email</form:label>
        <form:input type="text" path="email" class="form-control" placeholder="Email" required="required" />
        <form:label path="password" class="sr-only">Пароль</form:label>
        <form:input type="password" path="password" class="form-control mb-2" placeholder="Пароль"/>
        <form:label path="lastName" class="sr-only">Фамилия</form:label>
        <form:input type="text" path="lastName" class="form-control" placeholder="Фамилия" />
        <form:label path="firstName" class="sr-only">Имя</form:label>
        <form:input type="text" path="firstName" class="form-control" placeholder="Имя" />
        <form:label path="middleName" class="sr-only">Отчество</form:label>
        <form:input type="text" path="middleName" class="form-control mb-2" placeholder="Отчество" />
        <label for="birthday" class="sr-only">Дата рождения</label>
        <input type="date" name="birthday" id="birthday" class="form-control mb-2" placeholder="Birthday date" />
            <div id="phone" class="input-group mb-2 phoneClass">
                <div class="input-group-append">
                    <input type="number" class="form-control phoneClassInput" placeholder="Телефон" />
                </div>
                <div class="form-control">
                    <label class="sr-only">Тип телефона</label>
                    <select class="custom-select phoneClassSelect" >
                            <option value="MOBILE">Мобильный</option>
                            <option value="WORK">Рабочий</option>
                            <option value="HOME">Домашний</option>
                    </select>
                </div>
                <div class="input-group-append">
                    <button class="btn btn-danger" type="button" onclick="removeRow(this)">Удалить</button>
                </div>
            </div>
        <button type="button" class="btn btn-success mb-2" id="addRowBtn" onclick="addRow()">Добавить телефон</button>
        <%--<form:label path="skype" class="sr-only">Skype</form:label>--%>
        <form:input type="text" path="skype" class="form-control" placeholder="Skype" />
        <%--<form:label path="icq" class="sr-only">Icq</form:label>--%>
        <form:input type="text" path="icq" class="form-control" placeholder="Icq" />
        <label>Ваше фото</label>
        <input type="file" name="uploadPhoto" class="form-control mb-2"/>
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Регистрация!</button>
    </form:form>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<c:url value="/js/jquery-3.3.1.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/popper.min.js"/>"></script>
<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/js/socnet-custom.js"/>"></script>
</body>
</html>