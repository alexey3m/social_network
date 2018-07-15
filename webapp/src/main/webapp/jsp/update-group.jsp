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
    <link href="<c:url value="/resources/css/update-group.css"/>" rel="stylesheet" type="text/css"/>
    <title>Обновление группы Social-Network</title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<main role="main" class="container">
    <%--@elvariable id="group" type="com.getjavajob.training.web1803.common.Group"--%>
    <form:form id="formConfirm" class="form-reg" method="post" action="/updateGroup" enctype="multipart/form-data" modelAttribute="group" >
        <h2 class="form-reg-heading">Форма обновления группы</h2>
        <form:input type="hidden" path="id" value="${group.id}"/>
        <form:label path="name" class="sr-only">Имя</form:label>
        <form:input type="text" path="name" class="form-control" placeholder="Name group" value="${group.name}" readonly="true"/>
        <form:label path="info" class="sr-only">Информация</form:label>
        <form:input type="text" path="info" class="form-control" placeholder="Информация" value="${group.info}"/>
        <div class="row">
            <div class="col-md-3">
                <label>Текущее фото группы</label>
                <img src="data:image/jpg;base64, ${encodedPhoto}" onerror="this.src='resources/img/noPhotoAvailable.jpg'" class="img-fluid"
                     alt="Responsive image">
            </div>
            <div class="col">
                <label>Обновить фото</label>
                <input type="file" name="uploadPhoto" class="form-control mb-2"/>
            </div>
        </div>
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Сохранить изменения</button>
    </form:form>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<script src="<c:url value="/resources/js/socnet-custom.js"/>"></script>
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<c:url value="/resources/js/jquery-3.3.1.slim.min.js"/>"></script>
<script src="<c:url value="/resources/js/popper.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
</body>
</html>