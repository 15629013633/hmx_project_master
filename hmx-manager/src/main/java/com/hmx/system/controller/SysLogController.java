package com.hmx.system.controller;

import com.hmx.log.dto.SysLogDto;
import com.hmx.log.entity.SysLog;
import com.hmx.log.servie.ISysLogService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**日志记录控制层
 * 日志记录
 * Created by Administrator on 2019/7/7.
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;

    /**
     * 分页
     * 获取所有评论列表
     * @param sysLogDto
     * @return
     */
    @GetMapping("/getSysLogList")
    public ResultBean tagListPage(SysLogDto sysLogDto, PageBean<SysLog> page, Model model){
        page = sysLogService.getPage(page, sysLogDto);
        List<SysLog> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询来源列表成功");
    }
}
