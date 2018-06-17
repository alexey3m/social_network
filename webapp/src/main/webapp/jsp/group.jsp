<%@page import="com.getjavajob.training.web1803.service.AccountService" %>
<%@page import="com.getjavajob.training.web1803.service.GroupService" %>
<%@page import="com.getjavajob.training.web1803.common.Account" %>
<%@page import="com.getjavajob.training.web1803.common.Group" %>
<%@page import="com.getjavajob.training.web1803.common.Role" %>
<%@page import="com.getjavajob.training.web1803.common.GroupRole" %>
<%@page import="com.getjavajob.training.web1803.common.Status" %>
<%@page import="com.getjavajob.training.web1803.common.GroupStatus" %>
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
    <link href="resources/css/group.css" rel="stylesheet" type="text/css"/>
    <title>Group Social net!</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main" class="container">
    <c:set var="groupId" scope="page" value="${param.id}"/>
    <c:set var="message" scope="page" value="${param.message}"/>
    <c:set var="actionId" scope="page" value="${param.actionId}"/>
    <jsp:useBean id="accountService" class="com.getjavajob.training.web1803.service.AccountService"/>
    <c:set var="actionAccount" scope="page" value="${accountService.get(actionId)}"/>

    <c:if test="${message == 'reg'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Success!</strong> <br>You group created!
        </div>
    </c:if>
    <c:if test="${message == 'updateTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Success!</strong> <br>You group has been updated!
        </div>
    </c:if>
    <c:if test="${message == 'updateFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Error!</strong> <br>You group was not updated! Try again.
        </div>
    </c:if>

    <c:if test="${message == 'acceptTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Success!</strong> <br>${actionAccount.firstName} ${actionAccount.lastName} was added in group!
        </div>
    </c:if>
    <c:if test="${message == 'declineTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            Request from ${actionAccount.firstName} ${actionAccount.lastName} was declined.
        </div>
    </c:if>
    <c:if test="${message == 'removeTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            ${actionAccount.firstName} ${actionAccount.lastName} was removed from the group.
        </div>
    </c:if>
    <c:if test="${message == 'toUserTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Success!</strong> <br>${actionAccount.firstName} ${actionAccount.lastName} now is "USER".
        </div>
    </c:if>
    <c:if test="${message == 'toAdminTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Success!</strong> <br>${actionAccount.firstName} ${actionAccount.lastName} now is "ADMIN".
        </div>
    </c:if>
    <c:if test="${message == 'addPendingTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            <strong>Success!</strong> <br>You request has been sent. Please wait admin action.
        </div>
    </c:if>
    <c:if test="${message == 'removePendingTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            You request has been removed.
        </div>
    </c:if>
    <c:if test="${message == 'leaveGroupTrue'}">
        <div class="alert alert-success text-alert" role="alert">
            You left the group. Bye.
        </div>
    </c:if>
    <c:if test="${message == 'groupActionFalse'}">
        <div class="alert alert-danger text-alert" role="alert">
            <strong>Oops!</strong> <br>Something went wrong..!
        </div>
    </c:if>

    <jsp:useBean id="groupService" class="com.getjavajob.training.web1803.service.GroupService"/>
    <c:set var="group" scope="page" value="${groupService.get(groupId)}"/>
    <c:set var="sessionId" scope="page" value="${sessionScope.id}"/>
    <c:set var="role" scope="page" value="${groupService.getRoleMemberInGroup(groupId, sessionId)}"/>
    <c:set var="globalRole" scope="page" value="${accountService.getRole(sessionId)}"/>
    <div class="row">
        <div class="col-md-3">
            <div class="text-center">
                <img src="GetPhotoGroupServlet?id=${groupId}" onerror="this.src='resources/img/no-image-group.png'" class="img-fluid"
                     alt="Responsive image">
                <div class="control-panel">
                    Control panel<br>
                </div>
                <c:if test="${role == GroupRole.ADMIN || globalRole == Role.ADMIN}">
                    <div class="control-panel">
                        <a href="updateGroup.jsp?id=${groupId}">
                            <button type="button" class="btn btn-sm btn-primary">Update group</button>
                        </a>
                    </div>
                </c:if>
                <c:set var="status" scope="page" value="${groupService.getStatusMemberInGroup(groupId, sessionId)}"/>
                <c:if test="${status == GroupStatus.UNKNOWN}">
                    <div class="control-panel">
                        <form method="post" action="GroupActionServlet?action=addPending&groupId=${groupId}&actionId=${sessionId}">
                            <button type="submit" class="btn btn-primary">Join to group!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${status == GroupStatus.PENDING}">
                    <div class="control-panel">
                        <form method="post" action="GroupActionServlet?action=removeRequest&groupId=${groupId}&actionId=${sessionId}">
                            <button type="submit" class="btn btn-secondary">Remove my request!</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${status == GroupStatus.ACCEPTED}">
                    <div class="control-panel">
                        <form method="post" action="GroupActionServlet?action=leaveGroup&groupId=${groupId}&actionId=${sessionId}">
                            <button type="submit" class="btn btn-warning">Leave the group!</button>
                        </form>
                    </div>
                </c:if>
            </div>
        </div>
        <c:set var="accountCreator" scope="page" value="${accountService.get(group.userCreatorId)}"/>
        <div class="col-md-9">
            <div class="row">
                <h5>${group.name}</h5>
            </div>
            <div class="row">
                <div class="col-3">Create date:</div>
                <div class="col-6">${group.createDate}</div>
            </div>
            <div class="row">
                <div class="col-3">Group Info:</div>
                <div class="col-6">${group.info}</div>
            </div>
            <div class="row">
                <div class="col-3">Creator:</div>
                <div class="col-6">
                    <a href="account.jsp?id=${accountCreator.id}">${accountCreator.firstName} ${accountCreator.lastName}</a><br>
                </div>
            </div>
                <c:if test="${role == GroupRole.ADMIN}">
                    <div class="row">
                        <br><h6>Pending members for joining the group</h6>
                    </div>
                    <c:forEach var="currentAccountId" items="${group.pendingMembersId}">
                        <c:set var="currentAccount" scope="page" value="${accountService.get(currentAccountId)}"/>
                        <div class="row row-friends">
                            <div class="col-3">
                                <a href="account.jsp?id=${currentAccount.id}">
                                        ${currentAccount.firstName} ${currentAccount.middleName} ${currentAccount.lastName}
                                </a>
                            </div>
                            <div class="col-6">
                                <form class="d-inline" method="post" action="GroupActionServlet?action=acceptMember&groupId=${groupId}&actionId=${currentAccount.id}">
                                    <button type="submit" class="btn btn-sm btn-success">Add to group!</button>
                                </form>
                                <form class="d-inline" method="post" action="GroupActionServlet?action=declineMember&groupId=${groupId}&actionId=${currentAccount.id}">
                                    <button type="submit" class="btn btn-sm btn-danger">Decline request!</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            <div class="row">
                <h6>Group members</h6>
            </div>
                <c:forEach var="currentAccountId" items="${group.acceptedMembersId}">
                    <c:set var="currentAccount" scope="page" value="${accountService.get(currentAccountId)}"/>
                    <div class="row row-friends">
                        <div class="col-3">
                            <a href="account.jsp?id=${currentAccount.id}">
                                    ${currentAccount.firstName} ${currentAccount.middleName} ${currentAccount.lastName}
                            </a>
                        </div>
                        <div class="col-6">
                            <c:if test="${role == GroupRole.ADMIN && currentAccountId != sessionId}">
                                <form class="d-inline" method="post" action="GroupActionServlet?action=removeMember&groupId=${groupId}&actionId=${currentAccount.id}">
                                    <button type="submit" class="btn btn-sm btn-danger">Remove from group!</button>
                                </form>

                                <c:set var="rowRole" scope="page" value="${groupService.getRoleMemberInGroup(groupId, currentAccountId)}"/>
                                <c:if test="${rowRole == GroupRole.ADMIN && currentAccountId != sessionId}">
                                    <form class="d-inline" method="post" action="GroupActionServlet?action=toUser&groupId=${groupId}&actionId=${currentAccount.id}">
                                        <button type="submit" class="btn btn-sm btn-primary">Set group role to "USER"</button>
                                    </form>
                                </c:if>
                                <c:if test="${rowRole == GroupRole.USER && currentAccountId != sessionId}">
                                    <form class="d-inline" method="post" action="GroupActionServlet?action=toAdmin&groupId=${groupId}&actionId=${currentAccount.id}">
                                        <button type="submit" class="btn btn-sm btn-primary">Set group role to "ADMIN"</button>
                                    </form>
                                </c:if>
                            </c:if>
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