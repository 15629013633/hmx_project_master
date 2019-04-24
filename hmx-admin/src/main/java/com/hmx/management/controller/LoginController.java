package com.hmx.management.controller;

import com.hmx.utils.result.Result;
import com.hmx.data.LoginButtonData;

import com.hmx.user.entity.HmxUser;
import com.hmx.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
public class LoginController {


    @Autowired
    private LoginService loginService;


    @RequestMapping("/")
    public ModelAndView index(HttpServletRequest request){
        HmxUser userModel = (HmxUser) request.getSession().getAttribute("userInfo");
        ModelAndView mv = new ModelAndView();
        mv.addObject("userLogin",userModel);
        mv.setViewName("/index");
        return mv;
    }


    @RequestMapping("/home")
    public ModelAndView home(){

        ModelAndView mv = new ModelAndView();
        //查询home页统计数据
        Map<String, Integer> statisticsMap = loginService.loadHomeStatistics();
        mv.addObject("movieCount",statisticsMap.get("movieCount"));
        mv.addObject("userCount",statisticsMap.get("userCount"));
        mv.setViewName("/home");
        return mv;
    }



    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(defaultValue="false")Boolean error){
        ModelAndView mv = new ModelAndView();
        if(error){
            mv.addObject("isError","账户或密码不正确，请重新输入！");
        }else {
            mv.addObject("isError","");
        }
        mv.setViewName("/home/login");
        return mv;
    }



    @RequestMapping("/loginButton")
    @ResponseBody
    public Result<List<LoginButtonData>> loginButton(HttpServletRequest request){
        Result<List<LoginButtonData>> result = new Result<List<LoginButtonData>>();
        HmxUser hmxUser = (HmxUser) request.getSession().getAttribute("userInfo");
        result.setStatus(10000);
        result.setData(loginService.loginButton(hmxUser.getId()));
        return result;
    }


    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
