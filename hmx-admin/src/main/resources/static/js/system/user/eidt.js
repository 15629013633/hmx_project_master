$(function () {
    validParam();
})

function updateUser() {
    $('#formUpdate').bootstrapValidator('validate');

    if ($("#formUpdate").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
        $.fn.getAjaxJSON('post', '/system/userManage/addOrUpdateUser', getParameter(), function (result, e) {
            $.fn.messageBox('success', '操作成功！',function () {
                var model = $("#tradeList").attr("modelValue");
                $("#"+model).remove();
                $(".modal-backdrop").remove();
                $("#tradeList").bootstrapTable('refresh');
            });
        });
    }
}

function getParameter() {
    var params = {
        id: $("#userId").val(),
        userName: $("#userName").val(),
        userAliase: $("#userAliase").val(),
        userPhone: $("#cellPhoneAdd").val(),
        role: $("#role").val(),
        gender: $("#gender").val(),
        type: $("#type").val(),
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
            usernameAdd: {
                validators: {
                    notEmpty: {
                        message: '账户不能为空'
                    }
                }
            },
            nameAdd: {
                validators: {
                    notEmpty: {
                        message: '名称不能为空'
                    }
                }
            },
            cellPhoneAdd: {
                validators: {
                    notEmpty: {
                        message: '手机号码不能为空'
                    },
                    regexp: {//正则验证
                        regexp: /^[1][3,4,5,6,7,8,9][0-9]{9}$/,
                        message: '请输入正确的验证码'
                    }
                }
            },
            role: {
                validators: {
                    notEmpty: {
                        message: '角色不能为空'
                    }
                }
            },
            organization: {
                validators: {
                    notEmpty: {
                        message: '组织机构不能为空'
                    }
                }
            }
        }
    });
}