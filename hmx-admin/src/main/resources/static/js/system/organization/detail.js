$(function () {
    TableInit();
    validParam();
    loadEvent();
});

function loadEvent(){
//     $('#editForm').on('click', '.source', function (e) {
//         if($(this).is(':checked')){
//             $(this).prop("checked", true);
//         }else {
//             $(this).prop("checked", false);
//         }
//     })
}

function TableInit() {
    var option = {
        url: '/system/organization/getUserLists',
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
                    var contractInfo = '<a href="javascript:void(0)"  title="移除">移除</a>';
                    return contractInfo;
                }
            }
        ]
    };
    $('#organizationTable').bootstrapTable($.initTableArg(option));
}

function getSearchParams() {
    var params = {
        id: $("#organizationId").val(),
    }
    return params;
}

function updateOrganization() {//更新组织机构信息
    $('#editForm').bootstrapValidator('validate');
    if ($("#editForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
        var chk_value =[]//定义一个数组
        $('input[name="source"]:checked').each(function(){//遍历每一个名字为nodes的复选框，其中选中的执行函数
            chk_value.push($(this).val());//将选中的值添加到数组chk_value中
        });
        console.log(chk_value);
        var params = {
            id: $("#organizationId").val(),
            name: $("#name").val(),
            detail: $("#detail").val(),
            code:$("#code").val(),
            source:chk_value
        }
        if($('#jkHouse').is(':checked')){ //数据来源
            params.source.push($('#jkHouse').val())
        }
        if($('#health').is(':checked')){ //数据来源
            params.source.push($('#health').val())
        }
        console.log(params)
        $.fn.getAjaxJSON('post', '/system/organization/updateOrganization', params, function (result, e) {
            $.fn.messageBox('success', '修改成功！');
        },{traditional: true,async : false});
    }
}

//清除弹窗原数据
$("#organizationModal").on("hidden.bs.modal", function () {
    $("#organizationModal input").val('');
    $("#addForm").data('bootstrapValidator').resetForm(true);
    validParam();
});

function addOrganizationModel() {
    var id = $("#organizationId").val();
    $("#organizationModal").modal({
        backdrop: "static",//指定一个静态背景，当用户点击背景处，modal界面不会消失
        keyboard: true//当按下esc键时，modal框消失
    });
}

function addNextOrganization() {
    $('#addForm').bootstrapValidator('validate');
    if ($("#addForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
        var grade = $("#grade").val()
        if (grade == 3) {
            $.fn.messageBox('success', '该级别下不允许增加子机构！');
        } else {
            var params = {
                sid: $("#organizationId").val(),
                name: $("#nameAdd").val(),
                code: $("#codeAdd").val(),
                grade: grade * 1 + 1,
                detail: $("#detailAdd").val(),
            }
            $.fn.getAjaxJSON('post', '/system/organization/addOrganization', params, function (result, e) {
                $.fn.messageBox('success', '添加成功！');
                $("#organizationModal").modal('hide');
            });
        }
    }
}

function deleteOrganization() {
    $.fn.getAjaxJSON('post', '/system/organization/deleteOrganization', {id: $("#organizationId").val()}, function (result, e) {
        $.fn.messageBox('success', '删除成功！');
        $("#organizationModal").modal('hide');
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
            nameAdd: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    }
                }
            },
            codeAdd: {
                validators: {
                    notEmpty: {
                        message: '机构编码不能为空'
                    }
                }
            }
        }
    });

    $('#editForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    }
                }
            },
            code: {
                validators: {
                    notEmpty: {
                        message: '机构编码不能为空'
                    }
                }
            }
        }
    });
}