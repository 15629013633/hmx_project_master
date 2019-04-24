$(function () {
    initTable();
    // initPicTable();
    validParam();
});

function initTable() {
    var option = {
        url: '/categoryContent/categoryContentTable',
        queryParams: function (params) {
            var param = getSearchParams();
            param.pageNum = params.pageNumber;
            param.pageSize = params.pageSize;
            return param;
        },//传递参数
        columns: [
            {
                field: 'categoryContentId',
                title: '编号',
                halign: 'center',
                align: 'center'
            }, {
                field: 'categoryTitle',
                title: '标题',
                halign: 'center',
                align: 'center'
            },{
                field: 'contentType',
                title: '内容类型',
                halign: 'center',
                align: 'center'
            }, {
                field: 'browseNum',
                title: '浏览量',
                halign: 'center',
                align: 'center'
            }, {
                field: 'createid',
                title: '创建人',
                halign: 'center',
                align: 'center'
            },{
                field: 'createTime',
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
                    var isUpdate = '<a href="javascript:void(0)" class="update"  title="修改" onclick="openEidt(this)">修改</a>';
                    var isDelete = '<a href="javascript:void(0)" class="delete" title="删除" onclick="deleteArticle(' + row.id + ')">删除</a>';
                    return isUpdate + isDelete;
                }
            }
        ]
    };
    $('#contentList').bootstrapTable($.initTableArg(option));
} //表格

// function initPicTable() {
//     var option = {
//         url: 'media/vedio/getList',
//         queryParams: function (params) {
//             var param = getSearchParams();
//             param.pageNum = params.pageNumber;
//             param.pageSize = params.pageSize;
//             return param;
//         },//传递参数
//         columns: [
//             {
//                 title: '序列',
//                 halign: 'center',
//                 align: 'center',
//                 formatter: function (value,row,index) {
//                     return index+1;
//                 }
//             }, {
//                 field: 'movieId',
//                 title: '编号',
//                 halign: 'center',
//                 align: 'center',
//                 visible: false
//             }, {
//                 field: 'movieName',
//                 title: '视频名称',
//                 halign: 'center',
//                 align: 'center'
//             },{
//                 field: 'ratio',
//                 title: '视频清晰度',
//                 halign: 'center',
//                 align: 'center',
//                 formatter: function (value, row, index) {
//                     if(row.ratio == 1){
//                         return "标清";
//                     }else if(row.ratio == 2){
//                         return "高清";
//                     }else if(row.ratio == 3){
//                         return "超清";
//                     }
//                 }
//             }, {
//                 field: 'duration',
//                 title: '视频时长',
//                 halign: 'center',
//                 align: 'center'
//             }, {
//                 field: 'videoStatus',
//                 title: '视频处理状态',
//                 halign: 'center',
//                 align: 'center',
//                 formatter: function (value, row, index) {
//                     if(row.videoStatus == 1){
//                         return "处理成功";
//                     }else if(row.videoStatus == 2){
//                         return "处理中";
//                     }else if(row.videoStatus == 3){
//                         return "失败";
//                     }
//                 }
//             },{
//                 field: 'createTime',
//                 title: '创建时间',
//                 halign: 'center',
//                 align: 'center',
//                 formatter: function (value, row, index) {
//                     var dateee  = new Date(row.createTime).toJSON();
//                     var date = new Date(+new Date(dateee )+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
//                     return date;
//                 }
//             },{
//                 title: '操作',
//                 halign: 'center',
//                 align: 'center',
//                 formatter: function (value, row, index) {
//                     var isUpdate = '<a href="javascript:void(0)" class="update"  title="修改" onclick="openAdd(this)">修改</a>';
//                     var show = '<a href="javascript:void(0)" class="show"  title="观看" onclick="showVedio(' + row.movieId + ')">观看</a>';
//                     var isDelete = '<a href="javascript:void(0)" class="delete" title="删除" onclick="deleteVideo(' + row.movieId + ')">删除</a>';
//                     if(row.videoStatus == 2 ||  row.videoStatus==3){
//                         return isDelete;
//                     }else {
//                         return show + isDelete;
//                     }
//
//                 }
//             }
//         ]
//     };
//     $('#picList').bootstrapTable($.initTableArg(option));
// } //表格

function getSearchParams() {
    var params = {
        categoryTitle: $("#categoryTitle").val(),
        contentType: $("#contentType").val(),
        browseNum: $("#browseNum").val()
    }
    return params;
}

//列表搜索
function searchList() {
    $("#contentList").bootstrapTable('refresh');
}

function openEidt(element) {
    var id = '';
    if (element != null) {
        id = $(element).parent().parent().find("td").eq(0).text()
    }
    $.fn.showWindow({title: '文章信息'}, '/categoryContent/editInit?id=' + id, function (model) {
        $("#contentList").attr("modelValue", model.attr("id"));
    });
}


function updateArticle() {
    validParam();
    $('#formUpdate').bootstrapValidator('validate');

    var  data = getParameter();
    if(data != null){
        if ($("#formUpdate").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
            $.ajax({
                url: "/categoryContent/edit",
                data: data,
                type: "Post",
                dataType: "json",
                cache: false,//上传文件无需缓存
                processData: false,//用于对data参数进行序列化处理 这里必须false
                contentType: false, //必须
                success: function (result) {
                    if(result.status == 10000){
                        $.fn.messageBox('success', '操作成功！',function () {
                            $("#myModal").remove();
                            $(".modal-backdrop").remove();
                            searchList()
                        });
                    }else {
                        $.fn.messageBox('error', '更新失败！',function () {
                        });
                    }
                },
            })
        }
    }
}

function deleteArticle(id) {
    $.fn.getAjaxJSON('post', '/base/article/deleteArticle?id='+id, null, function (result, e) {
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

function selectPic(){
    var id = '';
    if (element != null) {
        id = $(element).parent().parent().find("td").eq(0).text()
    }
    $.fn.showWindow({title: '文章信息'}, '/categoryContent/selectPic', function (model) {
        $("#contentList").attr("modelValue", model.attr("id"));
    });
}

function getParameter() {
    var formFile = new FormData();
    // var fileObj = document.getElementById("img").files[0]; // js 获取文件对象
    // if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
    //     var imgUrl = $("#imgUrl").attr("name");
    //     if(imgUrl != null && imgUrl != "" && imgUrl != "undefined"){
    //         formFile.append("img", imgUrl);
    //     }else {
    //         alert("请选择图片");
    //         return;
    //     }
    // }

    var id = $("#categoryContentId").val();
    if(id != null && id != "" && id != "undefined"){
        formFile.append("categoryContentId", id);
    }
    formFile.append("categoryTitle", $("#title").val());
    formFile.append("categoryId", $("#type").val());
    formFile.append("categoryContent", editorValue.getValue());

    return formFile;

    // var params = {
    //     categoryContentId: $("#categoryContentId").val(),
    //     categoryTitle: $("#categoryTitle").val(),
    //     contentType: $("#contentType").val(),
    //     categoryContent: $("#content").val()
    // };
    // console.log(params);
    // return params;
}

function imgPreview(fileDom){
    //判断是否支持FileReader
    if (window.FileReader) {
        var reader = new FileReader();
    } else {
        alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
    }

    //获取文件
    var file = fileDom.files[0];
    var imageType = /^image\//;
    //是否是图片
    /*if (!imageType.test(file.type)) {
        alert("请选择图片！");
        return;
    }*/
    //读取完成
    reader.onload = function(e) {
        //获取图片dom
        var img = document.getElementById("preview");
        //图片路径设置为读取的图片
        img.src = e.target.result;
    };
    reader.readAsDataURL(file);
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














