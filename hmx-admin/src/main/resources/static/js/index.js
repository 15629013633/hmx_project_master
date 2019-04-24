$(function () {
    $(".modifyCellphone").hide();
    validParamIndex();
    $('body').on('click', '.breadcrumb>li>a', function(){
        var url = $(this).attr('url');
        $('#aio-contentBox').load(url);
    })
})

//注销用户
function logout(){
    $.fn.messageBox('warning', '是否确认退出账号？',function (isOut) {
        if(isOut){
            window.location.href = '/logout';
        }
    });
}

//清除弹窗原数据
$("#userInfoModel").on("hidden.bs.modal", function () {
    window.location.href = '/';
});

//清除弹窗原数据
$("#userModifyPasswordModel").on("hidden.bs.modal", function () {
    $("#userModifyPasswordModel input").val('');
});

function userInfoClick() {
    $("#userInfoModel").modal({
        backdrop: "static",//指定一个静态背景，当用户点击背景处，modal界面不会消失
        keyboard: true//当按下esc键时，modal框消失
    });
}

function userModifyPasswordClick() {
    $("#userModifyPasswordModel").modal({
        backdrop: "static",//指定一个静态背景，当用户点击背景处，modal界面不会消失
        keyboard: true//当按下esc键时，modal框消失
    });
}

$(".cellPhoneModifyButton").click(function () {
    $(".modifyCellphone").toggle();
    $(".modifyCellphone input").val('');
});

$(".updateUserModel").click(function () {
    $('#addIndexUserForm').bootstrapValidator('validate');
    if ($("#addIndexUserForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
        $.fn.getAjaxJSON('post', '/system/userManage/updateUser', getParameter(), function (result, e) {
            $.fn.messageBox('success', '操作成功！',function () {
                $("#userInfoModel").modal('hide');
            });
        });
    }
});

$(".updatePasswordModel").click(function () {
    var params = {
        userId: $("#userInfoId").val(),
        newPassword: $("#newPassword").val(),
        isReset: 0
    };
    $('#modifyPasswordForm').bootstrapValidator('validate');
    if ($("#modifyPasswordForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
        $.fn.getAjaxJSON('post', '/system/userManage/modifyPassword', params, function (result, e) {
            $.fn.messageBox('success', '操作成功！',function () {
                $("#userModifyPasswordModel").modal('hide');
            });
        });
    }
});

function getParameter() {
    var gender = $("#gender").val();
    if(gender == '男'){
        gender=1
    }else if(gender == '女'){
        gender=2
    }
    var params = {
        userName: $("#userName").val(),
        userAliase: $("#userAliase").val(),
        userPhone: $("#userPhone").val(),
        verifyCode: $("#verifyCode").val(),
        id: $("#userInfoId").val(),
        gender: gender
    };
    return params;
}

function validParamIndex() {
    $('#addIndexUserForm').bootstrapValidator({
        message: 'This value is not valid',
        live: 'disabled',//验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
        excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件，比如被禁用的或者被隐藏的
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            usernameInfo: {
                validators: {
                    notEmpty: {
                        message: '账号不能为空'
                    }
                }
            },
            nameInfo: {
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    }
                }
            },
            phoneValidation: {
                validators: {
                    regexp: {//正则验证
                        regexp: /^\d{6}$/,
                        message: '请输入正确的验证码'
                    }
                }
            }
        }
    });

    $("#modifyPasswordForm").bootstrapValidator({
        message: 'This value is not valid',
        live: 'disabled',//验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
        excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件，比如被禁用的或者被隐藏的
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            oldPassword: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    identical: {
                        field: 'newPassword',
                        message: '两次密码输入不一致'
                    },
                    stringLength: {//检测长度
                        min: 6,
                        max: 30,
                        message: '密码长度必须在6-30之间'
                    }
                }
            },
            newPassword: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    identical: {
                        field: 'oldPassword',
                        message: '两次密码输入不一致'
                    },
                    stringLength: {//检测长度
                        min: 6,
                        max: 30,
                        message: '密码长度必须在6-30之间'
                    }
                }
            }
        }
    })
}

var countdown = 5, timeOut;
function settime(val) {
    var cellphone = $("#cellPhoneModify").val();
    if (cellphone != null && cellphone != null && isPoneAvailable(cellphone)) {
        //发送验证码
        if (countdown == 5) {
            $.fn.getAjaxJSON('post', '/system/userManage/getValidation', {cellPhone: cellphone}, function (result, e) {
                $.fn.messageBox('success', '操作成功！', function () {
                    val.setAttribute("disabled", true);
                    $(val).text("重新发送(" + countdown + "s)");
                    timeOut = setInterval(function () {
                        if (countdown == 0) {
                            val.removeAttribute("disabled");
                            $(val).text('获取验证码')
                            clearInterval(timeOut);
                            countdown = 5
                        } else {
                            val.setAttribute("disabled", true);
                            countdown--;
                            $(val).text("重新发送（" + countdown + "s）");
                        }
                    }, 1000)
                });
            });
        }
    } else {
        $.fn.messageBox('warning', '请输入正确手机号！');
    }
}

function isPoneAvailable(str) {
    var myreg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
    if (!myreg.test(str)) {
        return false;
    } else {
        return true;
    }
}
