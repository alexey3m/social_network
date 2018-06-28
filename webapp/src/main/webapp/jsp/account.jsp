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
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <!-- Custom styles for this template -->
    <link href="resources/css/account.css" rel="stylesheet" type="text/css"/>
    <title>Account Social net!</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main" class="container">
    <c:if test="${infoMessage == 'updateTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Success!</strong> <br>You account has been updated!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'updateFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Error!</strong> <br>You account was not updated! Try again.
        </div>
    </c:if>
    <c:if test="${account == null}">
        <c:redirect url="AccountViewServlet?id=${sessionId}"/>
    </c:if>
    <c:if test="${infoMessage == 'friendsAddQueryTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Good!</strong> <br>Your friend request with ${actionAccount.firstName} ${actionAccount.lastName} was
            sent!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'friendsAcceptTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Success!</strong> <br>You are now friends with ${actionAccount.firstName} ${actionAccount.lastName}!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'friendsRemoveTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>It's a pity!</strong> <br>Now you are not friends
            with ${actionAccount.firstName} ${actionAccount.lastName}!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'removeRequestTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>It's a pity!</strong> <br>You request to ${actionAccount.firstName} ${actionAccount.lastName} has
            been removed!
        </div>
    </c:if>

    <c:if test="${infoMessage == 'friendsDeclineTrue'}">
        <div class="alert alert-danger text-alert" role="alert">
            Request from ${actionAccount.firstName} ${actionAccount.lastName} has been declined!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'friendsFalse' || infoMessage == 'updateRoleFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Oops!</strong> <br>Something went wrong..!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'updateRoleUser'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Success!</strong> <br>New role ${account.firstName} ${account.lastName} - "USER"!
        </div>
    </c:if>
    <c:if test="${infoMessage == 'updateRoleAdmin'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Success!</strong> <br>New role ${account.firstName} ${account.lastName} - "ADMIN"!
        </div>
    </c:if>

    <div class="row">
        <div class="col-md-3">
            <div class="text-center">
                <img src="getPhoto?id=${id}" onerror="this.src='resources/img/noPhotoAvailable.jpg'" class="img-fluid"
                     alt="Responsive image">
                <div class="control-panel">
                    Control panel<br>
                </div>
                <c:if test="${sessionRole == Role.ADMIN || sessionId == id}">
                    <div class="control-panel">
                        <a href="UpdateAccountViewServlet?id=${id}">
                            <button type="button" class="btn btn-sm btn-primary">Update account</button>
                        </a>
                    </div>
                </c:if>
                <c:if test="${sessionRole == Role.ADMIN && sessionId != id}">
                    <div class="control-panel">
                        <c:if test="${role == Role.ADMIN}">
                            <form method="post" action="UpdateRoleServlet?action=toUser&actionId=${id}">
                                <button type="submit" class="btn btn-sm btn-primary">Change role to "USER"</button>
                            </form>
                        </c:if>
                        <c:if test="${role == Role.USER}">
                            <form method="post" action="UpdateRoleServlet?action=toAdmin&actionId=${id}">
                                <button type="submit" class="btn btn-sm btn-primary">Change role to "ADMIN"</button>
                            </form>
                        </c:if>
                    </div>
                </c:if>
                <c:if test="${sessionId != id && status == Status.UNKNOWN}">
                    <div class="control-panel">
                        <form method="post" action="FriendsServlet?action=add&actionId=${id}">
                            <button type="submit" class="btn btn-primary">Add to friend!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionId != id && status == Status.PENDING && pendingStatus != Status.PENDING}">
                    <div class="control-panel">
                        <form method="post" action="FriendsServlet?action=removeRequest&actionId=${id}">
                            <button type="submit" class="btn btn-secondary">Remove my request!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionId == id && status == Status.DECLINE}">
                    <div class="control-panel">
                        <button type="submit" class="btn btn-danger" disabled>Yor request has been declined!</button>
                    </div>
                </c:if>
                <c:if test="${sessionId != id && status == Status.ACCEPTED}">
                    <div class="control-panel">
                        <form method="post" action="FriendsServlet?action=remove&actionId=${id}">
                            <button type="submit" class="btn btn-warning">Remove from friends!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionId != id && pendingStatus == Status.PENDING}">
                    <div class="control-panel">
                        <form method="post" action="FriendsServlet?action=accept&actionId=${id}">
                            <button type="submit" class="btn btn-success">Add to friend!</button>
                        </form>
                        <form method="post" action="FriendsServlet?action=decline&actionId=${id}">
                            <button type="submit" class="btn btn-danger">Decline request!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionId == id}">
                    <div class="control-panel">
                        <form method="post" action="createGroup.jsp">
                            <button type="submit" class="btn btn-primary">Create group!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${sessionId != id}">
                    <div class="control-panel">
                        <a href="AccountMessViewServlet?assignId=${id}">
                            <button type="submit" class="btn btn-primary">Send message!</button>
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
                <div class="col-5">Birthday:</div>
                <div class="col-5">${account.birthday}</div>
            </div>
            <c:forEach var="phone" items="${account.phones}">
                <div class="row">
                    <div class="col-5">
                        <c:if test="${phone.value == PhoneType.HOME}">Phone personally:</c:if>
                        <c:if test="${phone.value == PhoneType.WORK}">Phone work:</c:if>
                        <c:if test="${phone.value == PhoneType.ADDITIONAL}">Phone additional:</c:if></div>
                    <div class="col-5"><c:out value="${phone.key}"/></div>
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
                <div class="col-5">Registration date:</div>
                <div class="col-5">${account.regDate}</div>
            </div>
            <div class="row">
                <div class="col-5">Role:</div>
                <div class="col-5">${account.role}</div>
            </div>
            <div class="row">
                <hr/>
                <h5>Wall</h5>
                <hr>
            </div>
            <div class="card mb-1 box-shadow">
                <div class="card-body">
                    <form action="MessageServlet?action=new&type=accountWall&assignId=${account.id}"
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
            <c:forEach var="message" items="${messages}">
                <c:set var="messageAccount" value="${messagesAccounts[message.userCreatorId]}"/>
                <div class="card mb-1 box-shadow">
                    <div class="card-header">
                        <div class="row">
                            <div class="col">
                                <p class="blog-post-meta">Posted ${message.createDate} by <a
                                        href="AccountViewServlet?id=${messageAccount.id}">${messageAccount.firstName} ${messageAccount.lastName}</a>
                                </p>
                            </div>
                            <div class="float-right">
                                <c:if test="${sessionRole == Role.ADMIN || sessionId == id}">
                                    <form action="MessageServlet?action=remove&type=accountWall&assignId=${account.id}&messageId=${message.id}"
                                          method="post">
                                        <button type="submit" class="btn btn-outline-primary">Remove</button>
                                    </form>
                                </c:if>
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