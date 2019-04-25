package com.hmx.managemant;

import com.hmx.user.entity.HmxUser;
import com.hmx.user.service.HmxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by songjinbao on 2019/4/25.
 */
@RestController
@RequestMapping(value = "/management/userManage")
public class UserController {
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
