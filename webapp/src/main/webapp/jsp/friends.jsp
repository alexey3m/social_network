<%@page import="com.getjavajob.training.web1803.service.AccountService" %>
<%@page import="com.getjavajob.training.web1803.service.RelationshipService" %>
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
    <link href="resources/css/friends.css" rel="stylesheet" type="text/css"/>
    <title>You friends list Social net!</title>
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
    <c:set var="id" scope="page" value="${sessionScope.id}"/>
    <jsp:useBean id="relService" class="com.getjavajob.training.web1803.service.RelationshipService"/>
    <jsp:useBean id="accountService" class="com.getjavajob.training.web1803.service.AccountService"/>
    <c:set var="message" scope="page" value="${param.message}"/>
    <c:set var="actionId" scope="page" value="${param.actionId}"/>
    <c:set var="actionAccount" scope="page" value="${accountService.get(actionId)}"/>
    <c:if test="${message == 'friendsAddQueryTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Good!</strong> <br>Your friend request with ${actionAccount.firstName} ${actionAccount.lastName} was sent!
        </div>
    </c:if>
    <c:if test="${message == 'friendsAcceptTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Success!</strong> <br>You are now friends with ${actionAccount.firstName} ${actionAccount.lastName}!
        </div>
    </c:if>
    <c:if test="${message == 'friendsRemoveTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>It's a pity!</strong> <br>Now you are not friends with ${actionAccount.firstName} ${actionAccount.lastName}!
        </div>
    </c:if>
    <c:if test="${message == 'friendsDeclineTrue'}">
        <div class="alert alert-danger text-alert" role="alert">
            Request from ${actionAccount.firstName} ${actionAccount.lastName} has been declined!
        </div>
    </c:if>
    <c:if test="${message == 'removeRequestTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>It's a pity!</strong> <br>You request to ${actionAccount.firstName} ${actionAccount.lastName} has been removed!
        </div>
    </c:if>
    <c:if test="${message == 'friendsFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Oops!</strong> <br>Oops! Something went wrong..!
        </div>
    </c:if>
    <div>
        <h5>My requests</h5>
    </div>
    <c:forEach var="account" items="${relService.getFriendRequestsFromId(id)}">
        <div class="row row-friends">
            <div class="col-3">
                <a href="account.jsp?id=${account.id}">
                        ${account.firstName} ${account.middleName} ${account.lastName}
                </a>
            </div>
            <div class="col-3">
                <form method="post" action="FriendsServlet?action=removeRequest&actionId=${account.id}">
                    <button type="submit" class="btn btn-secondary">Remove my request!</button>
                </form>
            </div>
        </div>
    </c:forEach>
    <div>
        <h5>Pending requests to me</h5>
    </div>
    <c:forEach var="account" items="${relService.getPendingRequestsToId(id)}">
        <div class="row row-friends">
            <div class="col-3">
                <a href="account.jsp?id=${account.id}">
                        ${account.firstName} ${account.middleName} ${account.lastName}
                </a>
            </div>
            <div class="col-3">
                <form class="d-inline" method="post" action="FriendsServlet?action=accept&actionId=${account.id}">
                    <button type="submit" class="btn btn-sm btn-success">Add to friend!</button>
                </form>
                <form class="d-inline" method="post" action="FriendsServlet?action=decline&actionId=${account.id}">
                    <button type="submit" class="btn btn-sm btn-danger">Decline request!</button>
                </form>
            </div>
        </div>
    </c:forEach>
    <div>
        <h5>Friends</h5>
    </div>
    <c:forEach var="account" items="${relService.getAcceptedFriendsList(id)}">
        <div class="row row-friends">
            <div class="col-3">
                <a href="account.jsp?id=${account.id}">
                        ${account.firstName} ${account.middleName} ${account.lastName}
                </a>
            </div>
            <div class="col-3">
                <form method="post" action="FriendsServlet?action=remove&actionId=${account.id}">
                    <button type="submit" class="btn btn-sm btn-warning">Remove from friend!</button>
                </form>
            </div>
        </div>
    </c:forEach>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="resources/js/jquery-3.3.1.slim.min.js"></script>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>