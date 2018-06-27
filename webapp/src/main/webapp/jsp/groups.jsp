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
    <div>
        <h5>My groups</h5>
    </div>
    <c:forEach var="group" items="${myGroups}">
        <div class="row row-groups">
            <a href="GroupViewServlet?id=${group.id}">
                    ${group.name}
            </a>
        </div>
    </c:forEach>
    <div>
        <h5>All groups</h5>
    </div>
    <c:forEach var="group" items="${allGroups}">
        <div class="row row-groups">
            <a href="GroupViewServlet?id=${group.id}">
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