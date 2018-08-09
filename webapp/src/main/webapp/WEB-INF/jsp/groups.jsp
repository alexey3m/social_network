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
    <link href="<c:url value="/resources/css/groups.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/jquery-ui.css"/>" rel="stylesheet" type="text/css"/>
    <title>Мои группы Social net!</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
<main role="main" class="container">
    <div>
        <h5>Мои группы</h5>
    </div>
    <c:forEach var="group" items="${myGroups}">
        <div class="row row-groups">
            <a href="viewGroup?id=${group.id}">
                    ${group.name}
            </a>
        </div>
    </c:forEach>
    <div>
        <h5>Все группы Социальной сети</h5>
    </div>
    <c:forEach var="group" items="${allGroups}">
        <div class="row row-groups">
            <a href="viewGroup?id=${group.id}">
                    ${group.name}
            </a>
        </div>
    </c:forEach>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<c:url value="/resources/js/jquery-3.3.1.js"/>"></script>
<script src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/resources/js/popper.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/socnet-custom.js"/>"></script>
</body>
</html>