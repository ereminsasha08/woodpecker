const mapsAjaxUrl = "rest/availability/";
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
                "data": "id"
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
                        return "<button class='btn btn-info small' onclick='setLaser(" + row.id + ");'>Нет</button>";
                    }
                }
            },
            {
                "data": "stage",
                "render": function (date, type, row) {
                    if (date >= 6) {
                        return "<button class='btn btn-danger' onclick='createOrder(" + row.id + ");'>" + getCondition(date) + "</button>";
                    } else {
                        return getCondition(date)
                    }
                }
            },
        ],
    });
});


function createOrder(id) {
    form.find(":input").val("");
    $("#editRow").modal();
    $.get("rest/orders/" + id, function (data) {
        $.each(data, function (key, value) {
            if (value != null)
                if (key === "geographyMap") {
                    $.each(data[key], function (key, value) {
                        let a = document.getElementById(key);
                        if (a != null)
                            a.value = value
                    });
                } else {
                    let a = document.getElementById(key);
                    if (a != null)
                        a.value = value
                }
        });
    });

}
function save() {
    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        ctx.updateTable();
        successNoty("Сохранено");
    });
}
function getInfoCut(id) {
    $("#info-cut").modal();
    $.get("rest/cut/info/" + id,
        function (data) {
            let listSelect = "";
            $.each(data, function (index, value) {
                listSelect += '<div className="row">' +
                    '<div className="col">' +
                    ' <span id ="' + index + '">"' + value + '"</span>' +
                    '</div>' +
                    ' </div>'
            });
            document.getElementById("info").innerHTML = listSelect;
            $('#info-cut').modal();
        });
}

