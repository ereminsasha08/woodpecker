const paintAjaxUrl = "rest/paints/";
// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: paintAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: 'GET',
            url: paintAjaxUrl,
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
                        return date;
                    } else {
                        return "<button class='btn btn-danger' onclick='setIsColorPlywood(" + row.id + ");'>Покрасить доски</button>";
                    }
                }
            },
            {
                "data": "namePainter",
                "render": function (date, type, row) {
                    if (date != null)
                        return date;
                    return "Не назначен";
                }
            },
            {
                "data": "geographyMap.conditionMap",
                "render": function (date, type, row) {
                    if (date === 5)
                        return "<button class='btn btn-danger' onclick='setIsColorPlywood(" + row.id + ");'>Покраненно!</button>";
                    if (date === 4)
                        return "<button class='btn btn-secondary' onclick='setPainter(" + row.id + ");'>Назначить художника</button>";
                    return "Ждет доски";
                }
            },
        ],
    });
});

function get(id) {
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
        $('#orderInfo').modal();
    });
}


function setIsColorPlywood(id) {
    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl + id,
    }).done(function () {
        ctx.updateTable();
        successNoty("Покрашенно");
    });
}

function setPainter(id) {
    form = $('#formNamePainter');
    form.find(":input").val("");
    document.getElementById('id').value = id;
    $("#modalNamePainter").modal();
}
function savePainter(){
    form = $('#formNamePainter');
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + "painter/",
        data: form.serialize()
    }).done(function () {
        $("#modalNamePainter").modal("hide");
        ctx.updateTable();
        successNoty("Художник назначен");
    });
}