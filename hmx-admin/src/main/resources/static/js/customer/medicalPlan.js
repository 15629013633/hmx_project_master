/**
 * @Author: 肖映彤
 * @Description: 查询套餐期限
 * @Date: 10:16 2018-10-16
 */
function queryMedical() {
    $.ajax({
        type: "post",
        url: "/customer/getOverdueInfo",
        data: {"customerPhone":$("#customerPhone").val()},
        dataType: "json",
        success: function(result) {
            alert(result.msg);
        }
    });
}

/**
 * @Author: 肖映彤
 * @Description: 激活套餐
 * @Date: 16:11 2018-10-16
 */
function activateMedical() {
    $.ajax({
        type: "post",
        url: "/customer/activate",
        data: {"customerPhone":$("#customerPhone").val(),"userPhone":$("#userPhone").val()},
        dataType: "json",
        success: function(result) {
            alert(result.msg);
            window.location.reload();
        }
    });
}