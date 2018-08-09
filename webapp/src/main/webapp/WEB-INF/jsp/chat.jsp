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
    <!-- https://cdnjs.com/libraries/sockjs-client -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <!-- https://cdnjs.com/libraries/stomp.js/ -->
    <script  src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <!-- Custom styles for this template -->
    <link href="<c:url value="/resources/css/chat.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/jquery-ui.css"/>" rel="stylesheet" type="text/css"/>
    <title>Общий чат Social net!</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
<main role="main" class="container">
    <div id="chat-container">
        <div class="chat-header">
            <div class="user-container hidden">
                <span id="username"><a href="viewAccount?id=${id}">${username}</a></span>
            </div>
            <h3>Общий Чат</h3>
        </div>
        <hr/>
        <div id="connecting">Соединение...</div>
        <ul id="messageArea">
        </ul>
        <form id="messageForm" name="messageForm">
            <div class="input-message">
                <input type="text" id="message" autocomplete="off"
                       placeholder="Ваше сообщение..."/>
                <button type="submit">Отправить</button>
            </div>
        </form>
    </div>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<c:url value="/resources/js/jquery-3.3.1.js"/>"></script>
<script src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/resources/js/popper.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/socnet-custom.js"/>"></script>
<script src="<c:url value="/resources/js/socnet-custom-chat.js"/>"></script>
</body>
</html>



