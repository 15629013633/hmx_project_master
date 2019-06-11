package com.hmx.utils;

/**
 * Created by Administrator on 2019/6/10.
 */
public class Test {
    public static void main(String[] arg0){
//        JiguangPush push = new JiguangPush();
//        push.jiguangPush();
        JpushClientUtil clientUtil = new JpushClientUtil();
        //String content = "{\"contentImage\":\"https://outin-3df62ba9542f11e9b54700163e1c94a4.oss-cn-shanghai.aliyuncs.com/image/default/2B13DE02F3044BA193826EBE79C16A8C-6-2.jpg\",\"contentId\":118,\"contentTpye\":1,\"title\":\"PDF测试PDF测试PDF测试PDF测试PDF测试\",\"contentDes\":\"试PDF测试PDF测试PDF测试\"}";
//        String content = "{\"contentImage\":\"https://outin-3df62ba9542f11e9b54700163e1c94a4.oss-cn-shanghai.aliyuncs.com/image/default/2B13DE02F3044BA193826EBE79C16A8C-6-2.jpg\",\"contentId\":24,\"contentTpye\":3,\"title\":\"女驸马电影\",\"contentDes\":\"女驸马副标题\"}";
        String content = "{\"contentImage\":\"https://outin-3df62ba9542f11e9b54700163e1c94a4.oss-cn-shanghai.aliyuncs.com/image/default/2B13DE02F3044BA193826EBE79C16A8C-6-2.jpg\",\"contentId\":129,\"contentTpye\":2,\"title\":\"6-11音乐资料测试\",\"contentDes\":\"6-11音乐资料测试副标题\"}";

        //clientUtil.sendToAllAndroid("1黄梅戏消息推送通知", "1自定义标题", "1自定义内容", "https://www.baidu.com");
        clientUtil.sendToAll("音乐资料通知", "音乐资料标题", content, "https://www.baidu.com");

        //sendToAllIos("黄梅戏消息推送通知", "自定义标题", "自定义内容", "https://www.baidu.com");
//        JpushClientUtil clientUtil = new JpushClientUtil();

//        String str = sysSendMessage.getMessage();
//        Jdpush.testSendPush(appKey,masterSecret);
//        JpushClientUtil.sendToAllAndroid("群发消息", "尊敬的用户", str, "goodbye!");
//        if (!beanValidator(model, sysSendMessage)){
//            return form(sysSendMessage, model);
//        }
//        sysSendMessageService.save(sysSendMessage);
//        addMessage(redirectAttributes, "发送消息成功");
//        return "redirect:"+Global.getAdminPath()+"/sys/sysSendMessage/?repage";
    }
}
