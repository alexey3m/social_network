<%@ page import="com.getjavajob.training.web1803.common.enums.Status" %>
<%@ page import="com.getjavajob.training.web1803.common.enums.Role" %>
<%@ page import="com.getjavajob.training.web1803.common.enums.PhoneType" %>
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
    <link href="<c:url value="/resources/css/account.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/jquery-ui.css"/>" rel="stylesheet" type="text/css"/>

    <title>Аккаунт Social net!</title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<main role="main" class="container">
    <c:if test="${infoMessage == 'updateTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Успешно!</strong> <br>Ваш аккаунт был обновлен!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'updateFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Ошибка!</strong> <br>Ваш аккаунт не был обновлен! Попробуйте еще раз.
        </div>
    </c:if>
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
    <c:if test="${infoMessage == 'removeRequestTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Очень жаль!</strong> <br>Ваш запрос в друзья с ${actionAccount.firstName} ${actionAccount.lastName}
            был удален!
        </div>
    </c:if>

    <c:if test="${infoMessage == 'friendsDeclineTrue'}">
        <div class="alert alert-danger text-alert" role="alert">
            Запрос от ${actionAccount.firstName} ${actionAccount.lastName} был отклонен!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'friendsFalse' || infoMessage == 'updateRoleFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Упс!</strong> <br>Что-то пошло не так..
        </div>
    </c:if>
    <c:if test="${infoMessage == 'updateRoleUser'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Успешно!</strong> <br>Новая роль ${account.firstName} ${account.lastName} - "Пользователь"!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'updateRoleAdmin'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Успешно!</strong> <br>Новая роль ${account.firstName} ${account.lastName} - "Администратор"!
        </div>
    </c:if>

    <div class="row">
        <div class="col-md-3">
            <div class="text-center">
                <img src="data:image/jpg;base64, ${encodedPhoto}"
                     onerror="this.src='resources/img/noPhotoAvailable.jpg'" class="img-fluid"
                     alt="Responsive image">
                <div class="control-panel">
                    Панель управления<br>
                </div>
                <c:if test="${sessionRole == Role.ADMIN || sessionId == id}">
                    <div class="control-panel">
                        <a href="updateAccountPage?id=${id}">
                            <button type="button" class="btn btn-sm btn-primary">Обновить аккаунт</button>
                        </a>
                    </div>
                </c:if>
                <c:if test="${sessionRole == Role.ADMIN && sessionId != id}">
                    <div class="control-panel">
                        <c:if test="${role == Role.ADMIN}">
                            <form method="post" action="updateRole?action=toUser&actionId=${id}">
                                <button type="submit" class="btn btn-sm btn-primary">Изменить роль на "Пользователь"
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${role == Role.USER}">
                            <form method="post" action="updateRole?action=toAdmin&actionId=${id}">
                                <button type="submit" class="btn btn-sm btn-primary">Изменить роль на "Администратор"
                                </button>
                            </form>
                        </c:if>
                    </div>
                </c:if>
                <c:if test="${sessionId != id && status == Status.UNKNOWN}">
                    <div class="control-panel">
                        <form method="post" action="accountFriends?action=add&actionId=${id}">
                            <button type="submit" class="btn btn-primary">Добавить в друзья!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionId != id && status == Status.PENDING && pendingStatus != Status.PENDING}">
                    <div class="control-panel">
                        <form method="post" action="friends?action=removeRequest&actionId=${id}">
                            <button type="submit" class="btn btn-secondary">Удалить мой запрос!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionId == id && status == Status.DECLINE}">
                    <div class="control-panel">
                        <button type="submit" class="btn btn-danger" disabled>Ваш запрос был отклонен!</button>
                    </div>
                </c:if>
                <c:if test="${sessionId != id && status == Status.ACCEPTED}">
                    <div class="control-panel">
                        <form method="post" action="accountFriends?action=remove&actionId=${id}">
                            <button type="submit" class="btn btn-warning">Удалить из друзей!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionId != id && pendingStatus == Status.PENDING}">
                    <div class="control-panel">
                        <form method="post" action="accountFriends?action=accept&actionId=${id}">
                            <button type="submit" class="btn btn-success">Принять в друзья!</button>
                        </form>
                        <form method="post" action="accountFriends?action=decline&actionId=${id}">
                            <button type="submit" class="btn btn-danger">Отклонить запрос!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionId == id}">
                    <div class="control-panel">
                        <form method="get" action="createGroupPage">
                            <button type="submit" class="btn btn-primary">Создать группу!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionId != id}">
                    <div class="control-panel">
                        <a href="viewAccountMess?assignId=${id}">
                            <button type="submit" class="btn btn-primary">Отправить сообщение!</button>
                        </a>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col-md-9">
            <div class="row">
                <h5>${account.firstName} ${account.middleName} ${account.lastName}</h5>
            </div>
            <div class="row">
                <div class="col-5">День рождения:</div>
                <div class="col-5">${account.birthday}</div>
            </div>
            <c:forEach var="phone" items="${account.phones}">
                <div class="row">
                    <div class="col-5">
                        <c:if test="${phone.phoneType == PhoneType.MOBILE}">Телефон мобильный:</c:if>
                        <c:if test="${phone.phoneType == PhoneType.WORK}">Телефон рабочий:</c:if>
                        <c:if test="${phone.phoneType == PhoneType.HOME}">Телефон домашний:</c:if></div>
                    <div class="col-5"><c:out value="${phone.number}"/></div>
                </div>
            </c:forEach>
            <div class="row">
                <div class="col-5">ICQ:</div>
                <div class="col-5">${account.icq}</div>
            </div>
            <div class="row">
                <div class="col-5">Skype:</div>
                <div class="col-5">${account.skype}</div>
            </div>
            <div class="row">
                <div class="col-5">Дата регистрации:</div>
                <div class="col-5">${account.regDate}</div>
            </div>
            <div class="row">
                <div class="col-5">Роль:</div>
                <div class="col-5">${account.role}</div>
            </div>
            <div class="row">
                <hr/>
                <h5>Стена аккаунта</h5>
                <hr>
            </div>
            <div class="card mb-1 box-shadow">
                <div class="card-body">
                    <form action="messageAction?action=new&type=accountWall&assignId=${account.id}"
                          method="post" enctype="multipart/form-data">
                        <div class="row">
                            <label for="inputNewMessage" class="sr-only">Новое сообщение</label>
                            <textarea class="form-control" id="inputNewMessage" name="inputNewMessage" rows="3"
                                      placeholder="Сообщение"></textarea>
                        </div>
                        <div class="row blog-post">
                            <div class="col">
                                <input type="file" id="uploadImage" name="uploadImage" class="form-control-file">
                            </div>
                            <div class="float-right">
                                <button type="submit" class="btn btn-outline-primary">Отправить</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <c:forEach var="message" items="${messages}">
                <c:set var="messageAccount" value="${messagesAccounts[message.userCreatorId]}"/>
                <div class="card mb-1 box-shadow">
                    <div class="card-header">
                        <div class="row">
                            <div class="col">
                                <p class="blog-post-meta">Отправлено ${message.dateCreate} пользователем <a
                                        href="viewAccount?id=${messageAccount.id}">${messageAccount.firstName} ${messageAccount.lastName}</a>
                                </p>
                            </div>
                            <div class="float-right">
                                <c:if test="${sessionRole == Role.ADMIN || sessionId == id}">
                                    <form action="messageAction?action=remove&type=accountWall&assignId=${account.id}&messageId=${message.id}"
                                          method="post">
                                        <button type="submit" class="btn btn-outline-primary">Удалить</button>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="card-body form-inline">
                        <div class="row">
                            <div class="col-md-4">
                                <img src="<c:url value="getMessagePhoto"/>?id=${message.id}"
                                     onerror="this.src='resources/img/no-image-group.png'" class="img-fluid"
                                     alt="Responsive image">
                            </div>
                            <div class="col-md-7">
                                <p>${message.text}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
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