const orderAjaxUrl = "rest/order/";
const cutAjaxUrl = "rest/cut/"
// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: cutAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: 'GET',
            url: cutAjaxUrl,
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
                    if (data.isPlexiglass) {
                        return '<i class="fa fa-exclamation" aria-hidden="true"></i>';
                    }
                    return '';
                }
            },
            {
                "data": "id",
                "render": function (data, type, row) {
                    let ref = "<button class='btn btn-info' onclick='getInfoMap(" + data + ");' data-placement=\"top\" title=\"" + data + "\">";
                    return ref + data + "</a>";
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
                "data": "geographyMap.color",
                "render": function (data, type, row) {
                    if (data.toString().length < 15)
                        return data;
                    else return '<div class="overflow-auto" style="max-width: 240px; max-height: 40px">' + data + '</div>';
                }
            },
            {
                "data": "laser",
                "render": function (data, type, row) {
                    if (data != null) {
                        return "<button class='btn btn-warning' onclick='getInfoCut(" + row.id + "," + row.geographyMap.isColorPlywood + ");'>" + data + "</button>";
                    } else {
                        return "<button class='btn btn-danger' onclick='setLaser(" + row.id + ");'>Пилить</button>";
                    }
                }
            },

        ],

    });
});

function setLaser(id) {
    let find = $('#setLaserForm').find(":input").val("");
    document.getElementById('orderId').value = id;
    $("#setLaser").modal();
}

function saveLaserForm() {
    $.ajax({
        type: "PATCH",
        url: cutAjaxUrl,
        data: $('#setLaserForm').serialize(),
    }).done(function () {
        ctx.updateTable();
        successNoty("Лазер и листы установлены");
        $("#setLaser").modal("hide");
    });
}

function changeLaser() {
    $.ajax({
        type: "POST",
        url: cutAjaxUrl,
        data: $('#laserForm').serialize(),
    }).done(function () {
        ctx.updateTable();
        successNoty("Лазер изменен");
    });
}

function getInfoCut(id, isColorPlywood) {
    $("#infoListCut").modal();
    $.get(cutAjaxUrl + "info/" + id,
        function (data) {
            let listSelect = "";
            if (isColorPlywood)
                listSelect = '<div>' +
                    '  <h5>' +
                    'Карта из покрашенных досок' +
                    '  </h5>' +
                    '</div>'
            $.each(data, function (index, value) {
                listSelect +=
                    '<div class="row">' +
                    ' <div class="form-group col-6">' +
                    '  <output type="text" class="form-control" id="list" name="list"> ' +
                    ' <span id ="' + index + '">"' + value + '"</span>' +
                    '</output>' +
                    '</div>' +
                    '<div class="form-group col-6">' +
                    "<input " + ' class="form-check-input"' + "type='checkbox'" + ' id="' + index + '"' + (value.endsWith("Лист готов") ? "checked" : "") + " onclick='updateInfoAboutCut($(this)," + id + "," + index + "," + isColorPlywood + ");'/>" +
                    '<label for="' + id + '" class="form-check-label">' + (value.endsWith("Лист загравирован") || value.endsWith("Лист готов") ? '  Выпилен?' : '  Загравирован?') + '</label>' +
                    '</div>' +
                    '</div>'
            });
            document.getElementById("infoList").innerHTML = listSelect;
            document.getElementById("laserId").value = id;
        });
}


function updateInfoAboutCut(chkbox, id, index, isColorPlywood) {
    const enabled = chkbox.is(":checked");
    $.ajax({
        url: cutAjaxUrl + "info/" + id,
        type: "POST",
        data: {
            listIsComplete: enabled,
            numberList: index
        }
    }).done(function () {
        // chkbox.closest("tr").attr("data-user-enabled", enabled);
        successNoty(enabled ? "Ствтус изменен" : "Отмена");
    }).fail(function () {
        $(chkbox).prop("checked", !enabled);
    });
    setTimeout(() => getInfoCut(id, isColorPlywood), 400)

}