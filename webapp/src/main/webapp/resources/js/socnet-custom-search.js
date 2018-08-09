var pageAcc = 1;
var pageGr = 1;
var totalRowsAccount;
var totalRowsGroup;
var rowsPerPage = 5;

$(function () {
    $.ajax({
        type: "GET",
        url: "/accountFilterCount?filter=" + filter,
        success: function (data) {
            totalRowsAccount = data;
        }
    });
    $.ajax({
        type: "GET",
        url: "/groupFilterCount?filter=" + filter,
        success: function (data) {
            totalRowsGroup = data;
        }
    });

    $.ajax({
        type: "GET",
        url: "/accountFilter?filter=" + filter + "&page=" + pageAcc,
        dataType: "json",
        success: function (data) {
            var $templateRow = $('#templateRowAcc');
            $.each(data, function (i, obj) {
                var $row = $templateRow.clone().removeAttr('id').attr('id', 'rowAcc' + i).show();
                $row.find('#templateColAcc').html(
                    "<a href=\"viewAccount?id=" + obj.id + "\">" + obj.firstName + " " + obj.middleName + " " + obj.lastName + "</a>");
                $('#tableAcc').append($row);
            });
            displayPrevAcc();
            displayNextAcc();
        }
    });


    $.ajax({
        type: "GET",
        url: "/groupFilter?filter=" + filter + "&page=" + pageGr,
        dataType: "json",
        success: function (data) {
            var $templateRow = $('#templateRowGr');
            $.each(data, function (i, obj) {
                var $row = $templateRow.clone().removeAttr('id').attr('id', 'rowGr' + i).show();
                $row.find('#templateColGr').html(
                    "<a href=\"viewGroup?id=" + obj.id + "\">" + obj.name + "</a>");
                $('#tableGr').append($row);
            });
            displayPrevGr();
            displayNextGr();
        }
    });

});

function getAccounts() {
    $.ajax({
        type: "GET",
        url: "/accountFilter?filter=" + filter + "&page=" + pageAcc,
        dataType: "json",
        success: function (data) {
            var currentCount;
            $.each(data, function (i, obj) {
                $('#rowAcc' + i).show().find('#templateColAcc').html(
                    "<a href=\"viewAccount?id=" + obj.id + "\">" + obj.firstName + " " + obj.middleName + " " + obj.lastName + "</a>");
                currentCount = i;
            });
            while (currentCount < 4) {
                $('#rowAcc' + currentCount++).hide();
            }
        }
    });
}

$('#prevAcc').on('click', function (e) {
    e.preventDefault();
    pageAcc--;
    displayPrevAcc();
    displayNextAcc();
    getAccounts()
});

$('#nextAcc').on('click', function (e) {
    e.preventDefault();
    pageAcc++;
    displayPrevAcc();
    displayNextAcc();
    getAccounts()
});

function displayPrevAcc() {
    if (pageAcc > 1)
        $('#prevAcc').show();
    else
        $('#prevAcc').hide();
}

function displayNextAcc() {
    var currRow = pageAcc * rowsPerPage;
    if (totalRowsAccount === undefined || currRow > totalRowsAccount)
        $('#nextAcc').hide();
    else
        $('#nextAcc').show();
}

function getGroups() {
    $.ajax({
        type: "GET",
        url: "/groupFilter?filter=" + filter + "&page=" + pageGr,
        dataType: "json",
        success: function (data) {
            var currentCount;
            $.each(data, function (i, obj) {
                $('#rowGr' + i).show().find('#templateColGr').html(
                    "<a href=\"viewGroup?id=" + obj.id + "\">" + obj.name + "</a>");
                currentCount = i;
            });
            while (currentCount < 4) {
                $('#rowGr' + currentCount++).hide();
            }
        }
    });
}

$('#prevGr').on('click', function (e) {
    e.preventDefault();
    pageGr--;
    displayPrevGr();
    displayNextGr();
    getGroups()
});

$('#nextGr').on('click', function (e) {
    e.preventDefault();
    pageGr++;
    displayPrevGr();
    displayNextGr();
    getGroups()
});

function displayPrevGr() {
    if (pageGr > 1)
        $('#prevGr').show();
    else
        $('#prevGr').hide();
}

function displayNextGr() {
    var currRow = pageGr * rowsPerPage;
    if (totalRowsGroup === undefined || currRow > totalRowsGroup)
        $('#nextGr').hide();
    else
        $('#nextGr').show();
}