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
            // data: $("#filter").serialize(),
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
        lengthMenu: [
            [50, -1],
            [50, 'All'],
        ],
        "columns": [
            {
                "data": "orderMap",
                "render": function (data, type, row) {
                    if (data != null) {
                        return "<input type='checkbox' " + (data.isPaid ? "checked" : "") + " onclick='setPaid($(this)," + row.id + ");'/>";
                    } else return "";
                }
            },
            {
                "data": "id",
                "render": function (data, type, row) {
                    if (row.orderMap != null) {
                        let ref = "<button class='btn btn-info' onclick='getInfoMap(" + data + ");' data-placement=\"top\" title=\"" + data + "\">";
                        return ref + data + "</a>";
                    } else return data;
                }
            },
            {
                "data": "dateTime",
                "render": function (data, type, row) {
                    let s = data.substring(2, 10).replaceAll("-", "");
                    return s.substring(4, 6) + "." + s.substring(2, 4) + "." + s.substring(0,2);
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
                return renderLanguageState(data, row);
                }
            },
            {
                "data": "isMultiLevel",
                "render": function (data, type, row) {
                    if (data) {
                        return "Мнг"
                    }
                    return "Одн";
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
                    if (data != null) {
                        let ref = "<a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + data + "\">"
                        if (data.length > 10) {
                            data = data.substring(0, 7) + "...";
                        }
                        return ref + data + "</a>";
                    } else return "";
                }
            },
            {
                "data": "orderMap",
                "render": function (data, type, row) {
                    if (data !== null) {
                        if (!getCondition(data.stage.ordersOperation).toLowerCase().startsWith("отправлен")) {
                            return "<button class='btn btn-my btn-warning' onclick='getOrderForModify(" + row.id + ");'>" + getCondition(data.stage.ordersOperation) + "</button>";
                        } else return getCondition(data.stage.ordersOperation);
                    } else {
                        return "<button class='btn btn-my btn-warning' onclick='createOrder(" + row.id + ");'>Начать</button>";
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
                1,
                "desc"
            ]
        ],
    });
})
;

function setPaid(chkbox, id) {
    var enabled = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: ordersAjaxUrl + id,
        type: "PATCH",
        data: "isPaid=" + enabled
    }).done(function () {
        chkbox.closest("tr").attr("data-order-paid", enabled);
        successNoty(enabled ? "Оплачен" : "Не оплачен");
    }).fail(function () {
        $(chkbox).prop("checked", !enabled);
    });
}

function add() {
    form.find(":input").val("");
    document.getElementById('dateTime').value = new Date().toISOString().substring(0, 16);
    document.getElementById('isPlexiglass').value = "false";
    // document.getElementById('isState').value = "true";
    // document.getElementById('isCapital').value = "true"
    document.getElementById('isMultiLevel').value = "true";
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


function makeEditable(datatableOpts) {
    ctx.datatableApi = $("#datatable").DataTable(
        // https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
        $.extend(true, datatableOpts,
            {
                "ajax": {
                    "url": ctx.ajaxUrl,
                    "dataSrc": ""
                },
                "paging": true,
                "info": true,
                "createdRow": function (row, data, dataIndex) {
                    if (data.orderMap != null) {
                        if (!data.orderMap.isPaid) {
                            $(row).attr("data-order-paid", false);
                        }
                        if (data.orderMap.orderTerm != null && !(data.orderMap.marketPlace || data.orderMap.isAvailability)) {

                            if (date - Date.parse(data.orderMap.orderTerm) > -350000000) {
                                $(row).attr("data-map-info", true);
                                return;
                            }

                        }
                        if (data.orderMap.marketPlace && data.orderMap.isAvailability) {
                            $(row).attr("data-map-urgent-availability", true);
                            return;
                        }
                        if (data.orderMap.isAvailability)
                            $(row).attr("data-map-availability", true);
                        if (data.orderMap.marketPlace)
                            $(row).attr("data-map-urgent", true);
                    }

                },
            }
        ));
    form = $('#detailsForm');

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
    //
    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");
    //
    // $(document).ajaxSend(function (e, xhr, options) {
    //     xhr.setRequestHeader(header, token);
    // });
}