let form;
let date = Date.now();
let restOrder = "rest/orders/"

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
                    if (data.orderTerm != null && !(data.marketPlace || data.isAvailability)) {

                        if (date - Date.parse(data.orderTerm) > -350000000) {
                            $(row).attr("data-map-info", true);
                            return;
                        }
                    }
                    if (data.marketPlace && data.isAvailability) {
                        $(row).attr("data-map-urgent-availability", true);
                        return;
                    }
                    if (data.isAvailability)
                        $(row).attr("data-map-availability", true);
                    if (data.marketPlace)
                        $(row).attr("data-map-urgent", true);


                }
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

function add() {
    $("#modalTitle").html("addTitle");
    form.find(":input").val("");
    $("#editRow").modal();
}


function updateRow(id) {
    form.find(":input").val("");
    $("#modalTitle").html("editTitle");
    $.get(ctx.ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            if (value != null) {
                let elementById = document.getElementById(key);
                if (elementById != null)
                    elementById.value = value;
            }
        });
        $('#editRow').modal();
    });
}

function deleteRow(id) {

    if (confirm('Вы уверенны?')) {
        $.ajax({
            url: ctx.ajaxUrl + id,
            type: "DELETE"
        }).done(function () {
            ctx.updateTable();
            successNoty("Удаленно");
        });
    }
}

function updateTableByData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}

function save() {
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        ctx.updateTable();
        successNoty("Сохранено");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + key,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ");'><i class='fa fa-pencil fa-2x' size='60px'></i></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ");'><i class='fa fa-remove small fa-2x'  >   </i></a>";
    }
}

function failNoty(jqXHR) {
    closeNoty();
    let errorInfo = jqXHR.responseJSON;
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.typeMessage + "<br>" + errorInfo.details.join("<br>"),
        type: "error",
        layout: "bottomRight"
    });
    failedNote.show()
}


function getCondition(date) {
    switch (date) {
        case 0:
            return "Неизвестно";
        case 1:
            return "Новый заказ";
        case 2:
            return "Жду резку";
        case 3:
            return "Пилится";
        case 4:
            return "Жду покраску";
        case 5:
            return "Красится";
        case 6:
            return "Жду приклейки";
        case 7:
            return "Приклеивается";
        case 8:
            return "Доделывается"
        case 9:
            return "Запаковывается";
        case 10:
            return "Жду отправку";
        case 11:
            return "Отправлен";
        case 12:
            return "Наличие";
        case 13:
            return "Из наличия";
    }

}

function getInfoMap(id) {
    $("#modalInfoMap").modal();
    $.get("rest/orders/" + id, function (data) {
        $.each(data, function (key, value) {
            if (value != null)
                if (key === "geographyMap") {
                    $.each(data[key], function (key, value) {
                        if (value != null) {
                            let a = document.getElementById("infoMap_" + key);
                            if (a != null && !key.toString().endsWith("dateTime"))
                                a.value = value
                            else if (a != null)
                                a.value = value.toString().substring(0, 10).replaceAll('-', '.');
                        }
                    });
                } else {
                    let a = document.getElementById("infoMap_" + key);
                    if (a != null)
                        a.value = value
                }

        });
    });
}


function getOrderForModify(id) {
    $('#modify_orderForm').find(":input").val("");
    $("#modify_editRow").modal();
    $.get("rest/orders/" + id, function (data) {
        $.each(data, function (key, value) {
                if (value != null)
                    if (key === "geographyMap") {
                        $.each(data[key], function (key, value) {
                            if (value != null) {
                                let a = document.getElementById("modify_" + key);
                                if (a != null)
                                    a.value = value
                            }
                        });
                    } else {
                        let a = document.getElementById("modify_" + key);
                        if (a != null && !key.toString().endsWith("orderTerm")) {
                            a.value = value
                        } else if (a != null) {
                            a.value = value.toString().substring(0, 16);
                        }
                    }
            }
        )
        ;
    });

}

function modifyOrder() {
    $.ajax({
        type: "PATCH",
        url: restOrder,
        data: $("#modify_orderForm").serialize(),
    }).done(function () {
        $("#modify_editRow").modal("hide");
        ctx.updateTable();
        successNoty("Сохранено");
    });
}