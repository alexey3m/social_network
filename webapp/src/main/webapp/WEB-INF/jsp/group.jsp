<%@ page import="com.getjavajob.training.web1803.common.enums.Role" %>
<%@ page import="com.getjavajob.training.web1803.common.enums.GroupRole" %>
<%@ page import="com.getjavajob.training.web1803.common.enums.GroupStatus" %>
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
    <link href="<c:url value="/resources/css/group.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/jquery-ui.css"/>" rel="stylesheet" type="text/css"/>
    <title>Группа Social net!</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
<main role="main" class="container">
    <c:if test="${infoMessage == 'regGroup'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Успешно!</strong> <br>Ваша группа создана!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'updateTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Успешно!</strong> <br>Ваша группа была обновлена!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'updateFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Error!</strong> <br>Ваша группа не была обновлена! Попробуйте еще раз.
        </div>
    </c:if>
    <c:if test="${infoMessage == 'acceptTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Успешно!</strong> <br>${actionAccount.firstName} ${actionAccount.lastName} добавлен в группу!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'declineTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            Запрос от ${actionAccount.firstName} ${actionAccount.lastName} был отклонен.
        </div>
    </c:if>
    <c:if test="${infoMessage == 'removeTrue'}">
        <div class="alert alert-success text-alert" role="alert">
                ${actionAccount.firstName} ${actionAccount.lastName} был удален из группы.
        </div>
    </c:if>
    <c:if test="${infoMessage == 'toUserTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Успешно!</strong> <br>${actionAccount.firstName} ${actionAccount.lastName} теперь "Пользователь
            группы".
        </div>
    </c:if>
    <c:if test="${infoMessage == 'toAdminTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Успешно!</strong> <br>${actionAccount.firstName} ${actionAccount.lastName} теперь "Администратор
            группы".
        </div>
    </c:if>
    <c:if test="${infoMessage == 'addPendingTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Успешно!</strong> <br>Ваш запрос отправлен. Необходимо дождаться действия администратора группы.
        </div>
    </c:if>
    <c:if test="${infoMessage == 'removePendingTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            Ваш запрос на добавление в группу был удален.
        </div>
    </c:if>
    <c:if test="${infoMessage == 'leaveGroupTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            Вы покинули группу. До свидания.
        </div>
    </c:if>
    <c:if test="${infoMessage == 'groupActionFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Упс!</strong> <br>Что-то пошло не так..
        </div>
    </c:if>
    <div class="row">
        <div class="col-md-3">
            <div class="text-center">
                <img src="data:image/jpg;base64, ${encodedPhoto}" onerror="this.src='resources/img/no-image-group.png'"
                     class="img-fluid"
                     alt="Responsive image">
                <div class="control-panel">
                    Панель управления<br>
                </div>
                <c:if test="${role == GroupRole.ADMIN || globalRole == Role.ROLE_ADMIN}">
                    <div class="control-panel">
                        <a href="updateGroupPage?id=${groupId}">
                            <button type="button" class="btn btn-sm btn-primary btn-block">Обновить группу</button>
                        </a>
                    </div>
                </c:if>
                <c:if test="${status == GroupStatus.UNKNOWN}">
                    <div class="control-panel">
                        <form method="post"
                              action="groupAction?action=addPending&groupId=${groupId}&actionId=${sessionId}">
                            <button type="submit" class="btn btn-sm btn-primary btn-block">Вступить в группу!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${status == GroupStatus.PENDING}">
                    <div class="control-panel">
                        <form method="post"
                              action="groupAction?action=removeRequest&groupId=${groupId}&actionId=${sessionId}">
                            <button type="submit" class="btn btn-sm btn-secondary btn-block">Удалить мой запрос!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${status == GroupStatus.ACCEPTED}">
                    <div class="control-panel">
                        <form method="post"
                              action="groupAction?action=leaveGroup&groupId=${groupId}&actionId=${sessionId}">
                            <button type="submit" class="btn btn-sm btn-warning btn-block">Покинуть группу!</button>
                        </form>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col-md-9">
            <div class="row">
                <h5>${group.name}</h5>
            </div>
            <div class="row">
                <div class="col-3">Дата создания:</div>
                <div class="col-6">${group.createDate}</div>
            </div>
            <div class="row">
                <div class="col-3">Информация:</div>
                <div class="col-6">${group.info}</div>
            </div>
            <div class="row">
                <div class="col-3">Создатель группы:</div>
                <div class="col-6">
                    <a href="viewAccount?id=${accountCreator.id}">${accountCreator.firstName} ${accountCreator.lastName}</a><br>
                </div>
            </div>
            <c:if test="${role == GroupRole.ADMIN}">
                <div class="row">
                    <br><h6>Ожидающие добавления в группу</h6>
                </div>
                <c:forEach var="currentAccount" items="${pendingMembers}">
                    <div class="row row-friends">
                        <div class="col-4">
                            <a href="viewAccount?id=${currentAccount.id}">
                                    ${currentAccount.firstName} ${currentAccount.middleName} ${currentAccount.lastName}
                            </a>
                        </div>
                        <div class="col-5">
                            <form class="d-inline" method="post"
                                  action="groupAction?action=acceptMember&groupId=${groupId}&actionId=${currentAccount.id}">
                                <button type="submit" class="btn btn-sm btn-success">Добавить в группу!</button>
                            </form>
                            <form class="d-inline" method="post"
                                  action="groupAction?action=declineMember&groupId=${groupId}&actionId=${currentAccount.id}">
                                <button type="submit" class="btn btn-sm btn-danger">Отклонить запрос!</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <div class="row">
                <h6>Члены группы</h6>
            </div>
            <c:forEach var="currentAccount" items="${acceptedMembers}">
                <div class="row row-friends">
                    <div class="col-4">
                        <a href="viewAccount?id=${currentAccount.id}">
                                ${currentAccount.firstName} ${currentAccount.middleName} ${currentAccount.lastName}
                        </a>
                    </div>
                    <div class="col-6">
                        <c:if test="${role == GroupRole.ADMIN && currentAccount.id != sessionId}">
                            <form class="d-inline" method="post"
                                  action="groupAction?action=removeMember&groupId=${groupId}&actionId=${currentAccount.id}">
                                <button type="submit" class="btn btn-sm btn-danger">Удалить из группы!</button>
                            </form>
                            <c:set var="rowRole" scope="page" value="${acceptedMembersRole[currentAccount.id]}"/>
                            <c:if test="${rowRole == GroupRole.ADMIN && currentAccount.id != sessionId}">
                                <form class="d-inline" method="post"
                                      action="groupAction?action=toUser&groupId=${groupId}&actionId=${currentAccount.id}">
                                    <button type="submit" class="btn btn-sm btn-primary">Установить роль "Пользователь
                                        группы"
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${rowRole == GroupRole.USER && currentAccount.id != sessionId}">
                                <form class="d-inline" method="post"
                                      action="groupAction?action=toAdmin&groupId=${groupId}&actionId=${currentAccount.id}">
                                    <button type="submit" class="btn btn-sm btn-primary">Установить роль "Администратор
                                        группы"
                                    </button>
                                </form>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${status == GroupStatus.ACCEPTED}">
                <div class="row">
                    <hr/>
                    <h5>Стена группы</h5>
                    <hr>
                </div>
                <div class="card mb-1 box-shadow">
                    <div class="card-body">
                        <form action="messageAction?action=new&type=groupWall&assignId=${group.id}"
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
                                    <c:if test="${role == GroupRole.ADMIN || globalRole == Role.ROLE_ADMIN}">
                                        <form action="messageAction?action=remove&type=groupWall&assignId=${group.id}&messageId=${message.id}"
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
            </c:if>
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