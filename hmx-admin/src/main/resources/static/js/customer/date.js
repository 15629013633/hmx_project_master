/**
 * Created by shayanmei123 on 2018/8/1.
 */
(function(){
    var url = sessionStorage['dateUrl'],
        persionId = sessionStorage['persionId'];

    var initDate = function(){
        $(".form_datetime").datetimepicker({
            language: 'zh-CN',//显示中文
            format: 'yyyy',//显示格式
            startView: 'decade',
            minView: "decade",//设置只显示到月份
            autoclose: true,//选中自动关闭
            todayBtn: true,//显示今日按钮
        });
    }

    var initData = function(year){
        var year = year ? year : new Date().getFullYear(),
            o = {},
            data = {
                id: persionId,
                year:year
            }
        $('.dateList').getAjaxJSON ('get', url, data, function (rel, _this) {
            _this.find('.col-sm-4').remove();
            if(rel.data.length > 0){
                rel.data.forEach(function(ele, i){
                    var d = $.timestampToTime(ele),
                         html = '';
                    o.y ?  "" : o.y = d.y;
                    if(!o[d.m]){
                        html = '<div class="col-sm-4"><div class="panel panel-default">';
                        html += '<div class="panel-heading">'+d.m+'月</div><div class="panel-body"><ul class="list-inline">';
                        o[d.m] = {};
                        html +=  addDay(d, o);
                        html +=  '</ul></div></div></div>';

                        _this.append(html);
                    }else{
                        var $Div  = _this.children('div:last-child').find('.list-inline'),
                             type = addDay(d, o, $Div);
                        type ? $Div.append(type) : "";
                    }
                    // console.log(obj);
                })
                $('.dateList .form_datetime').val(o.y);
            }else{
                $.fn.messageBox('error', '暂无数据', function(){
                    // $('#customer_con').load('/customer/latestReport?mobilePhone=18788500529')
                });
            }
        })
    }

    function addDay(j, o, p){
        var str = '';
        if(!o[j.m][j.d]){
            o[j.m][j.d] = [];
            str += '<li><a href="javascript:void(0);">'+j.d+'日</a><ul class="list-styled">';
            str += addHMS(j, o);
            str += '</ul></li>';
            return str;
        }else{
            str += addHMS(j, o);
            p.find('.list-styled').append(str);
            return false;
        }
    }

    function addHMS(j, o){
        var date = j.y + '-' + j.m + '-' + j.d + ' ' + j.h;
            str = '<li><a href="javascript:void(0);" date="'+ date +'">'+j.h+'</a></li>';
        o[j.m][j.d].push(j.h);
        return str;
    }

    //加载事件
    var loadEvent = function () {
        $('.dateList').on('mouseenter', '.list-inline>li', function(){
            $(this).addClass('active');
        }).on('mouseleave','.list-inline>li',function(){
            $(this).removeClass('active');
        })

        $('.dateList').on('click', '.list-styled a', function(){

            var param = {
                id : sessionStorage['persionId'],
                date : $(this).attr('date'),
                isReport:true
            };
            $('#customer_con').load('/customer/initDetail',param);
        })

        $('.dateList').on('change', '.form_datetime', function(e){
            initData($(this).val());
        });
    }

    $(document).ready(function(){
        initDate();
        initData();
        loadEvent();
    })

}())