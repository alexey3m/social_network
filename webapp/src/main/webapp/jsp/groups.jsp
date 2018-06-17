<%@page import="com.getjavajob.training.web1803.service.GroupService" %>
<%@page import="com.getjavajob.training.web1803.common.Group" %>
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
    <link href="resources/css/groups.css" rel="stylesheet" type="text/css"/>
    <title>You friends list Social net!</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main" class="container">
    <jsp:useBean id="groupService" class="com.getjavajob.training.web1803.service.GroupService"/>
    <c:set var="sessionId" scope="page" value="${sessionScope.id}"/>
    <div>
        <h5>My groups</h5>
    </div>
    <c:forEach var="group" items="${groupService.getAllById(sessionId)}">
        <div class="row row-groups">
            <a href="group.jsp?id=${group.id}">
                    ${group.name}
            </a>
        </div>
    </c:forEach>
    <div>
        <h5>All groups</h5>
    </div>
    <c:forEach var="group" items="${groupService.getAll()}">
        <div class="row row-groups">
            <a href="group.jsp?id=${group.id}">
                    ${group.name}
            </a>
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