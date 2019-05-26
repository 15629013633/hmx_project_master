package com.hmx.system.entity;

import com.hmx.system.dto.CommentDto;

/**
 * Created by songjinbao on 2019/5/26.
 */
public class CommentModel extends CommentDto{
    private String userAliase;  //用户昵称
    private String userPhone;   //用户手机号
    private String headPic;   //用户头像

    public String getUserAliase() {
        return userAliase;
    }

    public void setUserAliase(String userAliase) {
        this.userAliase = userAliase;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
}
