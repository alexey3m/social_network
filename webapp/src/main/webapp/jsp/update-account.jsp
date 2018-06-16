<%@page import="com.getjavajob.training.web1803.service.AccountService" %>
<%@page import="com.getjavajob.training.web1803.service.PhoneService" %>
<%@page import="com.getjavajob.training.web1803.common.Account" %>
<%@page import="com.getjavajob.training.web1803.common.Role" %>
<%@page import="com.getjavajob.training.web1803.common.Status" %>
<%@page import="com.getjavajob.training.web1803.common.PhoneType" %>
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

    <title>Update account Social-Network</title>
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
<jsp:useBean id="accountService" class="com.getjavajob.training.web1803.service.AccountService"/>
<c:set var="id" scope="page" value="${param.id}"/>
<c:set var="account" scope="page" value="${accountService.get(id)}"/>
<main role="main" class="container">
    <form class="form-reg" method="post" action="UpdateAccountServlet" enctype="multipart/form-data">
        <h2 class="form-reg-heading">Update account form</h2>
        <input type="hidden" name="inputId" value="${account.id}">
        <label for="inputEmail" class="sr-only">Email</label>
        <input type="text" id="inputEmail" name="inputEmail" class="form-control" placeholder="Email" value="${account.email}" readonly>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="Password" value="${account.password}" required>
        <label for="inputFirstName" class="sr-only">First Name</label>
        <input type="text" id="inputFirstName" name="inputFirstName" class="form-control" placeholder="First name" value="${account.firstName}">
        <label for="inputMiddleName" class="sr-only">Middle Name</label>
        <input type="text" id="inputMiddleName" name="inputMiddleName" class="form-control" placeholder="Middle name" value="${account.middleName}">
        <label for="inputLastName" class="sr-only">Last Name</label>
        <input type="text" id="inputLastName" name="inputLastName" class="form-control" placeholder="Last name" value="${account.lastName}">
        <label for="inputBirthday" class="sr-only">Birthday date</label>
        <input type="date" id="inputBirthday" name="inputBirthday" class="form-control" placeholder="Birthday date" value="${account.birthday}">
        <jsp:useBean id="phoneService" class="com.getjavajob.training.web1803.service.PhoneService"/>
        <c:forEach var="phone" items="${phoneService.getAll(id)}">
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
        <label for="inputPhonePers" class="sr-only">Phone personally</label>
        <input type="text" id="inputPhonePers" name="inputPhonePers" class="form-control" placeholder="Phone personally" value="${phonePers}">
        <label for="inputPhoneWork" class="sr-only">Phone work</label>
        <input type="text" id="inputPhoneWork" name="inputPhoneWork" class="form-control" placeholder="Phone work" value="${phoneWork}">
        <label for="inputPhoneAdd" class="sr-only">Phone additional</label>
        <input type="text" id="inputPhoneAdd" name="inputPhoneAdd" class="form-control" placeholder="Phone additional" value="${phoneAdd}">
        <label for="inputSkype" class="sr-only">Skype</label>
        <input type="text" id="inputSkype" name="inputSkype" class="form-control" placeholder="Skype" value="${account.skype}">
        <label for="inputIcq" class="sr-only">Icq</label>
        <input type="text" id="inputIcq" name="inputIcq" class="form-control" placeholder="Icq" value="${account.icq}">
        <label for="uploadPhoto" class="sr-only">You photo</label>
        <input type="file" id="uploadPhoto" name="uploadPhoto" class="form-control">
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Update me</button>
    </form>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="resources/js/jquery-3.3.1.slim.min.js"></script>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>