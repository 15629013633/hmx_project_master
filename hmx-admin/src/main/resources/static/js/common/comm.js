/**
 * Created by shayanmei123 on 2018/7/9.
 */
(function($){
    //显示进度
    jQuery.fn.progress = function() {
        $(this).addClass('progress-parent');
        $(this).append('<div class="show-progress text-center"><i class="fa fa-spinner fa-pulse fa-3x"></i></div>');
    };
    //关闭进度
    jQuery.fn.unProgress = function() {
        $('.progress-parent').removeClass('progress-parent');
        $('.show-progress').remove();
    };

    $.customParam = function(a){
        var s = [];

        function add(key, value){
            // If value is a function, invoke it and return its value
            if (value == null || value === "" || value == "undefined" || value == undefined) {
                return;
            }
            value = jQuery.isFunction(value) ? value() : value;
            s[s.length] = encodeURIComponent(key) + "=" + encodeURIComponent(value);
        }

        /* private method*/
        function buildParams(prefix, obj, add) {
            if (jQuery.isArray(obj)) {
                // Serialize array item.
                jQuery.each(obj, function(i, v) {
                    if (/\[\]$/.test(prefix)) {
                        // Treat each array item as a scalar.
                        add(prefix, v);

                    } else {
                        buildParams(prefix + "[" + (typeof v === "object" || jQuery.isArray(v) ? i : "") + "]", v, add);
                    }
                });

            } else if (obj != null && typeof obj === "object") {
                // Serialize object item.
                for (var name in obj) {
                    buildParams(prefix + "." + name, obj[name], add);
                }

            } else {
                // Serialize scalar item.
                add(prefix, obj);
            }
        };

        if (jQuery.isArray(a) || (a.jquery && !jQuery.isPlainObject(a))) {
            // Serialize the form elements
            jQuery.each(a, function() {
                add(this.name, this.value);
            });

        } else {
            for (var prefix in a) {
                buildParams(prefix, a[prefix], add);
            }
        }

        return s.join("&").replace(/%20/g, "+");
    }

    //提交ajax,以json格式提将
    $.fn.getAjaxJSON = function(method, url, data, callback, attr) {
        var $This = $(this);

        if (data == null || data == undefined) {
            data = {};
        }

        var params = {
            type: method,
            url: url,
            data: data,
            dataType: "json", //如果要返回 json 这里很重要哦！！详细说明参见JQuery的API手册吧
            beforeSend: function() {
                $This.progress();
            },
            success: function(result) {
                if(result.status === 10000){
                    if (callback != null) {
                        callback(result, $This);
                    }
                }else if(result.status === 20000){
                    $This.messageBox('error', result.msg);
                }else{
                    $This.messageBox('warning', result.msg);
                }
            },
            complete: function() {
                $This.unProgress();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                $This.messageBox('error','访问 [' + url + "] 失败.");
            }
        }
        if (attr !== null) {
            obj = $.extend(true, params, attr)
        }
        $.ajax(params);
    };

    $.fn.getAjaxHTML = function(url, data, callback) {
        $This = $(this);
        if (data == null || data == undefined) {
            data = {};
        }
        $.ajax({
            // traditional:false,
            type: "get",
            url: url,
            data: data,
            dataType: "html", //如果要返回 json 这里很重要哦！！详细说明参见JQuery的API手册吧
            beforeSend: function() {
                $This.progress();
            },
            success: function(result) {
                if (callback != null) {
                    callback(result, $This);
                }
            },
            complete: function() {
                $This.unProgress();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                $This.messageBox('error','访问 [' + url + "] 失败.");
            }
        });
    };

    /**
     * $('body').popWindow({
          title:'标题',
          width:'模态框的宽度'
        }, url, function(){
     *
     * })
     * data:{}
     * url : 加载页面地址
     */
    $.fn.showWindow = function(data, url, completed) {
        var $This = $('body'),
            w = data.width ? data.width : '60%',
            t = data.title ? data.title : '标题';

        var html = '<div class="modal fade in modal-show"  id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">';
        html += '<div class="modal-dialog modal-max" role="document" style="width:'+ w +'">';
        html += '<div class="modal-content">';
        html += '<div class="modal-header">';
        html += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
        html += '<h4 class="modal-title" id="myModalLabel">' + t + '';
        html += '</h4></div><div class="modal-body"></div></div></div></div><div class="modal-backdrop fade in"></div>';

        $('#myModal').remove();

        $This.append(html);

        var $modal = $('#myModal');

        $modal.find('.close').click(function(e) {
            $('#myModal').remove();
            $('.modal-backdrop').remove();
        });

        $modal.show();
        $modal.find('.modal-body').progress();

        if(url) {
            $modal.find('.modal-body').getAjaxHTML(url, {}, function(result, obj){
                obj.html(result);
                if(completed) {
                    completed($modal);
                }
            })
            // $modal.find('.modal-body').load(url, function(result, status, xhr) {
            //     $(this).html(result);
            //     $(this).unProgress();
            //     if(completed) {
            //         completed($modal);
            //     }
            // });
        }
    };

    /**
     * $('body').popWindow(url, title, function(){
     *
     * })
     * url : 加载页面地址
     *
     * title : 标题
     */
    $.fn.popWindow = function(data, url, callback) {
        var  title =  data.title ? data.title : "",
              url = url ? url : "",
              width = data.width ? data.width : "50%";

        $('#myWindow').remove();
        $('body').append('<div class="container-edit" id="myWindow">'
            +'<div class="panel-heading clearfix"><h3 class="panel-title pull-left">'
            +'</h3><a href="javascript:;" data-opr="close" class="btn btn-danger btn-sm pull-right">关闭</a>'
            +'</div><div data-content="popWindow" class="container-body"></div></div><div class="modal-backdrops fade in"></div>');
        var $popWindow = $('#myWindow');
        var $content=$('[data-content="popWindow"]');

        $('.panel-title').text(title);

        $popWindow.width(width).show();

        $('[data-opr="close"]').click(function() {
                $popWindow.width(0).hide();
                $popWindow.add($('.modal-backdrops')).remove();
        });
        $('.modal-backdrops').click(function() {
                $popWindow.width(0).hide();
                $popWindow.add($('.modal-backdrops')).remove();
        });

        if(url){
            $content.getAjaxHTML(url, {}, function(result, obj){
                obj.html(result);
                if (callback) {
                    callback(obj);
                }
            })
        }
    };

    $.fn.slideLeftHide = function( speed, callback ) {
        this.animate({
            width : "hide",
            paddingLeft : "hide",
            paddingRight : "hide",
            marginLeft : "hide",
            marginRight : "hide"
        }, speed, callback );
    };
    $.fn.slideLeftShow = function( speed, callback ) {
        this.animate({
            width : "show",
            paddingLeft : "show",
            paddingRight : "show",
            marginLeft : "show",
            marginRight : "show"
        }, speed, callback );
    };

    /**
     * $.fn.messageBox(type, message, callback) 调用
     @type : 提示类型  (error、success、warning)
     @message ： 提示的内容信息
     @callback : 回调函数(可选)
     */
    $.fn.messageBox = function(type, message, callback) {
        if (!type) {
            return;
        }

        var type = type ? type : 'success',
            message = message ? message : '提示内容',
            flag = true,
            htmlMsg = '<div class="msgBox" tabindex="-1" role="dialog" id="msgBox">'+
						'<div class="msg-header">'+
							'<button type="button" class="close" aria-label="Close">'+
								'<span aria-hidden="true">&times;</span>'+
							'</button>'+
							'<h4 class="msg-title">'+
								'<i class="glyphicon glyphicon-ok-circle"></i>提示'+
                            '</h4>'+
                        '</div>'+
                        '<div class="msg-body">'+
                            '<p>'+ message +'</p>'+
                        '</div>'+
                        '<div class="msg-footer">'+
                            '<button type="button" class="btn btn-success ok">确定</button>'+
                        '</div>'+
                    '</div><div class="alert-backdrop"></div>';

        $('body').find('#msgBox').remove();
        $('body').append(htmlMsg);

        var $msgBox = $('#msgBox');
        $msgBox.fadeIn('fast', function() {
            $(this).animate({
                'opacity': 1
            }, 300);
        });

        if(type === 'warning'){
            $msgBox.find('.msg-title').html('<i class="fa fa-exclamation warning"></i>提示');
            $msgBox.find('.msg-footer').html('<button type="button" class="btn btn-warning ok">确定</button><button type="button" class="btn btn-default no">取消</button>');
        }else if(type === 'error'){
            $msgBox.find('.msg-title').html('<i class="glyphicon glyphicon-remove-circle error"></i>提示');
            $msgBox.find('.msg-footer').html('<button type="button" class="btn btn-danger ok">确定</button>');
        }

        $msgBox.on('click', '.close, .no', function(){
            flag = false;
            $msgBox.add($('.alert-backdrop')).fadeOut(300).remove();
            if(callback){
                callback(flag);
            }
        })

        $msgBox.on('click', '.ok', function(){
            $msgBox.add($('.alert-backdrop')).fadeOut(300).remove();
            if(callback){
                callback(flag);
            }
        })

        $('body').on('click', '.alert-backdrop', function(){
             $('#msgBox').focus();
        })
    }

    /**
     * 时间戳格式化函数
     * @param  {string} format    格式
     * @param  {int}    timestamp 要格式化的时间 默认为当前时间
     * @return {string}           格式化的时间字符串
     * date('Y-m-d','1350052653');//很方便的将时间戳转换成了2012-10-11
       date('Y-m-d H:i:s','1350052653');//得到的结果是2012-10-12 22:37:33
     */
    $.extend({
        /**
         *  表格参数汇总
         * @param option
         * @returns
         */
        initTableArg: function (option) {
            var options = {
                classes: 'table table-hover table-style',
                url: '',
                queryParamsType: '',
                queryParams: {},
                idField: "id", //指定主键列
                uniqueId: 'id', //对每一行指定唯一标识符
                // method: 'post',//请求方式,默认post
                pagination: true,//设置为 true 会在表格底部显示分页条
                pageNumber: 1,//如果设置了分页，首页页码，默认第一页
                pageSize: 10,//如果设置了分页，页面数据条数
                pageList: [5, 10, 20, 50, 100],//如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。
                striped: true,//设置为 true 会有隔行变色效果
                // height : 300,//定义表格的高度。
                cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性
                sortable: false, //是否启用排序 默认为true
                // sortOrder: "asc", //排序方式，'asc' 或者 'desc' 默认为asc
                // showRefresh: false, //是否显示刷新按钮  默认为false
                minimumCountColumns: 2, //最少允许的列数
                clickToSelect: true,//是否启用点击选中行
                // showFooter: false,//显示底部 默认为false
                // showColumns : false, //是否显示内容列下拉框 默认为false
                cardView: false, //设置为 true将显示card视图，适用于移动设备。否则为table试图，适用于pc端。
                detailView: false,
                detailFormatter: function (index, row) {
                    return '';
                },
                sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
            };

            $.extend(true, options, option);
            return options;
        },
        timestampToTime: function (t) {
            var len = t.length,
                o = {};
            t = len === 10 ? t * 1000 : t; //时间戳为10位需*1000，时间戳为13位的话不需乘1000
            t = len === 13 ? t * 1 : t;
            var date = new Date(t),
                h, m, s;
            o['y'] = date.getFullYear()
            o['m'] = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
            o['d'] = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
            h = date.getHours() < 10 ? '0' + date.getHours() : date.getHours();
            m = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
            s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
            o['h'] = h + ':' + m + ':' + s;
            o['q'] = o.y + '/' + o.m + '/' + o.d;
            return o;
        },

        datestampToDate: function (timestamp) {
            var date = new Date(timestamp);//时间戳为10位需timestamp*1000，时间戳为13位的话不需乘1000
            var Y = date.getFullYear() + '-';
            var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
            var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate() +  ' ';
            var h = date.getHours() + ':';
            var m = date.getMinutes() + ':';
            var s = date.getSeconds();
            return Y + M + D +' '+ h + m + s;
        }
    })

}(jQuery))