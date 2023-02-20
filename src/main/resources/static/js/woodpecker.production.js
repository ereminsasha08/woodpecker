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
                "render": function (data, type, row) {
                    if (data.isPlexiglass || !data.light.toLowerCase().startsWith("без под") || (data.additional != null && data.additional.toString().length > 0)) {
                        return '<i class="fa fa-exclamation" aria-hidden="true"></i>';
                    }
                    return '';
                }
            },
            {
                "data": "id",
                "render": function (data, type, row) {
                    let description = row.geographyMap.description;
                    let ref = "";

                    if (description != null && description.toString().length >= 4) {
                        ref = "<button class='btn btn-info' onclick='getInfoMap(" + data + ");' data-placement=\"top\" title=\"" + data + "\">";
                        return ref + description.toString().substring(0, 4) + "</a>";
                    } else {
                        ref = "<button class='btn btn-info' onclick='getInfoMap(" + data + ");' data-placement=\"top\" title=\"" + data + "\">";
                        return ref + data + "</a>";
                    }




                }
            },
            {
                "data": "orderTerm",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return data.substring(0, 10).replaceAll("-", ".");
                    }
                    return data;

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
                    return renderLanguageState(data, row);
                }
            },
            {
                "data": "geographyMap.color",
                "render": function (data, type, row) {
                    if (data.toString().length < 15)
                        return data;
                    else return '<div class="overflow-auto" style="max-width: 240px; max-height: 40px">' + data + '</div>';
                }
            },
            {
                "data": "geographyMap.light",
                "render": function (data, type, row) {
                     return data;
                }
            },
            {
                "data": "geographyMap.isMultiLevel",
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
                    let msg = 'Вы уверенны, что заказ отправлен?';
                    if (data >= 6) {
                        if (data === 9 && row.isAvailability) {
                            msg = "Вы уверены, что заказ готов для наличия?"
                            return "<button class='btn btn-danger' onclick='setCondition(" + row.id + "," + 10 + ",\"" + msg + "\");'>Внести в наличие" + "</button>";
                        } else return "<button class='btn btn-danger' onclick='setCondition(" + row.id + "," + data + ",\"" + msg + "\");'>" + getCondition(data) + "</button>";
                    } else {
                        return getCondition(data)
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

