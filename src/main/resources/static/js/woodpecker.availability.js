const availabilityAjax = "rest/availability/";
// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: availabilityAjax,
    updateTable: function () {
        $.ajax({
            type: 'GET',
            url: availabilityAjax,
        }).done(updateTableByData);
    }
}

// $(document).ready(function () {
$(function () {
    makeEditable({
        "columns": [
            {
                "data": "id",
                "render": function (data) {
                    let ref = "<button class='btn btn-info' onclick='getInfoMap(" + data + ");' data-placement=\"top\" title=\"" + data + "\">";
                    return ref + data + "</a>";
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
                "render": function (data, type, row) {
                    return renderLanguageState(data, row.geographyMap);
                }
            },
            {
                "data": "geographyMap.isMultiLevel",
                "render": function (data) {
                    if (data) {
                        return "Многоур."
                    }
                    return "Одноур.";
                }
            },
            {
                "data": "geographyMap.color",
                "render": function (data) {
                    if (data.toString().length < 15)
                        return data;
                    else return '<div class="overflow-auto" style="max-width: 240px; max-height: 40px">' + data + '</div>';
                }
            },
            {
                "data": "geographyMap.light",
                "render": function (data, type) {
                    if (type === "display" && !data.toString().toLowerCase().startsWith("без"))
                        return data;
                    else return "Нет";
                }
            },
            {
                "data": "laser",
                "render": function (data, type, row) {
                    if (data != null) {
                        return "<button class='btn btn-info' onclick='getInfoCut(" + row.id + ");'>" + data + "</button>";
                    } else {
                        return "<button class='btn btn-secondary' onclick='setLaser(" + row.id + ");'>Нет</button>";
                    }
                }
            },
            {
                "data": "stage",
                "render": function (data, type, row) {
                    return "<button class='btn btn-my btn-warning' onclick='getOrderForModify(" + row.id + ");'>" + getCondition(data.ordersOperation) + "</button>";

                }
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
        columnDefs: [
            {
                targets: [2],
                orderData: [1, 2, 4, 3,5]
            }
        ],
        "order": [
            [
                2,
                "asc"
            ]
        ],
    });
});

function makeEditable(datatableOpts) {
    ctx.datatableApi = $("#datatable").DataTable(

        // https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
        $.extend(true, datatableOpts,
            {
                "ajax": {
                    "url": ctx.ajaxUrl,
                    "dataSrc": ""
                },
                "paging": false,
                "info": true,
                "createdRow": function (row, data, dataIndex) {
                    if (data.orderTerm != null && !(data.marketPlace || data.isAvailability)) {

                        if (data - Date.parse(data.orderTerm) > -350000000) {
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

    $('#datatable tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
    });

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
    $('#formAvailability').find(":input").val("");
    document.getElementById('availability_dateTime').value = new Date().toISOString().substring(0, 16);
    document.getElementById('availability_isPlexiglass').value = "false";
    document.getElementById('availability_isMonochromatic').value = "false";
    document.getElementById('availability_price').value = "1000";
    document.getElementById('availability_contact').value = "Нет";
    $("#createAvailability").modal();
}

function saveAvailability() {
    $.ajax({
        type: "POST",
        url: availabilityAjax,
        data: $('#formAvailability').serialize()
    }).done(function () {
        $("#createAvailability").modal("hide");
        ctx.updateTable();
        successNoty("Карта создана");
        $("#formAvailability")[0].reset();
    });

}

function getInfoCut(id) {
    $("#info-cut").modal();
    $.get("rest/cut/info/" + id,
        function (data) {
            let listSelect = "";
            $.each(data, function (index, value) {
                listSelect +=
                    '<div class="row">' +
                    '<div class="form-group col-3"></div>' +
                    '<div class="form-group col-6">' +
                    '<output type="text" class="form-control" id="list" name="list"> ' +
                    '<span id ="' + index + '">"' + value + '"</span>' +
                    '</output>' +
                    '</div>' +
                    '</div>'
            });
            document.getElementById("info").innerHTML = listSelect;
            $('#info-cut').modal();
        });
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



function updateRow(id) {
    form.find(":input").val("");
    $("#createAvailability").modal();
    $.get("rest/orders/" + id, function (data) {
        $.each(data, function (key, value) {
            if (value != null)
                if (key === "geographyMap") {
                    $.each(data[key], function (key, value) {
                        if (value != null) {
                            let a = document.getElementById("availability_" + key);
                            if (a != null && !key.toString().endsWith("dateTime"))
                                a.value = value
                            else if (a != null)
                                a.value = value.toString().substring(0, 16);
                        }
                    });
                } else {
                    let a = document.getElementById("availability_" + key);
                    if (a != null)
                        a.value = value
                }

        });
    });
    document.getElementById('availability_price').value = "1000";
    document.getElementById('availability_contact').value = "Нет";
}

function deleteRow(id) {

    if (confirm('Вы уверенны?')) {
        $.ajax({
            url: "rest/maps/" + id,
            type: "DELETE"
        }).done(function () {
            ctx.updateTable();
            successNoty("Удаленно");
        });
    }
}

function save() {
    $.ajax({
        type: "POST",
        url: availabilityAjax,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        ctx.updateTable();
        successNoty("Сохранено");
    });
}
