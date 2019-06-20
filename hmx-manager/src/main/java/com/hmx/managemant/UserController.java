package com.hmx.managemant;

import com.hmx.user.dto.HmxUserDto;
import com.hmx.user.entity.HmxUser;
import com.hmx.user.service.HmxUserService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 获取所有评论列表
     * @param userDto
     * @return
     */
    @GetMapping("/AllList")
    public ResultBean list(HmxUserDto userDto, PageBean<HmxUser> page, Model model){
        page = hmxUserService.getPage(page, userDto);
        List<HmxUser> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询消息列表成功");
    }
}
