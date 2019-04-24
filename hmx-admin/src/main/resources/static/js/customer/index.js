/**
 * Created by shayanmei123 on 2018/8/2.
 */
(function($){
    var loadEvent = function(){
        $('#historyInfo').click(function(){
            sessionStorage['persionId'] = $('#personId').val();
            sessionStorage['dateUrl'] = '/customer/getReportDate';
            $('#customer_con').load('/customer/initDate');
        })

        $('body').on('click', '.breadcrumb>li>a', function(){
            var url = $(this).attr('url');
            $('#customer_con').load(url);
        })

        $('body').on('click', '.exit', function(){
            window.location.href="/customer/initLogin";
        })
    }

    $(document).ready(function(){
        loadEvent();
    })
}(jQuery))