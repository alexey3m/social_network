<%@page import="com.getjavajob.training.web1803.service.AccountService" %>
<%@page import="com.getjavajob.training.web1803.service.GroupService" %>
<%@page import="com.getjavajob.training.web1803.common.Account" %>
<%@page import="java.util.Map" %>
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
    <link href="resources/css/search-result.css" rel="stylesheet" type="text/css"/>
    <title>You friends list Social net!</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main" class="container">
    <jsp:useBean id="accountService" class="com.getjavajob.training.web1803.service.AccountService"/>
    <jsp:useBean id="groupService" class="com.getjavajob.training.web1803.service.GroupService"/>
    <c:set var="searchString" value="${param.inputSearch}"/>
    <div>
        <h5>Search result for string "${searchString}"</h5>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Accounts</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="account" items="${accountService.searchByString(searchString)}">
            <tr>
                <th>
                    <a href="account.jsp?id=${account.id}">${account.firstName} ${account.middleName} ${account.lastName}</a>
                </th>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Groups</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="group" items="${groupService.searchByString(searchString)}">
            <tr>
                <th>
                    <a href="group.jsp?id=${group.id}">${group.name}</a>
                </th>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="resources/js/jquery-3.3.1.slim.min.js"></script>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>