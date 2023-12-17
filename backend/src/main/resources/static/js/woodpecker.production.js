const mapsAjaxUrl = "rest/production/";
// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mapsAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: 'GET',
            url: mapsAjaxUrl,
        }).done(updateTableByData);
    }
}

// $(document).ready(function () {
$(function () {
    makeEditable({
        "order": false,
        "columns": [
            {
                "data": "geographyMap",
                "orderable": false,
                "defaultContent": "",
                "render": function (data, type, row) {
                    if (data.isPlexiglass || !data.light.toLowerCase().startsWith("без под") || (data.additional != null && data.additional.toString().length > 0)) {
                        return  '<i class="fa fa-exclamation" aria-hidden="true"></i>';
                    }
                }
            },
            {
                "data": "id",
                "orderable": false,
                "render": function (data, type, row) {
                    let description = row.geographyMap.description;
                    let ref = "<button class='btn btn-info' onclick='getInfoMap(" + data + ");' data-placement=\"top\" title=\"" + data + "\">";
                    if (description != null && description.toString().length >= 4) {
                        ref += description.toString().substring(0, 4);
                    } else {
                        ref += data;
                    }
                    return ref + "</a>"
                }
            },
            {
                "data": "orderTerm",
                "orderable": false,
                "render": function (data, type, row) {
                    if (type === "display") {
                        let s = data.substring(2, 10).replaceAll("-", "");
                        return s.substring(4, 6) + "." + s.substring(2, 4) + "." + s.substring(0, 2);
                    }
                    return data;

                }
            },
            {
                "data": "geographyMap.typeMap",
                "orderable": false,
            },
            {
                "data": "geographyMap.size",
                "orderable": false,
            },
            {
                "data": "geographyMap.language",
                "orderable": false,
                "render": function (data, type, row) {
                    return renderLanguageState(data, row.geographyMap);
                }
            },
            {
                "data": "geographyMap.color",
                "orderable": false,
                "render": function (data, type, row) {
                    if (data.toString().length < 10)
                        return data;
                    else return '<div class="overflow-auto" style="max-width: 100px; max-height: 40px">' + data + '</div>';
                }
            },
            {
                "data": "geographyMap.light",
                "orderable": false,
                "render": function (data, type, row) {
                    if (data != null && !data.toString().toLowerCase().startsWith("без"))
                        if (data.toString().length < 10)
                            return data;
                        else return '<div class="overflow-auto" style="max-width: 100px; max-height: 40px">' + data + '</div>';
                    else return "Нет";

                }

            },
            {
                "data": "geographyMap.isMultiLevel",
                "orderable": false,
                "render": function (data, type, row) {
                    if (data) {
                        return '<span class="fa fa-check"></span>';
                    }
                    return '<span class="fa fa-close"></span>';
                    // if (data) {
                    //     return "Многоур."
                    // }
                    // return "Одноур.";
                }
            },
            {
                "data": "geographyMap.isColorPlywood",
                "orderable": false,
                "render": function (data, type, row) {
                    if (data) {
                        return '<span class="fa fa-check"></span>';
                    }
                    return '<span class="fa fa-close"></span>';
                    // if (data) {
                    //     return "Многоур."
                    // }
                    // return "Одноур.";
                }
            },

            {
                "data": "laser",
                "orderable": false,
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
                "orderable": false,
                "render": function (data, type, row) {
                    let msg = 'Вы уверенны, что заказ отправлен?';
                    if (data.ordersOperation >= 6) {
                        if (data.ordersOperation === 9 && row.isAvailability) {
                            msg = "Вы уверены, что заказ готов для наличия?"
                            return "<button class='btn btn-my btn-danger' onclick='setCondition(" + row.id + "," + 10 + ",\"" + msg + "\");'>Внести в наличие" + "</button>";
                        } else return "<button class='btn btn-my btn-danger' onclick='setCondition(" + row.id + "," + data.ordersOperation + ",\"" + msg + "\");'>" + getCondition(data.ordersOperation) + "</button>";
                    } else {
                        return getCondition(data.ordersOperation)
                    }
                }
            },
        ],
    });
});

function setCondition(id, conditionMap, msg) {
    if (conditionMap === 10 && !confirm(msg)) {
        return;
    }
    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl + id,
        data: {
            conditionMap: conditionMap
        }
    }).done(function () {
        ctx.updateTable();
        successNoty(getCondition(conditionMap));
    });

}

function getInfoCut(id) {
    $("#info-cut").modal("show");
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
            $('#info-cut').modal("show");
        });
}

