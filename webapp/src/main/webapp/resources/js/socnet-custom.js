function removeRow(btn) {
    btn.parentNode.parentNode.remove();
}

function addRow() {
    var newRow = $('<div id="phone" class="input-group mb-2"><div class="input-group-append">' +
        '<input type="number" id="inputPhone" name="inputPhone" class="form-control" placeholder="Телефон">' +
        '</div> <div class="form-control"> <label for="phoneSel" class="sr-only">Тип телефона</label>' +
        '<select class="custom-select" id="phoneSel" name="phoneSel">' +
        '<option value="0">Мобильный</option><option value="1">Рабочий</option><option value="2">Домашний</option></select></div>' +
        '<div class="input-group-append"> <button class="btn btn-danger" type="button" onclick="removeRow(this)">Удалить</button></div>' +
        '</div>');
    $('#addRowBtn').before(newRow);
}

$('#formConfirm').submit(function() {
    return confirm("Уверены ли вы, что хотите сохранить изменения?");
});