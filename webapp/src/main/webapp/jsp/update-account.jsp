<%@ page import="com.getjavajob.training.web1803.common.enums.PhoneType" %>
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
    <link href="resources/css/update-account.css" rel="stylesheet" type="text/css"/>

    <title>Обновление аккаунта Social-Network</title>
</head>

<body>
<jsp:include page="navbar.jsp"/>
<main role="main" class="container">
    <form id="formConfirm" class="form-reg" method="post" action="UpdateAccountServlet" enctype="multipart/form-data">
        <h2 class="form-reg-heading">Форма обновления аккаунта</h2>
        <input type="hidden" name="inputId" value="${account.id}">

        <label for="inputEmail" class="sr-only">Email</label>
        <input type="text" id="inputEmail" name="inputEmail" class="form-control" placeholder="Email"
               value="${account.email}" readonly>

        <label for="inputPassword" class="sr-only">Пароль</label>
        <input type="password" id="inputPassword" name="inputPassword" class="form-control mb-2" placeholder="Пароль"
               value="${account.password}" required>

        <label for="inputLastName" class="sr-only">Фамилия</label>
        <input type="text" id="inputLastName" name="inputLastName" class="form-control" placeholder="Фамилия"
               value="${account.lastName}">

        <label for="inputFirstName" class="sr-only">Имя</label>
        <input type="text" id="inputFirstName" name="inputFirstName" class="form-control" placeholder="Имя"
               value="${account.firstName}">

        <label for="inputMiddleName" class="sr-only">Отчество</label>
        <input type="text" id="inputMiddleName" name="inputMiddleName" class="form-control mb-2" placeholder="Отчество"
               value="${account.middleName}">

        <label for="inputBirthday" class="sr-only">Дата рождения</label>
        <input type="date" id="inputBirthday" name="inputBirthday" class="form-control mb-2" placeholder="Birthday date"
               value="${account.birthday}">
        <c:forEach var="phone" items="${account.phones}">
            <div id="phone" class="input-group mb-2">
                <div class="input-group-append">
                    <input type="number" id="inputPhone" name="inputPhone" class="form-control"
                           placeholder="Телефон" value="${phone.key}">
                </div>
                <div class="form-control">
                    <label for="phoneSel" class="sr-only">Тип телефона</label>
                    <select class="custom-select" id="phoneSel" name="phoneSel">
                        <c:if test="${phone.value == PhoneType.MOBILE}">
                            <option value="0" selected>Мобильный</option>
                            <option value="1">Рабочий</option>
                            <option value="2">Домашний</option>
                        </c:if>
                        <c:if test="${phone.value == PhoneType.WORK}">
                            <option value="0">Мобильный</option>
                            <option value="1" selected>Рабочий</option>
                            <option value="2">Домашний</option>
                        </c:if>
                        <c:if test="${phone.value == PhoneType.HOME}">
                            <option value="0">Мобильный</option>
                            <option value="1">Рабочий</option>
                            <option value="2" selected>Домашний</option>
                        </c:if>
                    </select>
                </div>
                <div class="input-group-append">
                    <button class="btn btn-danger" type="button" onclick="removeRow(this)">Удалить</button>
                </div>
            </div>
        </c:forEach>
        <button type="button" class="btn btn-success mb-2" id="addRowBtn" onclick="addRow()">Добавить телефон</button>
        <label for="inputSkype" class="sr-only">Skype</label>
        <input type="text" id="inputSkype" name="inputSkype" class="form-control" placeholder="Skype"
               value="${account.skype}">
        <label for="inputIcq" class="sr-only">Icq</label>
        <input type="text" id="inputIcq" name="inputIcq" class="form-control" placeholder="Icq" value="${account.icq}">
        <label for="uploadPhoto" class="sr-only">Ваш аватар</label>
        <input type="file" id="uploadPhoto" name="uploadPhoto" class="form-control mb-2">
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Сохранить изменения</button>
    </form>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<script src="resources/js/socnet-custom.js"></script>
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="resources/js/jquery-3.3.1.slim.min.js"></script>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>