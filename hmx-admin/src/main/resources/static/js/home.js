/**
 * Created by shi on 2018/10/4.
 */

$(function () {
	getBasicData();
});


function getBasicData() {
	$.fn.getAjaxJSON('get', '/home', null, function (result, e) {
		if (result.status == 10000) {
			$.fn.messageBox('success', '获取成功！', function () {
			});
		} else {
			$.fn.messageBox('error', '获取失败！', function () {
			});
		}
	});
}
