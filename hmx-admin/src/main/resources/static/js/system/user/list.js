$(function () {
    //1.初始化Table
    new TableInit().Init();

    //2.初始化Button的点击事件
    /* var oButtonInit = new ButtonInit();
     oButtonInit.Init(); */

});


function TableInit() {
    var oTableInit = new Object();
    var grade = $("#organiztionInfo").val();
    //初始化Table
    this.Init = function () {
        $('#tradeList').bootstrapTable({
                url: '/system/userManage/getLists',     //请求后台的URL（*）
                queryParamsType: '',
                queryParams: function (params) {
                    var param = getSearchParams();
                    param.page = params.pageNumber - 1;
                    param.size = params.pageSize;
                    return param;
                },//传递参数
                // method: 'post',//请求方式,默认post
                pagination: true,//设置为 true 会在表格底部显示分页条
                pageNumber: 1,//如果设置了分页，首页页码，默认第一页
                pageSize: 10,//如果设置了分页，页面数据条数
                pageList: [5, 10, 20, 50, 100],//如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。
                search: false,//是否启用搜索框
                striped: true,//设置为 true 会有隔行变色效果
                height: 300,//定义表格的高度。
                cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性
                sortable: false, //是否启用排序
                sortOrder: "asc", //排序方式
                showRefresh: false, //是否显示刷新按钮
                minimumCountColumns: 2, //最少允许的列数
                clickToSelect: true,//是否启用点击选中行
                showToggle: false, //是否显示详细视图和列表视图的切换按钮
                cardView: false,//是否显示详细视图
                detailView: false,//是否显示父子表
                showFooter: false,//显示底部
                showColumns: false,
                //分页方式：client客户端分页，server服务端分页（*）
                sidePagination: "server",
                columns: [{
                    title: '序列',
                    halign: 'center',
                    align: 'center',
                    formatter: function (value,row,index) {
                        return index+1;
                    }
                },{
                    field: 'id',
                    title: '主键',
                    halign: 'center',
                    align: 'center',
                    visible: false
                }, {
                    field: 'userName',
                    title: '姓名',
                    halign: 'center',
                    align: 'center'
                }, {
                    field: 'userAliase',
                    title: '别名',
                    halign: 'center',
                    align: 'center'
                }, {
                    field: 'userPhone',
                    title: '手机号',
                    halign: 'center',
                    align: 'center'
                }, {
                    field: 'gender',
                    title: '性别',
                    halign: 'center',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if(value == 1){
                            return '男';
                        }else{
                            return '女';
                        }
                        return row.organizationId.name;
                    }
                }, {
                    field: 'type',
                    title: '用户类别',
                    halign: 'center',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if(value == 1){
                            return '后台用户';
                        }else if(value == 2){
                            return '前端用户';
                        }else{
                            return '两者都是';
                        }
                    }
                }, {
                    title: '操作',
                    halign: 'center',
                    align: 'center',
                    formatter: function (value, row, index) {
                        var isValid = '';
                        var isPassword = '';
                        if (grade == '云下健管') {
                            isPassword = '<a href="javascript:void(0)" title="重置密码" onclick="rePassword(' + row.id + ')">重置密码</a>'
                        }
                        if (row.id != 1) {
                            if (row.valid == 1) {
                                isValid = '<a href="javascript:void(0)" title="禁用" onclick="isValid(this,0)">禁用</a>'
                            } else {
                                isValid = '<a href="javascript:void(0)" title="启用" onclick="isValid(this,1)">启用</a>'
                            }
                            var contractInfo = '<a href="javascript:void(0)" class="update" title="修改" onclick="openEidt(this)">修改</a>' + isValid + isPassword;
                            return contractInfo;
                        }
                        return isValid;
                    }
                }
                ]
            }
        );
    }
    ;
}
;

function getSearchParams() {
    var params = {
        userName: $("#name").val(),
        userPhone: $("#cellPhone").val(),
        type:$("#type").val()
    }
    return params;
}

//列表搜索
function searchList() {
    $("#tradeList").bootstrapTable('refresh');
}

function isValid(element, status) {
    var row = $(element).parent().parent().find("td");
    $.fn.getAjaxJSON('post', '/system/userManage/isValid', {
        "userId": row.eq(0).text(),
        "isValid": status
    }, function (result, e) {
        $.fn.messageBox('success', '更新成功！',function () {
            searchList()
        });
    });
}

function openEidt(element) {
    var userId = '';
    if (element != null) {
        userId = $(element).parent().parent().find("td").eq(0).text()
    }
    $.fn.showWindow({title: '用户信息'}, '/system/userManage/eidt?id=' + userId, function (model) {
        $("#tradeList").attr("modelValue", model.attr("id"));
    });
}

function rePassword(id) {
    $.fn.messageBox('warning', '是否重置密码', function () {
        $.fn.getAjaxJSON('post', '/system/userManage/modifyPassword', {
            "userId": id,
            "isReset": 1,
        }, function (result, e) {
            $.fn.messageBox('success', '重置成功！',function () {
                searchList();
            });
        });
    });
}

