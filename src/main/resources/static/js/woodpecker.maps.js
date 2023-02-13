const mapsAjaxUrl = "rest/maps/";
const ordersAjaxUrl = "rest/orders/"
const filter = "rest/maps/filter";
// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mapsAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: 'GET',
            url: filter,
            data: $("#filter").serialize(),
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
                "render": function (data, type, row) {
                    return data.substring(0, 10).replaceAll("-", ".");
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
                "render": function (data, type, row) {
                    let language = data;
                    let city = "без ст"
                    if (data.includes("Русский")) {
                        language = "Рус";
                    } else if (data.includes("Английский"))
                        language = "Анг"
                    if (row.isState)
                        city = "шт";
                    else if (row.isCapital)
                        city = "ст";
                    return language + " " + city;
                }
            },
            {
                "data": "isMultiLevel",
                "render": function (data, type, row) {
                    // if (data) {
                    //     return  '<span class="fa fa-check"></span>';
                    // }
                    // return '<span class="fa fa-close"></span>';
                    if (data) {
                        return "Многоур"
                    }
                    return "Одноур";
                }
            },
            {
                "data": "color",
                "render": function (data, type, row) {
                    var ref = "<a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + data + "\">"
                    if (data.length > 10) {
                        data = data.substring(0, 7) + "...";
                    }
                    return ref + data + "</a>";
                }
            },
            {
                "data": "description",
                "render": function (data, type, row) {
                    var ref = "<a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + data + "\">"
                    if (data.length > 10) {
                        data = data.substring(0, 7) + "...";
                    }
                    return ref + data + "</a>";
                }
            },
            {
                "data": "orderMap",
                "render": function (data, type, row) {
                    if (data !== null) {
                        if (!getCondition(data.stage).toLowerCase().startsWith("отправлен")) {
                            return "<button class='btn btn-danger' onclick='getOrderForModify(" + row.id + ");'>" + getCondition(data.stage) + "</button>";
                        } else return getCondition(data.stage);
                    } else {
                        return "<button class='btn btn-secondary' onclick='createOrder(" + row.id + ");'>Начать</button>";
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
    document.getElementById('dateTime').value = new Date().toISOString().substring(0, 16);
    document.getElementById('typeMap').value = "Мир";
    document.getElementById('isPlexiglass').value = "false";
    document.getElementById('isState').value = "true";
    document.getElementById('isCapital').value = "true"
    document.getElementById('isMultiLevel').value = "true";
    document.getElementById('light').value = "Без подсветки";
    document.getElementById('isMonochromatic').value = "false";
    $("#editRow").modal();
}


function createOrder(id) {
    form.find(":input").val("");
    $("#createOrder").modal();
    document.getElementById('order-Id').value = id;

}

function saveOrder() {
    $.ajax({
        type: "POST",
        url: ordersAjaxUrl,
        data: $('#orderForm').serialize()
    }).done(function () {
        $("#createOrder").modal("hide");
        ctx.updateTable();
        successNoty("Заказ создан");
        $("#orderForm")[0].reset();
    });

}