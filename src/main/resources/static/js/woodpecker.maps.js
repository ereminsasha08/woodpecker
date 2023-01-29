const mapsAjaxUrl = "rest/maps/";
const filter = "rest/maps/filter";
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
    makeEditable({

        "columns": [
            {
                "data": "id"
            },
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    return date.substring(0, 10).replaceAll("-", ".");
                }
            },
            {
                "data": "typeMap"
            },
            {
                "data": "size"
            },
            {
                "data": "language",
                "render": function (date, type, row) {
                    var s = "EN";
                    if ("Русский" === date) {
                        s = "RU";
                    }
                    return s;
                }
            },
            {
                "data": "isState",
                "render": function (date, type, row) {
                    if (date) {
                        return "ШТ"
                    }
                    return "Без ШТ";
                }
            },
            {
                "data": "isMultiLevel",
                "render": function (date, type, row) {
                    if (date) {
                        return "Многоур."
                    }
                    return "Одноур.";
                }
            },
            {
                "data": "color",
                "render": function (date, type, row) {
                    var ref = "<a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + date + "\">"
                    if (date.length > 10) {
                        date = date.substring(0, 7) + "...";
                    }
                    return ref + date + "</a>";
                }
            },
            {
                "data": "description",
                "render": function (date, type, row) {
                    var ref = "<a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + date + "\">"
                    if (date.length > 10) {
                        date = date.substring(0, 7) + "...";
                    }
                    return ref + date + "</a>";
                }
            },
            {
                "data": "conditionMap",
                "render": function (date, type, row) {
                    switch (date) {
                        case -1:
                            return "Неизвестно";
                        case 0:
                            return "<button class='btn btn-secondary' onclick='createOrder(" + row.id + ");'>Начать</button>";
                        case 1:
                            return "В очереди"
                        case 2:
                            return "Пилится";
                        case 4:
                            return "Выпилен";
                        case 6:
                            return "Красится";
                        case 8:
                            return "Покрашен";
                        case 10:
                            return "На приклейки";
                        case 12:
                            return "На запаковке";
                        case 14:
                            return "Готов к отправке";
                        case 16:
                            return "Отправлен";

                    }
                }
            },
            {
                "data": "price"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],

        "order": [
            [
                0,
                "desc"
            ]
        ],
    });
});

function add() {
    form.find(":input").val("");
    $("#editRow").modal();
    document.getElementById('conditionMap').value = "0";
}


function createOrder(id){
    form.find(":input").val("");
    $("#createOrder").modal();
    document.getElementById('order-Id').value = id;

}
function saveOrder(){
    $.ajax({
        type: "POST",
        url: "rest/orders/",
        data: $('#orderForm').serialize()
    }).done(function () {
        ctx.updateTable();
        successNoty("Заказ создан");
    });
}