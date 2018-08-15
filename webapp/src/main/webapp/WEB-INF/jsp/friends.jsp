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
    <link href="<c:url value="/resources/css/friends.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/jquery-ui.css"/>" rel="stylesheet" type="text/css"/>
    <title>Мои друзья Social net!</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
<main role="main" class="container">
    <c:if test="${infoMessage == 'friendsAddQueryTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Отлично!</strong> <br>Ваш запрос в друзья с ${actionAccount.firstName} ${actionAccount.lastName} был
            отправлен!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'friendsAcceptTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Успешно!</strong> <br>Теперь вы друзья с ${actionAccount.firstName} ${actionAccount.lastName}!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'friendsRemoveTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Очень жаль!</strong> <br>Теперь вы не друзья с ${actionAccount.firstName} ${actionAccount.lastName}!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'friendsDeclineTrue'}">
        <div class="alert alert-danger text-alert" role="alert">
            Запрос от ${actionAccount.firstName} ${actionAccount.lastName} был отклонен!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'removeRequestTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Очень жаль!</strong> <br>Ваш запрос в друзья с ${actionAccount.firstName} ${actionAccount.lastName}
            был удален!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'friendsFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Упс!</strong> <br>Что-то пошло не так..
        </div>
    </c:if>
    <div>
        <h5>Мои запросы</h5>
    </div>
    <c:forEach var="account" items="${myRequest}">
        <div class="row row-friends">
            <div class="col-3">
                <a href="viewAccount?id=${account.id}">
                        ${account.firstName} ${account.middleName} ${account.lastName}
                </a>
            </div>
            <div class="col-2">
                <form method="post" action="friends?action=removeRequest&actionId=${account.id}">
                    <button type="submit" class="btn btn-sm btn-secondary btn-block">Удалить мой запрос!</button>
                </form>
            </div>
        </div>
    </c:forEach>
    <div>
        <h5>Ожидающие запросы ко мне</h5>
    </div>
    <c:forEach var="account" items="${pendingRequest}">
        <div class="row row-friends">
            <div class="col-3">
                <a href="viewAccount?id=${account.id}">
                        ${account.firstName} ${account.middleName} ${account.lastName}
                </a>
            </div>
            <div class="col-2">
                <form method="post" action="friends?action=accept&actionId=${account.id}">
                    <button type="submit" class="btn btn-sm btn-success btn-block">Принять в друзья!</button>
                </form>
            </div>
            <div class="col-2">
                <form method="post" action="friends?action=decline&actionId=${account.id}">
                    <button type="submit" class="btn btn-sm btn-danger btn-block">Отклонить запрос!</button>
                </form>
            </div>
        </div>
    </c:forEach>
    <div>
        <h5>Друзья</h5>
    </div>
    <c:forEach var="account" items="${friends}">
        <div class="row row-friends">
            <div class="col-3">
                <a href="viewAccount?id=${account.id}">
                        ${account.firstName} ${account.middleName} ${account.lastName}
                </a>
            </div>
            <div class="col-2">
                <form method="post" action="friends?action=remove&actionId=${account.id}">
                    <button type="submit" class="btn btn-sm btn-warning btn-block">Удалить из друзей!</button>
                </form>
            </div>
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