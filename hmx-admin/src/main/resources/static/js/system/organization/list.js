$(function () {
    treeFunction();
});

//树状
function treeFunction() {
    $.fn.getAjaxJSON('post', '/system/organization/getLists', {}, function (result, e) {
        zTreeObj = $.fn.zTree.init($("#organizationTree"), setting, result.data);
        var nodes = zTreeObj.getNodes();
        zTreeObj.expandNode(nodes[0], true, true, false);
    });
}

var zTreeObj,
    setting = {
        view: {
            selectedMulti: false //是否允许同时选中多个节点
        },
        data: {
            simpleData: { //简单 JSON 数据配置
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: 0
            }
        },
        check: {
            enable: false,
            autoCheckTrigger: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "s" }
        },
        callback: {
            onClick: zTreeOnClick,
            //onCheck: zTreeOnCheck,
        }
    }

function zTreeOnClick(event, treeId, treeNode) {
    var organizationId = treeNode.id;
    $.fn.popWindow({title:'组织机构详情'}, '/system/organization/detail?organizationId='+organizationId)
}

function zTreeOnCheck(event, treeId, treeNode) {

}
