$(function () {
    treeFunction();
});
var isUpdate = true;
var roleId = $("#roleIdNow").val();


//树状
function treeFunction() {
    $.fn.getAjaxJSON('post', '/system/roleManage/getPermissionLists', {}, function (result, e) {
        zTreeObj = $.fn.zTree.init($("#roelTree"), setting, result.data);
        var nodes = zTreeObj.getNodes();
        $.fn.getAjaxJSON('post', '/system/roleManage/getPermissionCheck', {'roleId':roleId}, function (result, e) {
            $.each(result.data,function (index,iteam) {
                isUpdate = false;
                var node = zTreeObj.getNodeByParam("id", iteam.permissionId);//根据ID找到该节点
                zTreeObj.checkNode(node, !node.checked, true);
                zTreeObj.updateNode(node);
            });
        });
        zTreeObj.expandAll(true);
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
            enable: true,
            autoCheckTrigger: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "ps" }
        },
        callback: {
           // onClick: zTreeOnClick,
            onCheck: zTreeOnCheck
        }
    },
    data1 = [ //简单 JSON 数据
        {"id": 1, "pId": 0, "name": "父节点1"},
        {"id": 2, "pId": 0, "name": "父节点2"},
        {"id": 3, "pId": 1, "name": "子节点1"},
        {"id": 4, "pId": 1, "name": "子节点2"},
        {"id": 5, "pId": 2, "name": "子节点1"},
        {"id": 6, "pId": 2, "name": "子节点2"}
    ],
    data2 = [ //标准 JSON 数据
        {"id":1,"name":"一元集团","code":"100","detail":"一元集团","valid":true,"children":[{"id":2,"name":"南区","code":"10001","detail":"一元集团","valid":true,"children":[{"id":8,"name":"A002","code":"100A002","detail":"店","valid":true,"children":[]},{"id":9,"name":"A003","code":"100A003","detail":"店","valid":true,"children":[]},{"id":7,"name":"A001","code":"100A001","detail":"店","valid":true,"children":[]}]},{"id":3,"name":"北区","code":"10002","detail":"一元集团","valid":true,"children":[{"id":10,"name":"A004","code":"100A004","detail":"店","valid":true,"children":[]}]}]}
    ];

function zTreeOnClick(event, treeId, treeNode) {
}

function zTreeOnCheck(event, treeId, treeNode) {
    if(isUpdate){
        var param = {"perId":treeNode.id , "roleId":roleId , "check":treeNode.checked}
        $.fn.getAjaxJSON('post', '/system/roleManage/updateRolePermission',param, function (result, e) {
            $('.message-success').click(function(){
                $.fn.messageBox('success', '修改成功！');
            })
        });
    }else {
        isUpdate = true;
    }
}

function onAsyncSuccess(event, treeId, treeNode, msg) {
    //var f = $("#add_fileid_id").val();//所选的ID   例如 "12589"
    var treeObj = $.fn.zTree.getZTreeObj(treeId);//ztree树的ID
    var node = treeObj.getNodeByParam("id", 2);//根据ID找到该节点
    treeObj.selectNode(node);//根据该节点选中
};