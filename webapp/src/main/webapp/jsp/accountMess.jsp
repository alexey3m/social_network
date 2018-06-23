<%@page import="com.getjavajob.training.web1803.service.MessageService" %>
<%@page import="com.getjavajob.training.web1803.service.AccountService" %>
<%@page import="com.getjavajob.training.web1803.common.Message" %>
<%@page import="com.getjavajob.training.web1803.common.Account" %>
<%@page import="com.getjavajob.training.web1803.common.MessageType" %>
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
    <link href="resources/css/accountMess.css" rel="stylesheet" type="text/css"/>
    <title>You messages Social net!</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main" class="container">
    <jsp:useBean id="messageService" class="com.getjavajob.training.web1803.service.MessageService"/>
    <jsp:useBean id="accountService" class="com.getjavajob.training.web1803.service.AccountService"/>
    <c:set var="sessionId" scope="page" value="${sessionScope.id}"/>
    <c:set var="assignId" scope="page" value="${param.assignId}"/>
    <c:if test="${assignId == null}">
        <c:set var="assignId" scope="page" value="${param.id}"/>
    </c:if>
    <c:set var="newMessageAccount" value="${accountService.get(assignId)}"/>
    <div class="row">
        <div class="col-md-3">
            <h5>Contacts</h5>
            <c:forEach var="accountId" items="${messageService.getAllAccountIdDialog(sessionId)}">
                <c:set var="account" value="${accountService.get(accountId)}"/>
                <div class="row">
                    <a href="accountMess.jsp?assignId=${account.id}">${account.firstName} ${account.lastName}</a>
                </div>
            </c:forEach>
        </div>
        <div class="col-md-9">
            <c:if test="${assignId != null}">
                <h5>New message</h5>
                <div class="card mb-1 box-shadow">
                    <div class="card-header">
                        To <a href="account.jsp?id=${newMessageAccount.id}">${newMessageAccount.firstName} ${newMessageAccount.lastName}</a>
                    </div>
                    <div class="card-body">
                        <form action="MessageServlet?action=new&type=account&assignId=${assignId}"
                              method="post" enctype="multipart/form-data">
                            <div class="row">
                                <label for="inputNewMessage" class="sr-only">New message</label>
                                <textarea class="form-control" id="inputNewMessage" name="inputNewMessage" rows="3"
                                          placeholder="New message"></textarea>
                            </div>
                            <div class="row blog-post">
                                <div class="col">
                                    <input type="file" id="uploadImage" name="uploadImage" class="form-control-file">
                                </div>
                                <div class="float-right">
                                    <button type="submit" class="btn btn-outline-primary">Send message</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <h5>Messages</h5>
                <c:forEach var="messageEntry" items="${messageService.getAllByCurrentIdAssignId(sessionId, assignId)}">
                    <c:set var="message" value="${messageService.get(messageEntry.key)}"/>

                    <c:set var="messageAccount" value="${accountService.get(message.userCreatorId)}"/>
                    <div class="card mb-1 box-shadow">
                        <div class="card-header">
                            <div class="row">
                                <div class="col">
                                    <p class="blog-post-meta">Posted ${message.createDate} by <a
                                            href="account.jsp?id=${messageAccount.id}">${messageAccount.firstName} ${messageAccount.lastName}</a>
                                    </p>
                                </div>
                                <div class="float-right">
                                    <form action="MessageServlet?action=remove&type=account&assignId=${assignId}&messageId=${message.id}"
                                          method="post">
                                        <button type="submit" class="btn btn-outline-primary">Remove</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="card-body form-inline">
                            <div class="row">
                                <div class="col-md-4">
                                    <img src="GetImageMessageServlet?id=${message.id}"
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
<script src="resources/js/jquery-3.3.1.slim.min.js"></script>
<script src="resources/js/popper.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>