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
                "data": "id",
                "render": function (date, type, row) {
                    let ref = "<button class='btn btn-info' onclick='getInfoMap(" + date + ");' data-placement=\"top\" title=\"" + date + "\">";
                    return ref + date + "</a>";
                }
            },
            {
                "data": "orderTerm",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.substring(0, 10).replaceAll("-", ".");
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
                    let language = date;
                    let city = "без ст"
                    if (date.includes("Русский")) {
                        language = "Рус";
                    } else if (date.includes("Английский"))
                        language = "Анг"
                    if (row.geographyMap.isState)
                        city = "шт";
                    else if (row.geographyMap.isCapital)
                        city = "ст";
                    return language + " " + city;
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
                    // let ref = "<a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + date + "\">"
                    // if (date.length > 10) {
                    //     date = date.substring(0, 7) + "...";
                    // }
                    return date;
                }
            },
            {
                "data": "laser",
                "render": function (date, type, row) {
                    if (date != null) {
                        return "<button class='btn btn-warning' onclick='getInfoCut(" + row.id + "," + row.geographyMap.isColorPlywood + ");'>" + date + "</button>";
                    } else {
                        return "<button class='btn btn-danger' onclick='setLaser(" + row.id + ");'>Пилить</button>";
                    }
                }
            },

        ],

    });
});

function setLaser(id) {
    $.ajax({
        type: "PATCH",
        url: cutAjaxUrl + id,
    }).done(function () {
        ctx.updateTable();
        successNoty("Лазер установлен");
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
                listSelect += '<div class="row">' +
                   ' <div class="form-group col-6">'+
                      '  <output type="text" class="form-control" id="list" name="list"> ' +
                    ' <span id ="' + index + '">"' + value + '"</span>' +
                    '</output>' +
                    '</div>' +
                    '<div class="form-group col-6">' +
                    "<input "+' class="form-check-input"'+"type='checkbox'" + ' id="' + index + '"' + (value.endsWith("Лист готов") ? "checked" : "") + " onclick='updateInfoAboutCut($(this)," + id + "," + index + "," + isColorPlywood + ");'/>" +
                    '<label for="' + id + '" class="form-check-label">' + (value.endsWith("Лист загравирован") || value.endsWith("Лист готов") ? '  Выпилен?' : '  Загравирован?') + '</label>' +
                    '</div>' +
                    '</div>'
            });
            document.getElementById("infoList").innerHTML = listSelect;
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