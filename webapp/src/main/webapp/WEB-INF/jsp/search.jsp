<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <!-- Custom styles for this template -->
    <link href="<c:url value="/resources/css/search.css"/>" rel="stylesheet" type="text/css"/>
    <title>Поиск Social net!</title>
</head>
<body>
<main role="main" class="container">
    <form class="form-inline my-2 my-lg-0 navs" action="<c:url value="/viewSearch"/>">
        <input class="form-control mr-sm-2" id="searchAccount" type="search" name="inputSearch" placeholder="Поиск" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Поиск</button>
    </form>
</main><!-- /.container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
</body>
</html>