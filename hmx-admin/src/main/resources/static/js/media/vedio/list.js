$(function () {
    initTable();
    validParam();
});

//预览视频
function showVideo(fileDom) {
    var objUrl = getObjectURL(fileDom.files[0]) ;
    if (objUrl) {
        $("#video0").attr("src", objUrl) ;
    }
}
//预览视频中获取url
function getObjectURL(file) {
    var url = null ;
    if (window.createObjectURL!=undefined) { // basic
        url = window.createObjectURL(file) ;
    } else if (window.URL!=undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file) ;
    } else if (window.webkitURL!=undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file) ;
    }
    return url ;
}
//关闭模态框时清空预览
function cleaarPath(){
    $("#video0").attr("src", "");
    document.getElementById('img').value='';
}
//上传视频
function uploadVideo(){
    var para = getUploadParameter();
    if (para != null) {


        //方法为上传的进度条
        // function onprogress(evt){
        //     var loaded = evt.loaded;     //已经上传大小情况
        //     var tot = evt.total;      //附件总大小
        //     var per = Math.floor(100*loaded/tot);  //已经上传的百分比
        //     if(per>2){
        //         $('.progress').show();
        //     }
        //     $("#progress").css("width" , per +"%").find("span").html( per +"%");
        // }
    }
}
//初始化列表
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
                title: '序列',
                halign: 'center',
                align: 'center',
                formatter: function (value,row,index) {
                    return index+1;
                }
            }, {
                field: 'movieId',
                title: '编号',
                halign: 'center',
                align: 'center',
                visible: false
            }, {
                field: 'movieName',
                title: '视频名称',
                halign: 'center',
                align: 'center'
            },{
                field: 'ratio',
                title: '视频清晰度',
                halign: 'center',
                align: 'center',
                formatter: function (value, row, index) {
                    if(row.ratio == 1){
                        return "标清";
                    }else if(row.ratio == 2){
                        return "高清";
                    }else if(row.ratio == 3){
                        return "超清";
                    }
                }
            }, {
                field: 'duration',
                title: '视频时长',
                halign: 'center',
                align: 'center'
            }, {
                field: 'videoStatus',
                title: '视频处理状态',
                halign: 'center',
                align: 'center',
                formatter: function (value, row, index) {
                    if(row.videoStatus == 1){
                        return "处理成功";
                    }else if(row.videoStatus == 2){
                        return "处理中";
                    }else if(row.videoStatus == 3){
                        return "失败";
                    }
                }
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
                    var isUpdate = '<a href="javascript:void(0)" class="update"  title="修改" onclick="openAdd(this)">修改</a>';
                    var show = '<a href="javascript:void(0)" class="show"  title="观看" onclick="showVedio(' + row.movieId + ')">观看</a>';
                    var isDelete = '<a href="javascript:void(0)" class="delete" title="删除" onclick="deleteVideo(' + row.movieId + ')">删除</a>';
                    if(row.videoStatus == 2 ||  row.videoStatus==3){
                        return isDelete;
                    }else {
                        return show + isDelete;
                    }

                }
            }
        ]
    };
    $('#vedioList').bootstrapTable($.initTableArg(option));
}
//获取查询条件
function getSearchParams() {
    var params = {
        movieName: $("#title1").val(),
        contentId: $("#contentType1").val()
    }
    return params;
}

//列表搜索
function searchList() {
    $('#vedioList').bootstrapTable('destroy');
    initTable();
}

//播放视频
function showVedio(movieId) {
    $.fn.showWindow({title: '视频展示'}, 'media/vedio/show?movieId=' + movieId, function (model) {
        $("#vedioList").attr("modelValue", model.attr("id"));
    });
}

function deleteVideo(movieId) {
    $.fn.getAjaxJSON('post', '/media/delete/video?movieId='+movieId, null, function (result, e) {
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

function getUploadParameter() {
    var formFile = new FormData();
    var fileObj = document.getElementById("img").files[0]; // js 获取文件对象
    if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
        var check = document.getElementById('img').value;
        if(check == null && check == "" && check == "undefined"){
            alert("请选择视频");
            return;
        }
    }
    var binaryFile;
    var reader = new FileReader();

    reader.readAsDataURL(fileObj);
    // reader.readAsArrayBuffer(fileObj);
    reader.onload = function(evt){
        // binaryFile = file;
        // console.log(binaryFile);
        var base64 = evt.target.result + "";
        console.log(base64);
        var base64s = base64.split(",");
        console.log(base64s[0]);
        // var blob = new Blob([reader.result]);
        var house = parseInt((document.getElementById("video0").duration)/3600);
        var min = parseInt((document.getElementById("video0").duration%3600)/60);
        var secd = Math.ceil(document.getElementById("video0").duration%60);
        var time = house + "时" + min + "分" + secd +"秒";
        formFile.append("title", $("#title").val());
        formFile.append("duration", time);
        formFile.append("file", base64s[1]); //加入文件对象
        formFile.append("contentType", $("#contentType").val());
        formFile.append("ratio", $("#ratio").val());
        $.ajax({
            url: "/media/upload/video",
            data: formFile,
            type: "Post",
            dataType: "json",
            cache: false,//上传文件无需缓存
            processData: false,//用于对data参数进行序列化处理 这里必须false
            contentType: false, //必须
            /*xhr:function(){//暂时先不用，进度条
                var xhr = $.ajaxSettings.xhr();
                if(onprogress && xhr.upload) {
                    xhr.upload.addEventListener("progress" , onprogress, false);
                    return xhr;
                }
            },*/
            success: function (result) {
                if (result.status == 10000) {
                    $.fn.messageBox('success', '视频正在处理，请稍后在列表中刷新查看处理状态！', function () {
                        $("#addModal").modal('hide');  //手动关闭
                        searchList()
                    });
                } else {
                    $.fn.messageBox('error', '操作失败！', function () {
                    });
                }
            },
        });
    };
}

//条件检查
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














