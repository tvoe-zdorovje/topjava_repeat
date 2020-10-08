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
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered",
                        "render":function (date) {
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
                "rowId": function (data) {
                    return data.id;
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