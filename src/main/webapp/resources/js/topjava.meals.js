var mealAjaxUrl = "profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("profile/meals/", updateTableByData);
}

$.ajaxSetup({
    "converters": {
        "text json": function (text) {
            var json = JSON.parse(text);
            try {
                $(json).each(function () {
                    this['dateTime'] = this['dateTime'].replace("T", " ").substring(0, 16);
                });
            } catch (e) {
            }
            return json;
        },
    },
})

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "createdRow": function (row, data) {
                $(row).attr("data-mealExcess", data.excess);
            },
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        }),
        updateTable: updateFilteredTable
    });

    //datetimepicker
    $.datetimepicker.setLocale(navigator.language.substring(0, 2));
    var i18n = {
        ru: {
            months: [
                'Январь', 'Февраль', 'Март', 'Апрель',
                'Май', 'Июнь', 'Июль', 'Август',
                'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь',
            ],
            dayOfWeek: [
                "Вс", "Пн", "Вт", "Ср",
                "Чт", "Пт", "Сб",
            ]
        }
    };

    $("#startDate").datetimepicker({
        i18n: i18n,
        closeOnDateSelect: true,
        timepicker: false,
        format: "Y-m-d",
        onShow: function (ct) {
            var date = $("#endDate").val();
            this.setOptions({
                maxDate: date ? date : false
            });
        }
    });

    $("#endDate").datetimepicker({
        i18n: i18n,
        closeOnDateSelect: true,
        timepicker: false,
        format: "Y-m-d",
        onShow: function (ct) {
            var date = $("#startDate").val();
            this.setOptions({
                minDate: date ? date : false
            });
        }
    });

    $("#startTime").datetimepicker({
        i18n: i18n,
        closeOnTimeSelect: true,
        datepicker: false,
        format: "H:i",
        onShow: function (ct) {
            var time = $("#endTime").val();
            this.setOptions({
                maxTime: time ? time : false
            });
        }
    });

    $("#endTime").datetimepicker({
        i18n: i18n,
        closeOnTimeSelect: true,
        datepicker: false,
        format: "H:i",
        onShow: function (ct) {
            var time = $("#startTime").val();
            this.setOptions({
                minTime: time ? time : false
            });
        }
    });

    $("#dateTime").datetimepicker({
        i18n: i18n,
        closeOnTimeSelect: true,
        format: "Y-m-d H:i",
    });

});