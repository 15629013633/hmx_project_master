$(function () {
    validParam();
})

function updateCategory(v) {
    $('#formUpdate').bootstrapValidator('validate');
    if ($("#formUpdate").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
        if(v == 1){
            $.fn.getAjaxJSON('post', '/category/add', getParameter(), function (result, e) {
                $.fn.messageBox('success', '操作成功！',function () {
                    var model = $("#tradeList").attr("modelValue");
                    $("#"+model).remove();
                    $(".modal-backdrop").remove();
                    $("#tradeList").bootstrapTable('refresh');
                });
            });
        }else{
            $.fn.getAjaxJSON('post', '/category/edit', getParameter(), function (result, e) {
                $.fn.messageBox('success', '操作成功！',function () {
                    var model = $("#tradeList").attr("modelValue");
                    $("#"+model).remove();
                    $(".modal-backdrop").remove();
                    $("#tradeList").bootstrapTable('refresh');
                });
            });
        }

    }
}

function getParameter() {
    var params = {
        categoryId: $("#categoryId").val(),
        categoryName: $("#categoryName").val(),
        isClose: $("#isClose").val(),
        version: $("#version").val(),
        categoryType: $("#categoryType").val()
    };
    return params;
}

function validParam() {
    $('#formUpdate').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            categoryName: {
                validators: {
                    notEmpty: {
                        message: '分类名称不能为空'
                    }
                }
            },
            sort: {
                validators: {
                    notEmpty: {
                        message: '排序不能为空'
                    }
                }
            },
            isClose: {
                validators: {
                    notEmpty: {
                        message: '是否展示首页不能为空'
                    }
                }
            },
            version: {
                validators: {
                    notEmpty: {
                        message: '版本编号不能为空'
                    }
                }
            }
        }
    });
}