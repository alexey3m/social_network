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
    <link href="resources/css/reg.css" rel="stylesheet" type="text/css"/>

    <title>Регистрация аккаунта Social-Network</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main" class="container">
    <c:set var="infoMessage" scope="page" value="${param.infoMessage}"/>
    <c:if test="${infoMessage == 'emailFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Ошибка!</strong> <br>Данная электронная почта уже используется!
        </div>
    </c:if>
    <form class="form-reg" method="post" action="RegServlet" enctype="multipart/form-data">

        <h2 class="form-reg-heading">Форма регистрации</h2>
        <label for="inputEmail" class="sr-only">Email</label>
        <input type="text" id="inputEmail" name="inputEmail" class="form-control" placeholder="Email" required>

        <label for="inputPassword" class="sr-only">Пароль</label>
        <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="Пароль"
               required>

        <label for="inputLastName" class="sr-only">Фамилия</label>
        <input type="text" id="inputLastName" name="inputLastName" class="form-control" placeholder="Фамилия">

        <label for="inputFirstName" class="sr-only">Имя</label>
        <input type="text" id="inputFirstName" name="inputFirstName" class="form-control" placeholder="Имя">

        <label for="inputMiddleName" class="sr-only">Отчество</label>
        <input type="text" id="inputMiddleName" name="inputMiddleName" class="form-control" placeholder="Отчество">

        <label for="inputBirthday" class="sr-only">Дата рождения</label>
        <input type="date" id="inputBirthday" name="inputBirthday" class="form-control" placeholder="Дата рождения">

        <div id="phone" class="input-group mb-2">
            <div class="input-group-append">
                <input type="number" id="inputPhone" name="inputPhone" class="form-control" placeholder="Телефон">
            </div>
            <div class="form-control">
                <label for="phoneSel" class="sr-only">Тип телефона</label>
                <select class="custom-select" id="phoneSel" name="phoneSel">
                    <option value="0">Мобильный</option>
                    <option value="1">Рабочий</option>
                    <option value="2">Домашний</option>
                </select>
            </div>
            <div class="input-group-append">
                <button class="btn btn-danger" type="button" onclick="removeRow(this)">Удалить</button>
            </div>
        </div>
        <button type="button" class="btn btn-success mb-2" id="addRowBtn" onclick="addRow()">Добавить телефон</button>

        <label for="inputSkype" class="sr-only">Skype</label>
        <input type="text" id="inputSkype" name="inputSkype" class="form-control" placeholder="Skype">

        <label for="inputIcq" class="sr-only">Icq</label>
        <input type="text" id="inputIcq" name="inputIcq" class="form-control" placeholder="Icq">

        <label for="uploadPhoto" class="sr-only">Ваш аватар</label>
        <input type="file" id="uploadPhoto" name="uploadPhoto" class="form-control">

        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Регистрация!</button>
    </form>

</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="resources/js/jquery-3.3.1.slim.min.js"></script>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="../resources/js/socnet-custom.js"></script>
</body>
</html>