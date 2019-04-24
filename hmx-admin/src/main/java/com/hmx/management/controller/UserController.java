package com.hmx.management.controller;

import com.hmx.user.entity.HmxUser;
import com.hmx.user.service.HmxUserService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ResponseBody
public class UserController
{
	@Autowired
	private HttpServletRequest request;
    @Autowired
    private HmxUserService hmxUserService;

    @GetMapping("/getUser")
    public String getUser(@RequestParam("id") Integer id) {
        HmxUser user=hmxUserService.info(id);
        System.out.println(user);
        return "hello";
    }


}
