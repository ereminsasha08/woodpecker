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
                "render": function (data, type, row) {
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
                    return renderLanguageState(data, row);
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
                "data": "geographyMap.light",
                "render": function (date, type, row) {
                    if (type === "display" && !date.toString().toLowerCase().startsWith("без"))
                        return date;
                    else return "Нет";
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
                        return "<button class='btn btn-info' onclick='getInfoCut(" + row.id + ");'>" + date + "</button>";
                    } else {
                        return "<button class='btn btn-secondary small' onclick='setLaser(" + row.id + ");'>Нет</button>";
                    }
                }
            },
            {
                "data": "stage",
                "render": function (date, type, row) {
                    return "<button class='btn btn-warning' onclick='getOrderForModify(" + row.id + ");'>" + getCondition(date) + "</button>";

                }
            },
        ],
        "order": [
            [
                6,
                "asc",
                3,
                "asc",
            ]
        ]
    });
});

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

