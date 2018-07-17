<%@ page import="com.getjavajob.training.web1803.common.enums.PhoneType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <!-- Custom styles for this template -->
    <link href="<c:url value="/resources/css/update-account.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/jquery-ui.css"/>" rel="stylesheet" type="text/css"/>
    <title>Обновление аккаунта Social-Network</title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<main role="main" class="container">
    <%--@elvariable id="account" type="com.getjavajob.training.web1803.common.Account"--%>
    <form:form id="formConfirm" class="form-reg" method="post" action="/updateAccount" enctype="multipart/form-data" modelAttribute="account" onsubmit="updateIndexedInputNames();">
        <h2 class="form-reg-heading">Форма обновления аккаунта</h2>
        <form:input type="hidden" path="id" value="${account.id}"/>
        <form:label path="email" class="sr-only">Email</form:label>
        <form:input type="text" path="email" class="form-control" placeholder="Email" value="${account.email}" />
        <form:label path="password" class="sr-only">Пароль</form:label>
        <form:input type="password" path="password" class="form-control mb-2" placeholder="Пароль" value="${account.password}" />
        <form:label path="lastName" class="sr-only">Фамилия</form:label>
        <form:input type="text" path="lastName" class="form-control" placeholder="Фамилия" value="${account.lastName}"/>
        <form:label path="firstName" class="sr-only">Имя</form:label>
        <form:input type="text" path="firstName" class="form-control" placeholder="Имя" value="${account.firstName}"/>
        <form:label path="middleName" class="sr-only">Отчество</form:label>
        <form:input type="text" path="middleName" class="form-control mb-2" placeholder="Отчество" value="${account.middleName}"/>
        <label for="birthday" class="sr-only">Дата рождения</label>
        <input type="date" name="birthday" id="birthday" class="form-control mb-2" placeholder="Birthday date" value="${account.birthday}"/>
        <c:forEach items="${account.phones}" var="phone" varStatus="status">
            <div id="phone" class="input-group mb-2 phoneClass">
                <div class="input-group-append">
                    <input type="number" class="form-control phoneClassInput" placeholder="Телефон" value="${phone.number}"/>
                </div>
                <div class="form-control">
                    <label class="sr-only">Тип телефона</label>
                    <select class="custom-select phoneClassSelect" >
                        <c:if test="${phone.phoneType == PhoneType.MOBILE}">
                            <option value="MOBILE" selected>Мобильный</option>
                            <option value="WORK">Рабочий</option>
                            <option value="HOME">Домашний</option>
                        </c:if>
                        <c:if test="${phone.phoneType == PhoneType.WORK}">
                            <option value="MOBILE">Мобильный</option>
                            <option value="WORK" selected>Рабочий</option>
                            <option value="HOME">Домашний</option>
                        </c:if>
                        <c:if test="${phone.phoneType == PhoneType.HOME}">
                            <option value="0">Мобильный</option>
                            <option value="WORK">Рабочий</option>
                            <option value="HOME" selected>Домашний</option>
                        </c:if>
                    </select>
                </div>
                <div class="input-group-append">
                    <button class="btn btn-danger" type="button" onclick="removeRow(this)">Удалить</button>
                </div>
            </div>
        </c:forEach>
        <button type="button" class="btn btn-success mb-2" id="addRowBtn" onclick="addRow()">Добавить телефон</button>
        <form:label path="skype" class="sr-only">Skype</form:label>
        <form:input type="text" path="skype" class="form-control" placeholder="Skype" value="${account.skype}"/>
        <form:label path="icq" class="sr-only">Icq</form:label>
        <form:input type="text" path="icq" class="form-control" placeholder="Icq" value="${account.icq}"/>
        <div class="row">
            <div class="col-md-3">
                <label>Ваше текущее фото</label>
                <img src="data:image/jpg;base64, ${encodedPhoto}" onerror="this.src='resources/img/noPhotoAvailable.jpg'" class="img-fluid"
                     alt="Responsive image">
            </div>
            <div class="col">
                <label>Обновить фото</label>
                <input type="file" name="photoUpdate" class="form-control mb-2"/>
            </div>
        </div>
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Сохранить изменения</button>
    </form:form>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<script src="<c:url value="/resources/js/socnet-custom.js"/>"></script>
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<c:url value="/resources/js/jquery-3.3.1.js"/>"></script>
<script src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/resources/js/popper.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/socnet-custom.js"/>"></script>
</body>
</html>