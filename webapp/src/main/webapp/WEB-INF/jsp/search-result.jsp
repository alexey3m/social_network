<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <!-- Custom styles for this template -->
    <link href="<c:url value="/css/search-result.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/css/jquery-ui.css"/>" rel="stylesheet" type="text/css"/>
    <title>Результаты поиска Social net!</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
<main role="main" class="container">
    <div>
        <h5>Результаты поиска по "${searchString}"</h5>
    </div>
    <script type="text/javascript">
        var filter = "${searchString}"; // use expression tag in JSP here
    </script>
    <table id="tableAcc" class="table table-hover">
        <thead>
        <tr>
            <th>Аккаунты</th>
        </tr>
        </thead>
        <tbody>
        <tr id="templateRowAcc" style="display:none;">
            <td id="templateColAcc"></td>
        </tr>
        </tbody>
    </table>

    <ul class="pagination">
        <li class="page-item" id="prevAcc" style='display:none'>
            <a class="page-link" href="#" tabindex="-1">Назад</a>
        </li>
        <li class="page-item" id="nextAcc" style='display:none'>
            <a class="page-link" href="#">Вперед</a>
        </li>
    </ul>


    <table id="tableGr" class="table table-hover">
        <thead>
        <tr>
            <th>Группы</th>
        </tr>
        </thead>
        <tbody>
        <tr id="templateRowGr" style="display:none;">
            <td id="templateColGr">
            </td>
        </tr>
        </tbody>
    </table>
    <ul class="pagination">
        <li class="page-item" id="prevGr" style='display:none'>
            <a class="page-link" href="#" tabindex="-1">Назад</a>
        </li>
        <li class="page-item" id="nextGr" style='display:none'>
            <a class="page-link" href="#">Вперед</a>
        </li>
    </ul>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<c:url value="/js/jquery-3.3.1.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/popper.min.js"/>"></script>
<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/js/socnet-custom.js"/>"></script>
<script src="<c:url value="/js/socnet-custom-search.js"/>"></script>
</body>
</html>