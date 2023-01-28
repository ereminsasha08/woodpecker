const mapsAjaxUrl = "rest/orders/";
// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mapsAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: 'GET',
        }).done(updateTableByData);
    }
}

// $(document).ready(function () {
$(function () {
    makeEditable({

        "columns": [
            {
                "data": "id",
                "render": function (date, type, row) {
                    var ref = "<a onclick='get(" + date + ");' data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + date + "\">";
                    return ref + date + "</a>";
                }
            },
            {
                "data": "orderTerm",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.substring(0, 10).replace("-", ".");
                    }
                    return date;
                }
            },
            {
                "data": "geographyMap.typeMap"
            },
            {
                "data": "geographyMap.size"
            },
            {
                "data": "geographyMap.language",
                "render": function (date, type, row) {
                    var s = "EN";
                    if ("Русский" === date) {
                        s = "RU";
                    }
                    return s;
                }
            },
            {
                "data": "geographyMap.isState",
                "render": function (date, type, row) {
                    if (date) {
                        return "ШТ"
                    }
                    return "Без ШТ";
                }
            },
            {
                "data": "geographyMap.isMultiLevel",
                "render": function (date, type, row) {
                    if (date) {
                        return "Многоур."
                    }
                    return "Одноур.";
                }
            },
            {
                "data": "geographyMap.color",
                "render": function (date, type, row) {
                    var ref = "<a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + date + "\">"
                    if (date.length > 10) {
                        date = date.substring(0, 7) + "...";
                    }
                    return ref + date + "</a>";
                }
            }
            //     {
            //         "data": "description",
            //         "render": function (date, type, row) {
            //             var ref = "<a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + date + "\">"
            //             if (date.length > 10) {
            //                 date = date.substring(0, 7) + "...";
            //             }
            //             return ref + date + "</a>";
            //         }
            //     },
            //     {
            //         "data": "conditionMap",
            //         "render": function (date, type, row) {
            //             switch (date) {
            //                 case -1:
            //                     return "Неизвестно";
            //                 case 0:
            //                     return "Новый заказ";
            //                 case 2:
            //                     return "Пилится";
            //                 case 4:
            //                     return "Выпилен";
            //                 case 6:
            //                     return "Красится";
            //                 case 8:
            //                     return "Покрашен";
            //                 case 10:
            //                     return "На приклейки";
            //                 case 12:
            //                     return "На запаковке";
            //                 case 14:
            //                     return "Готов к отправке";
            //                 case 16:
            //                     return "Отправлен";
            //
            //             }
            //         }
            //     },
            //     {
            //         "data": "price"
            //     },
            //     {
            //         "orderable": false,
            //         "defaultContent": "",
            //         "render": renderEditBtn
            //     },
            //     {
            //         "orderable": false,
            //         "defaultContent": "",
            //         "render": renderDeleteBtn
            //     }
        ],

        "order": [
            [
                0,
                "desc"
            ]
        ],
    });
});

function get(id) {
    $("#orderInfo").modal();
    $.get(ctx.ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            if (value != null)
                if (key === "geographyMap") {
                    $.each(data[key], function (key, value){
                        let elementById = document.getElementById(key);
                        if (elementById != null) {
                            elementById.innerText += value;
                        }
                    });
                }

        });
        $('#orderInfo').modal();
    });
}
