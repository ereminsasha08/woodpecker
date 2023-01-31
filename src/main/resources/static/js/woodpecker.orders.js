const mapsAjaxUrl = "rest/orders/";
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
                "data": "id",
                "render": function (date, type, row) {
                    let ref = "<button class='btn btn-info' onclick='get(" + date + ");' data-placement=\"top\" title=\"" + date + "\">";
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
                    let s = "EN";
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
                    let ref = "<a href=\"javascript:void(0);\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + date + "\">"
                    if (date.length > 10) {
                        date = date.substring(0, 7) + "...";
                    }
                    return ref + date + "</a>";
                }
            },
            {
                "data": "laser",
                "render": function (date, type, row) {
                    if (date != null) {
                        return "<button class='btn btn-warning' onclick='getInfoCut(" + row.id + ");'>" + date + "</button>";
                    } else {
                        return "<button class='btn btn-danger' onclick='setLaser(" + row.id + ");'>Пилить</button>";
                    }
                }
            },
        ],
    });
});

function get(id) {
    const form = $('#orderInfo');
    form.find(":input").val("");
    $("#orderInfo").modal();
    $.get(ctx.ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            if (value != null)
                if (key === "geographyMap") {
                    $.each(data[key], function (key, value) {
                        let elementById = document.getElementById(key);
                        if (elementById != null) {
                            elementById.innerText += value;
                        }
                    });
                }

        });
    });
}


function setLaser(id) {
    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl + id,
    }).done(function () {
        ctx.updateTable();
        successNoty("Лазер установлен");
    });
}

function getInfoCut(id) {
    $("#info-cut").modal();
    $.get(ctx.ajaxUrl + "infocut/" + id,
        function (data) {
            let listSelect = "";
            $.each(data, function (index, value) {
                // listSelect += '<p id ="' + index + '">"' + value + '"</p>' +
                //     "<input type='checkbox' " + (value.endsWith("Готов") ? "checked" : "") + " onclick='enable($(this)," + id + ");" +
                //     "'/>";
                // +'<br>'
                listSelect += '<div className="row">' +
                    '<form>' +
                    '<div className="col">' +
                    ' <span id ="' + index + '">"' + value + '"</span>' +
                    '</div>' +
                    '  <div className="col">' +
                    "<input type='checkbox'" + ' id="' + index + '"' + (value.endsWith("Готов") ? "checked" : "") + " onclick='enable($(this)," + id + "," + index + ");'/>" +
                    ' </div>' +
                    ' </form>' +
                    ' </div>'
            });
            document.getElementById("info").innerHTML = listSelect;
            $('#info-cut').modal();
        });
}


function enable(chkbox, id, index) {
    const enabled = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: ctx.ajaxUrl + "infocut/" + id,
        type: "POST",
        data: {
            listIsComplete: enabled,
            numberList: index
        }
    }).done(function () {
        // chkbox.closest("tr").attr("data-user-enabled", enabled);
        successNoty(enabled ? "Выпилена" : "Отмена");
    }).fail(function () {
        $(chkbox).prop("checked", !enabled);
    });
}