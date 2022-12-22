const mapsAjaxUrl = "maps/";
const filter = "maps/filter";
// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mapsAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: 'GET',
            url: filter,
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mapsAjaxUrl, updateTableByData);
}

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "info": true,
            "columns": [
                {
                    "data": "id"
                },
                {
                    "data": "dateTime"
                },
                {
                    "data": "typeMap"
                },
                {
                    "data": "size"
                },
                {
                    "data": "language"
                },
                {
                    "data": "state"
                },
                {
                    "data": "multiLevel"
                },
                {
                    "data": "color"
                },
                {
                    "data": "description"
                },
                {
                    "data": "conditionMap"
                },
                {
                    "data": "prise"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],

            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});

function add() {
    form.find(":input").val("");
    $("#editRow").modal();
    let select = document.getElementById('conditionMap');
    select.value = '0';
}