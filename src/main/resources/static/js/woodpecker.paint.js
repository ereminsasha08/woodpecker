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
                        if (row.isColorPlywood) {
                            return "<button class='btn btn-danger' onclick='getInfoCut(" + row.id + "," + row.isColorPlywood + ");'>" + date + "</button>";

                        } else {
                            return "<button class='btn btn-warning' onclick='getInfoCut(" + row.id + "," + row.isColorPlywood + ");'>" + date + "</button>";
                        }
                    } else {
                        return "<button class='btn btn-info small' onclick='setLaser(" + row.id + ");'>Нет</button>";
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
                "data": "stage",
                "render": function (date, type, row) {
                    if (date === 5)
                        return "<button class='btn btn-danger' onclick='setStagePaint(" + row.id + ");'>Покраненно!</button>";
                    if (date === 4)
                        return "<button class='btn btn-secondary' onclick='setPainter(" + row.id + ");'>Назначить художника</button>";
                    else
                        return "Ждет покраску досок";

                }
            },
        ],
    });
});

function setIsColorPlywood(id) {
    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl + id,
    }).done(function () {
        ctx.updateTable();
        successNoty("Покрашенно");
        $('#info-cut').modal("hide");
    });
}

function setStagePaint(id) {
    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl + "stage/" + id,
    }).done(function () {
        ctx.updateTable();
        successNoty("Покрашенно");
        $('#info-cut').modal("hide");
    });
}

function setPainter(id) {
    form = $('#formNamePainter');
    form.find(":input").val("");
    document.getElementById('id').value = id;
    $("#modalNamePainter").modal();
}

function savePainter() {
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

function getInfoCut(id, isColor) {
    $("#info-cut").modal();
    $.get("rest/cut/info/" + id,
        function (data) {
            let listSelect = "";
            $.each(data, function (index, value) {
                listSelect += '<div class="row">' +
                    '<div class="col">' +
                    ' <span id ="' + index + '">"' + value + '"</span>' +
                    '</div>' +
                    ' </div>'
            });
            document.getElementById("info").innerHTML = listSelect;
            let cancel = "<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\" onclick=\"closeNoty()\"><span class=\"fa fa-close\"></span>Отмена</button>";
            let paint = "<button class=\'btn btn-danger\' onclick=\'setIsColorPlywood(" + id + ");\'>Покрасить доски</button>";
            if (isColor)
                cancel += paint;
            document.getElementById("infoButton").innerHTML = cancel;

        });
}