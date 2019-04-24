$(function () {
    initTable();
    alertModal();
    validParam();
});

function initTable() {
    var option = {
        url: '/system/roleManage/getRoleLists',
        queryParams: function (params) {
            var param = {};
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
                align: 'center',
            }, {
                field: 'detail',
                title: '描述',
                halign: 'center',
                align: 'center'
            }, {
                title: '操作',
                halign: 'center',
                align: 'center',
                formatter: function (value, row, index) {
                    var isUpdate = '<a href="javascript:void(0)" class="update"  title="修改" onclick="modifyRole(this)">修改</a>';
                    var isDelete = '<a href="javascript:void(0)" class="delete" title="删除" onclick="deleteRole(' + row.id + ')">删除</a>';
                    if (row.name == 'ROLE_ADMIN') {
                        isUpdate = '';
                        isDelete = '';
                    }
                    var contractInfo = '<a href="javascript:void(0)" class="detail detailModel" title="详情">详情</a>' + isUpdate + isDelete;
                    return contractInfo;
                }
            }
        ]
    };
    $('#roleTable').bootstrapTable($.initTableArg(option));
} //表格

function alertModal() {
    $('#roleTable').on('click', '.detailModel', function () {
        var roleId = $(this).parent().parent().find("td").eq(0).text();
        $.fn.popWindow({title:'角色详情',width:'40%'}, '/system/roleManage/detail?roleId=' + roleId, function ($content) {
        })
    })
}

//清除弹窗原数据
$("#roleModal").on("hidden.bs.modal", function () {
    $("#roleModal input").val('');
    $("#addForm").data('bootstrapValidator').resetForm(true);
    validParam();
});

function modifyRole(element) {
    var roleId = $(element).parent().parent().find("td").eq(0).text();
    var name = $(element).parent().parent().find("td").eq(1).text();
    var detail = $(element).parent().parent().find("td").eq(2).text();
    $("#roleModal").modal({
        backdrop: "static",//指定一个静态背景，当用户点击背景处，modal界面不会消失
        keyboard: true//当按下esc键时，modal框消失
    });
    $("#roleId").val(roleId);
    $("#name").val(name);
    $("#detail").val(detail);
}

function updateRole(id) {
    $('#addForm').bootstrapValidator('validate');
    if ($("#addForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
        var param = {"id": $("#roleId").val(), "name": $("#name").val(), "detail": $("#detail").val()};
        $.fn.getAjaxJSON('post', '/system/roleManage/updateRole', param, function (result, e) {
            $.fn.messageBox('success', '更新成功！');
            $("#roleModal").modal('hide');
            $("#roleTable").bootstrapTable('refresh');
        });
    }
}

function deleteRole(id) {
    $.fn.getAjaxJSON('post', '/system/roleManage/deleteRole', {"roleId": id}, function (result, e) {
        $.fn.messageBox('success', '删除成功！');
        $("#roleModal").modal('hide');
        $("#roleTable").bootstrapTable('refresh');
    });
}

function validParam() {
    $('#addForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: '名称不能为空'
                    }
                }
            },
            detail: {
                validators: {
                    notEmpty: {
                        message: '描述不能为空'
                    }
                }
            }
        }
    });
}


