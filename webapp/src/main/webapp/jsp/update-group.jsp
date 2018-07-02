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
    <link href="resources/css/update-group.css" rel="stylesheet" type="text/css"/>
    <title>Обновление группы Social-Network</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main" class="container">
    <form class="form-reg" method="post" action="UpdateGroupServlet" enctype="multipart/form-data">
        <h2 class="form-reg-heading">Форма обновления группы</h2>
        <input type="hidden" name="inputId" value="${group.id}">
        <label for="inputName" class="sr-only">Имя</label>
        <input type="text" id="inputName" name="inputName" class="form-control" placeholder="Имя" value="${group.name}"
               readonly>
        <label for="inputInfo" class="sr-only">Информация</label>
        <input type="text" id="inputInfo" name="inputInfo" class="form-control" placeholder="Информация"
               value="${group.info}">
        <label for="uploadPhoto" class="sr-only">Аватар группы</label>
        <input type="file" id="uploadPhoto" name="uploadPhoto" class="form-control"><br>
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