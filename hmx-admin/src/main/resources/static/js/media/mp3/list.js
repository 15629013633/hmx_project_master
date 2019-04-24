$(function () {
    initTable();
    validParam();
});

function initTable() {
    var option = {
        url: 'media/vedio/getList',
        queryParams: function (params) {
            var param = getSearchParams();
            param.pageNum = params.pageNumber;
            param.pageSize = params.pageSize;
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
                title: '音频名称',
                halign: 'center',
                align: 'center'
            },{
                field: 'type',
                title: '音频格式',
                halign: 'center',
                align: 'center',
            }, {
                field: 'size',
                title: '音频大小',
                halign: 'center',
                align: 'center'
            }, {
                field: 'categoryContentId',
                title: '对应内容id',
                halign: 'center',
                align: 'center'
            },{
                field: 'createDate',
                title: '创建时间',
                halign: 'center',
                align: 'center',
                formatter: function (value, row, index) {
                    var dateee  = new Date(row.createTime).toJSON();
                    var date = new Date(+new Date(dateee )+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
                    return date;
                }
            },{
                title: '操作',
                halign: 'center',
                align: 'center',
                formatter: function (value, row, index) {
                    var isUpdate = '<a href="javascript:void(0)" class="update"  title="修改" onclick="openAdd(this)">修改</a>';
                    var show = '<a href="javascript:void(0)" class="show"  title="观看" onclick="showVedio(this)">详情</a>';
                    var isDelete = '<a href="javascript:void(0)" class="delete" title="删除" onclick="deleteArticle(' + row.id + ')">删除</a>';
                    return isUpdate + show + isDelete;
                }
            }
        ]
    };
    $('#vedioList').bootstrapTable($.initTableArg(option));
} //表格

function getSearchParams() {
    var params = {
        name: $("#name").val(),
        type: $("#type").val()
    }
    return params;
}

//列表搜索
function searchList() {
    $("#vedioList").bootstrapTable('refresh');
}

//此处做成视频上传
function openAdd(element) {
    var id = '';
    if (element != null) {
        id = $(element).parent().parent().find("td").eq(0).text()
    }
    $.fn.showWindow({title: '相关信息'}, 'media/vedio/show?id=' + id, function (model) {
        $("#tradeList").attr("modelValue", model.attr("id"));
    });
}

//播放视频
function showVedio(element) {
    var id = '';
    if (element != null) {
        id = $(element).parent().parent().find("td").eq(0).text()
    }
    $.fn.showWindow({title: '视频展示'}, 'media/mp3/show?id=' + id, function (model) {
        $("#vedioList").attr("modelValue", model.attr("id"));
    });
}

function deleteArticle(id) {
    $.fn.getAjaxJSON('post', '/category/deleteArticle?id='+id, null, function (result, e) {
        if(result.status == 10000){
            $.fn.messageBox('success', '删除成功！',function () {
                searchList()
            });
        }else {
            $.fn.messageBox('error', '删除失败！',function () {
            });
        }
    });
}

function getParameter() {
    var formFile = new FormData();
    var fileObj = document.getElementById("img").files[0]; // js 获取文件对象
    if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
        var imgUrl = $("#imgUrl").attr("name");
        if(imgUrl != null && imgUrl != "" && imgUrl != "undefined"){
            formFile.append("img", imgUrl);
        }else {
            alert("请选择图片");
            return;
        }
    }

    var id = $("#category").val();
    if(id != null && id != "" && id != "undefined"){
        formFile.append("id", id);
    }
    formFile.append("title", $("#name").val());
    formFile.append("type", $("#type01").val());
    formFile.append("description", $("#description").val());
    formFile.append("grade", $("#grade").val());
    formFile.append("content",editorValue.getValue());
    formFile.append("file", fileObj); //加入文件对象
    return formFile;
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
            name: {
                validators: {
                    notEmpty: {
                        message: '标题不能为空'
                    }
                }
            },
            type: {
                validators: {
                    notEmpty: {
                        message: '类型不能为空'
                    },
                    stringLength:{
                        min:1,
                        max: 100,
                        message: '类型不能为空'
                    }
                }
            },
            description: {
                validators: {
                    notEmpty: {
                        message: '描述不能为空'
                    }
                }
            },
            content: {
                validators: {
                    notEmpty: {
                        message: '内容不能为空'
                    }
                }
            }
        }
    });
}














