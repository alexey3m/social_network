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
    <link href="<c:url value="/resources/css/update-group.css"/>" rel="stylesheet" type="text/css"/>
    <title>Обновление группы Social-Network</title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<main role="main" class="container">
    <form id="formConfirm" class="form-reg" method="post" action="<c:url value="updateGroup"/>" enctype="multipart/form-data">
        <h2 class="form-reg-heading">Форма обновления группы</h2>
        <input type="hidden" name="inputId" value="${group.id}">
        <label for="inputName" class="sr-only">Имя</label>
        <input type="text" id="inputName" name="inputName" class="form-control" placeholder="Имя" value="${group.name}"
               readonly>
        <label for="inputInfo" class="sr-only">Информация</label>
        <input type="text" id="inputInfo" name="inputInfo" class="form-control" placeholder="Информация"
               value="${group.info}">
        <div class="row">
            <div class="col-md-3">
                <label for="uploadPhoto">Текущее фото группы</label>
                <img src="data:image/jpg;base64, ${encodedPhoto}" onerror="this.src='resources/img/no-image-group.png'" class="img-fluid"
                     alt="Responsive image">
            </div>
            <div class="col">
                <label for="uploadPhoto">Обновить фото</label>
                <input type="file" id="uploadPhoto" name="uploadPhoto" class="form-control mb-2">
            </div>
        </div>        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Сохранить изменения</button>
    </form>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<c:url value="/resources/js/jquery-3.3.1.slim.min.js"/>"></script>
<script src="<c:url value="/resources/js/popper.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
</body>
</html>