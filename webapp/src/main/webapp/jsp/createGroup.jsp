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
    <link href="resources/css/createGroup.css" rel="stylesheet" type="text/css"/>
    <title>Create new group Social-Network</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main" class="container">
    <c:set var="infoMessage" scope="page" value="${param.infoMessage}"/>
    <c:set var="oldName" scope="page" value="${param.name}"/>
    <c:if test="${infoMessage == 'nameFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Error!</strong> <br>Name "${oldName}" already used!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'smFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Error!</strong> <br>Something went wrong!
        </div>
    </c:if>
    <form class="form-reg" method="post" action="CreateGroupServlet" enctype="multipart/form-data">
        <h2 class="form-reg-heading">Create group form</h2>
        <label for="inputName" class="sr-only">Email</label>
        <input type="text" id="inputName" name="inputName" class="form-control" placeholder="Name" required>
        <label for="inputInfo" class="sr-only">Information</label>
        <input type="text" id="inputInfo" name="inputInfo" class="form-control" placeholder="Info">
        <label for="uploadPhoto" class="sr-only">Group photo</label>
        <input type="file" id="uploadPhoto" name="uploadPhoto" class="form-control"><br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Create group!</button>
    </form>
</main><!-- /.container -->

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="resources/js/jquery-3.3.1.slim.min.js"></script>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>