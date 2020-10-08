let needToFilter = false;

$(function () {
    makeEditable({
        "ajaxUrl": "profile/meals/",
        "datatableApi": $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date) {
                        return date.replace("T", " ").substring(0, 16);
                    }
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
                    "render": function () {
                        return "<button class='btn update'><span class='fa fa-pencil'></span></button>";
                    }
                },
                {
                    "defaultContent": "Remove",
                    "orderable": false,
                    "render": function () {
                        return "<button class='btn delete'><span class='fa fa-remove'></span></button>";
                    }
                }
            ],
            "rowId": function (data) {
                return data.id;
            },
            "createdRow": function (row, data, index) {
                $(row).attr("data-mealExcess", data.excess)
            },
            "order": [
                [
                    0, "desc"
                ]
            ]
        }),
        getData: function () {
            return $.ajax({
                type: "GET",
                url: context.ajaxUrl.concat(needToFilter ? "filter" : ""),
                data: $('form.filter').serialize()
            });
        }
    });
})

function filter() {
    needToFilter = true;
    updateTable()
}

function resetFilter() {
    $("form.filter").find(":input[type=date], :input[type=time]").val("");
    needToFilter = false;
    updateTable();
}