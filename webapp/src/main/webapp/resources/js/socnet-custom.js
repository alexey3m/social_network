function removeRow(btn) {
    btn.parentNode.parentNode.remove();
}

function addRow() {
    var newRow = $('<div id="phone" class="input-group mb-2 phoneClass">\n' +
        '                <div class="input-group-append">\n' +
        '                    <input type="number" class="form-control phoneClassInput" placeholder="Телефон"/>\n' +
        '                </div>\n' +
        '                <div class="form-control">\n' +
        '                    <select class="custom-select phoneClassSelect" >\n' +
        '                            <option value="MOBILE">Мобильный</option>\n' +
        '                            <option value="WORK">Рабочий</option>\n' +
        '                            <option value="HOME">Домашний</option>\n' +
        '                    </select>\n' +
        '                </div>\n' +
        '                <div class="input-group-append">\n' +
        '                    <button class="btn btn-danger" type="button" onclick="removeRow(this)">Удалить</button>\n' +
        '                </div>\n' +
        '            </div>');
    $('#addRowBtn').before(newRow);
}

$('#formConfirm').submit(function () {
    return confirm("Уверены ли вы, что хотите сохранить изменения?");
});

function updateIndexedInputNames() {
    $('.phoneClass').each(function (containerIndex, element) {
        $(this).find("input").each(function (index, element) {
            $(element).attr("name", "phones[" + containerIndex + "].number");
        });
        $(this).find("select").each(function (index, element) {
            $(element).attr("name", "phones[" + containerIndex + "].phoneType");
        });
    });
}

$(function () {
    $("#searchAccount").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: '/commonSearchFilter',
                data: {
                    filter: request.term
                },
                success: function (data) {
                    response(data);
                }
            });
        },
        minLength: 1
    });
});
