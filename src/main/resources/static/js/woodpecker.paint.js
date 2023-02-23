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
                "data": "geographyMap",
                "orderable": false,
                "render": function (data, type, row) {
                    if (data.isMonochromatic) {
                        return '<i class="fa fa-exclamation" aria-hidden="true"></i>';
                    }
                    return '';
                }
            },
            {
                "data": "id",
                "orderable": false,
                "render": function (data, type, row) {
                    let ref = "<button class='btn btn-info' onclick='getInfoMap(" + data + ");' data-placement=\"top\" title=\"" + data + "\">";
                    return ref + data + "</a>";
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
                "orderable": false
            },
            {
                "data": "geographyMap.size",
                "orderable": false
            },
            {
                "data": "geographyMap.language",
                "orderable": false,
                "render": function (data, type, row) {
                    let language = data;
                    let city = "без ст"
                    if (data.includes("Русский")) {
                        language = "Рус";
                    } else if (data.includes("Английский"))
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
                "data": "geographyMap.color",
                "orderable": false,
                "render": function (data, type, row) {
                    if (data.toString().length < 15)
                        return data;
                    else return '<div class="overflow-auto" style="max-width: 180px; max-height: 40px">' + data + '</div>';
                }
            },
            {
                "data": "laser",
                "orderable": false,
                "render": function (data, type, row) {
                    if (data != null) {
                        if (row.isColorPlywood) {
                            return "<button class='btn btn-warning' onclick='getInfoCut(" + row.id + "," + row.isColorPlywood + ");'>" + data + "</button>";

                        } else {
                            return "<button class='btn btn-info' onclick='getInfoCut(" + row.id + "," + row.isColorPlywood + ");'>" + data + "</button>";
                        }
                    } else {
                        return "<button class='btn btn-secondary small' onclick='setLaser(" + row.id + ");'>Нет</button>";
                    }

                }
            },
            {
                "data": "namePainter",
                "orderable": false,
                "render": function (data, type, row) {
                    if (data != null)
                        return data;
                    return "Не назначен";
                }
            },
            {
                "data": "stage",
                "orderable": false,
                "render": function (data, type, row) {
                    if (data === 5)
                        return "<button class='btn btn-danger' onclick='setStagePaint(" + row.id + ");'>Покраненно!</button>";
                    if (data === 4)
                        return "<button class='btn btn-warning' onclick='setPainter(" + row.id + ");'>Назначить художника</button>";
                    else
                        return getCondition(data);

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
    if (confirm('Вы уверенны?')) {
        $.ajax({
            type: "PATCH",
            url: ctx.ajaxUrl + "stage/" + id,
        }).done(function () {
            ctx.updateTable();
            successNoty("Покрашенно");
            $('#info-cut').modal("hide");
        });
    }
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
            let cancel = "<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\" onclick=\"closeNoty()\"><span class=\"fa fa-close\"></span>Отмена</button>";
            let paint = "<button class=\'btn btn-danger\' onclick=\'setIsColorPlywood(" + id + ");\'>Покрасить доски</button>";
            if (isColor)
                cancel += paint;
            document.getElementById("infoButton").innerHTML = cancel;

        });
}