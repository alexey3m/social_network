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
    <link href="<c:url value="/resources/css/accountMess.css"/>" rel="stylesheet" type="text/css"/>
    <title>Ваши сообщения Social net!</title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<main role="main" class="container">
    <div class="row">
        <div class="col-md-3">
            <h5>Контакты</h5>
            <c:forEach var="account" items="${contacts}">
                <div class="row">
                    <a href="viewAccountMess?assignId=${account.id}">${account.firstName} ${account.lastName}</a>
                </div>
            </c:forEach>
        </div>
        <div class="col-md-9">
            <c:if test="${assignId != 0}">
                <h5>Новое сообщение</h5>
                <div class="card mb-1 box-shadow">
                    <div class="card-header">
                        Новое сообщение для <a
                            href="viewAccount?id=${newMessageAccount.id}">${newMessageAccount.firstName} ${newMessageAccount.lastName}</a>
                    </div>
                    <div class="card-body">
                        <form action="messageAction?action=new&type=account&assignId=${assignId}"
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
                <h5>Все сообщения</h5>
                <c:forEach var="message" items="${allMessages}">
                    <c:set var="messageAccount" value="${allAccountsMessages[message.userCreatorId]}"/>
                    <div class="card mb-1 box-shadow">
                        <div class="card-header">
                            <div class="row">
                                <div class="col">
                                    <p class="blog-post-meta">Отправлено ${message.createDate} пользователем <a
                                            href="viewAccount?id=${messageAccount.id}">${messageAccount.firstName} ${messageAccount.lastName}</a>
                                    </p>
                                </div>
                                <div class="float-right">
                                    <form action="messageAction?action=remove&type=account&assignId=${assignId}&messageId=${message.id}"
                                          method="post">
                                        <button type="submit" class="btn btn-outline-primary">Удалить</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="card-body form-inline">
                            <div class="row">
                                <div class="col-md-4">
                                    <img src="getMessagePhoto?id=${message.id}"
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
<script src="<c:url value="/resources/js/jquery-3.3.1.slim.min.js"/>"></script>
<script src="<c:url value="/resources/js/popper.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
</body>
</html>