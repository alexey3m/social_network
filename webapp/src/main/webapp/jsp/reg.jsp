<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="resources/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link href="resources/css/reg.css" rel="stylesheet" type="text/css"/>

    <title>Registration account Social-Network</title>
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
    <form class="form-reg" method="post" action="RegServlet" enctype="multipart/form-data">

        <h2 class="form-reg-heading">Registration form</h2>
        <label for="inputEmail" class="sr-only">Email</label>
        <input type="text" id="inputEmail" name="inputEmail" class="form-control" placeholder="Email" required>

        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="Password" required>

        <label for="inputFirstName" class="sr-only">First Name</label>
        <input type="text" id="inputFirstName" name="inputFirstName" class="form-control" placeholder="First name">

        <label for="inputMiddleName" class="sr-only">Middle Name</label>
        <input type="text" id="inputMiddleName" name="inputMiddleName" class="form-control" placeholder="Middle name">

        <label for="inputLastName" class="sr-only">Last Name</label>
        <input type="text" id="inputLastName" name="inputLastName" class="form-control" placeholder="Last name">

        <label for="inputBirthday" class="sr-only">Birthday date</label>
        <input type="date" id="inputBirthday" name="inputBirthday" class="form-control" placeholder="Birthday date">

        <label for="inputPhonePers" class="sr-only">Phone personally</label>
        <input type="text" id="inputPhonePers" name="inputPhonePers" class="form-control" placeholder="Phone personally">

        <label for="inputPhoneWork" class="sr-only">Phone work</label>
        <input type="text" id="inputPhoneWork" name="inputPhoneWork" class="form-control" placeholder="Phone work">

        <label for="inputPhoneAdd" class="sr-only">Phone additional</label>
        <input type="text" id="inputPhoneAdd" name="inputPhoneAdd" class="form-control" placeholder="Phone additional">

        <label for="inputSkype" class="sr-only">Skype</label>
        <input type="text" id="inputSkype" name="inputSkype" class="form-control" placeholder="Skype">

        <label for="inputIcq" class="sr-only">Icq</label>
        <input type="text" id="inputIcq" name="inputIcq" class="form-control" placeholder="Icq">

        <label for="uploadPhoto" class="sr-only">You photo</label>
        <input type="file" id="uploadPhoto" name="uploadPhoto" class="form-control">

        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Register me</button>
    </form>

</main><!-- /.container -->

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="resources/js/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="resources/js/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="resources/js/bootstrap.min.js"
        integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
        crossorigin="anonymous"></script>
</body>
</html>