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
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
    <a class="navbar-brand" href="./">Social-Network</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
            aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="friends.jsp">My friends</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="groups.jsp">My groups</a>
            </li>
        </ul>
        <div class="navbar-nav mr-auto text-login">
            <div class="text-login">
                Current user: <c:out value="${sessionScope.userName}"/>
            </div>
            <c:set var="email" scope="page" value="${sessionScope.email}"/>
            <c:if test="${email != null}">
                <div class="control-panel">
                    <form class="form-inline my-2 my-lg-0" action="LogoutServlet">
                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Logout!</button>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</nav>
<main role="main" class="container">
    <c:set var="message" scope="page" value="${param.message}"/>
    <c:set var="oldName" scope="page" value="${param.name}"/>
    <c:if test="${message == 'nameFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Error!</strong> <br>Name "${oldName}" already used!
        </div>
    </c:if>
    <c:if test="${message == 'smFalse'}">
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