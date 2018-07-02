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
    <form class="form-reg" method="post" action="UpdateAccountServlet" enctype="multipart/form-data">
        <h2 class="form-reg-heading">Форма обновления аккаунта</h2>
        <input type="hidden" name="inputId" value="${account.id}">

        <label for="inputEmail" class="sr-only">Email</label>
        <input type="text" id="inputEmail" name="inputEmail" class="form-control" placeholder="Email"
               value="${account.email}" readonly>

        <label for="inputPassword" class="sr-only">Пароль</label>
        <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="Пароль"
               value="${account.password}" required>

        <label for="inputLastName" class="sr-only">Фамилия</label>
        <input type="text" id="inputLastName" name="inputLastName" class="form-control" placeholder="Фамилия"
               value="${account.lastName}">

        <label for="inputFirstName" class="sr-only">Имя</label>
        <input type="text" id="inputFirstName" name="inputFirstName" class="form-control" placeholder="Имя"
               value="${account.firstName}">

        <label for="inputMiddleName" class="sr-only">Отчество</label>
        <input type="text" id="inputMiddleName" name="inputMiddleName" class="form-control" placeholder="Отчество"
               value="${account.middleName}">

        <label for="inputBirthday" class="sr-only">Дата рождения</label>
        <input type="date" id="inputBirthday" name="inputBirthday" class="form-control" placeholder="Birthday date"
               value="${account.birthday}">
        <c:forEach var="phone" items="${account.phones}">
            <c:if test="${phone.value == PhoneType.HOME}">
                <c:set var="phonePers" scope="page" value="${phone.key}"/>
            </c:if>
            <c:if test="${phone.value == PhoneType.WORK}">
                <c:set var="phoneWork" scope="page" value="${phone.key}"/>
            </c:if>
            <c:if test="${phone.value == PhoneType.ADDITIONAL}">
                <c:set var="phoneAdd" scope="page" value="${phone.key}"/>
            </c:if>
        </c:forEach>
        <label for="inputPhonePers" class="sr-only">Телефон личный</label>
        <input type="text" id="inputPhonePers" name="inputPhonePers" class="form-control" placeholder="Телефон личный"
               value="${phonePers}">
        <label for="inputPhoneWork" class="sr-only">Телефон рабочий</label>
        <input type="text" id="inputPhoneWork" name="inputPhoneWork" class="form-control" placeholder="Телефон рабочий"
               value="${phoneWork}">
        <label for="inputPhoneAdd" class="sr-only">Телефон дополнительный</label>
        <input type="text" id="inputPhoneAdd" name="inputPhoneAdd" class="form-control"
               placeholder="Телефон дополнительный"
               value="${phoneAdd}">
        <label for="inputSkype" class="sr-only">Skype</label>
        <input type="text" id="inputSkype" name="inputSkype" class="form-control" placeholder="Skype"
               value="${account.skype}">
        <label for="inputIcq" class="sr-only">Icq</label>
        <input type="text" id="inputIcq" name="inputIcq" class="form-control" placeholder="Icq" value="${account.icq}">
        <label for="uploadPhoto" class="sr-only">Ваш аватар</label>
        <input type="file" id="uploadPhoto" name="uploadPhoto" class="form-control">
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Обновить</button>
    </form>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="resources/js/jquery-3.3.1.slim.min.js"></script>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>