package com.hmx.base.controller;

import com.hmx.user.entity.HmxUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Created by Administrator on 2019/7/7.
 */
public class BaseController {
    /**
     * 当前账号常量
     */
    private static final String ACCOUNT = "account";


    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public HmxUser getAccount() {
        HttpSession session = getRequest().getSession();
        return (HmxUser) session.getAttribute(ACCOUNT);
    }

    public void setAccount(HmxUser account) {
        HttpSession session = getRequest().getSession();
        if (account != null) {
            session.setAttribute(ACCOUNT, account);
            //session过期时间设置，以秒为单位，即在没有活动30分钟后，session将失效
            session.setMaxInactiveInterval(30 * 60);
        }
    }
}
