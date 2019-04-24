$(function () {
    initTable();
})

function initTable() {
    var option = {
        url: '/customer/getLists',
        queryParams: function (params) {
            var param = getSearchParams();
            param.page = params.pageNumber - 1;
            param.size = params.pageSize;
            return param;
        },//传递参数
        columns: [
            {
                field: 'id',
                title: '编号',
                halign: 'center',
                align: 'center'
            }, {
                field: 'name',
                title: '名称',
                halign: 'center',
                align: 'left'
            }, {
                field: 'cellPhone',
                title: '电话号码',
                halign: 'center',
                align: 'left'
            }, {
                title: '操作',
                halign: 'center',
                align: 'center',
                formatter: function (value, row, index) {
                    var contractInfo = '<a href="javascript:void(0)" class="fa-remove" title="移除">移除</a>';
                    return contractInfo;
                }
            }
        ]
    };
    $('#tableList').bootstrapTable($.initTableArg(option));
}

function getSearchParams() {
    var params = {
        //id: $("#organizationId").val(),
    }
    return params;
}