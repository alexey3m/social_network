var MAX_ROWS = 5;
var totalRows = 0;
var page = 0;

$(function () {
    $.ajax({
        type: "GET",
        url: "/accountFilterAjax?filter=" + filterPage,
        dataType: "json",
        success: function (data) {
            var $templateRow = $('#templateRowAcc');
            $.each(data, function (i, obj) {
                var $row = $templateRow.clone().removeAttr('id')
                // copy data
                $row.find('#templateColAcc').html(
                    "<a href=\"viewAccount?id=" + obj.id + "\">" + obj.firstName + " " + obj.middleName + " " + obj.lastName + "</a>");
                $('#tableAcc').append($row);
            });
            // Navigation part
            page = 0;
            totalRows = data.length;
            showRowsAcc();
            displayNextAcc();
            // Navigation handler
            $('#prevAcc').on('click', function (e) {
                e.preventDefault();
                page--;
                displayPrevAcc();
                displayNextAcc();
                showRowsAcc()
            });
            $('#nextAcc').on('click', function (e) {
                e.preventDefault();
                page++;
                displayPrevAcc();
                displayNextAcc();
                showRowsAcc();
            });
        },
        error: function (e) {
            alert('Error: ' + e);
        }
    });
    $.ajax({
        type: "GET",
        url: "/groupFilterAjax?filter=" + filterPage,
        dataType: "json",
        success: function (data) {
            var $templateRow = $('#templateRowGr');
            $.each(data, function (i, obj) {
                var $row = $templateRow.clone().removeAttr('id')
                // copy data
                $row.find('#templateColGr').html(
                    "<a href=\"viewGroup?id=" + obj.id + "\">" + obj.name + "</a>");
                $('#tableGr').append($row);
            });
            // Navigation part
            page = 0;
            totalRows = data.length;
            showRowsGr();
            displayNextGr();
            // Navigation handler
            $('#prevGr').on('click', function (e) {
                e.preventDefault();
                page--;
                displayPrevGr();
                displayNextGr();
                showRowsGr()
            });
            $('#nextGr').on('click', function (e) {
                e.preventDefault();
                page++;
                displayPrevGr();
                displayNextGr();
                showRowsGr();
            });
        },
        error: function (e) {
            alert('Error: ' + e);
        }
    });
});

function displayPrevAcc() {
    if (page > 0)
        $('#prevAcc').show();
    else
        $('#prevAcc').hide();
}

function displayNextAcc() {
    var currRow = (page + 1) * MAX_ROWS;
    if (currRow >= totalRows)
        $('#nextAcc').hide();
    else
        $('#nextAcc').show();
}

function showRowsAcc() {
    var startRow = page * MAX_ROWS;
    var counter = 0;
    $('.rowAcc').each(function () {
        if (!($(this).attr('id') || $(this).hasClass('header'))) {
            if (counter < startRow || counter >= startRow + MAX_ROWS) {
                $(this).hide();
            }
            else {
                $(this).show();
            }
            counter++;
        }
    });
}

function displayPrevGr() {
    if (page > 0)
        $('#prevGr').show();
    else
        $('#prevGr').hide();
}

function displayNextGr() {
    var currRow = (page + 1) * MAX_ROWS;
    if (currRow >= totalRows)
        $('#nextGr').hide();
    else
        $('#nextGr').show();
}

function showRowsGr() {
    var startRow = page * MAX_ROWS;
    var counter = 0;
    $('.rowGr').each(function () {
        if (!($(this).attr('id') || $(this).hasClass('header'))) {
            if (counter < startRow || counter >= startRow + MAX_ROWS) {
                $(this).hide();
            }
            else {
                $(this).show();
            }
            counter++;
        }
    });
}