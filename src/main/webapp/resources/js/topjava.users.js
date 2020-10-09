// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email",
                        "render": function (data) {
                            return "<a href='mailto:${data}'>${data}</a>".replaceAll("${data}", data);
                        }
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled",
                        "render": function (data, type, full) {
                            return "<input type='checkbox' onclick=changeStatus(this) ".concat(data ? "checked" : "").concat("/>");
                        }
                    },
                    {
                        "data": "registered",
                        "render": function (date) {
                            return date.replace("T", " ").substring(0, 16);
                        }
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false,
                        "render": function () {
                            return "<button class='btn update'><span class='fa fa-pencil'></span></button>";
                        }
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false,
                        "render": function () {
                            return "<button class='btn delete'><span class='fa fa-remove'></span></button>";
                        }
                    }
                ],
                "createdRow": function (row, data, index) {
                    row = $(row);
                    row.attr("id", data.id);
                    if (!data.enabled) {
                        row.attr("class", "text-muted");
                    }
                },
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            }),
            getData: function () {
                return $.ajax({
                    type: "GET",
                    url: context.ajaxUrl
                });
            }
        }
    );
});

function changeStatus(checkbox) {
    checkbox = $(checkbox);
    let id = checkbox.parents("tr").attr("id");
    let status = checkbox.is(":checked");
    $.ajax({
        type: "PATCH",
        url: context.ajaxUrl + id + "?status=" + status

    }).done(function () {
        updateTable();
        successNoty(status ? "Enabled" : "Disabled")
    })

}