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
    <link href="<c:url value="/resources/css/search-result.css"/>" rel="stylesheet" type="text/css"/>
    <title>Результаты поиска Social net!</title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<main role="main" class="container">
    <div>
        <h5>Результаты поиска по "${searchString}"</h5>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Аккаунты</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="account" items="${findAccounts}">
            <tr>
                <th>
                    <a href="viewAccount?id=${account.id}">${account.firstName} ${account.middleName} ${account.lastName}</a>
                </th>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Группы</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="group" items="${findGroups}">
            <tr>
                <th>
                    <a href="viewGroup?id=${group.id}">${group.name}</a>
                </th>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<c:url value="/resources/js/jquery-3.3.1.slim.min.js"/>"></script>
<script src="<c:url value="/resources/js/popper.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
</body>
</html>