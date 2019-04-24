package com.hmx.management.controller.user;


import com.hmx.user.dao.RoleMapper;
import com.hmx.user.entity.po.Role;
import com.hmx.user.service.RoleService;
import com.hmx.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping(value = "/system/roleManage")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleDao;


    @RequestMapping(value = "/init")
    public ModelAndView init(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/system/role/list");
        return mv;
    }


    @RequestMapping(value = "/detail")
    public ModelAndView detail(Integer roleId){
        ModelAndView mv = new ModelAndView();
        mv.addObject("roleId",roleId);
        mv.setViewName("/system/role/detail");
        return mv;
    }


    @RequestMapping(value = "/getPermissionLists")
    @ResponseBody
    public Result<Object> getLists() {
        Result<Object> result = new Result<Object>();
        result.setStatus(10000);
        result.setData(roleService.findByAll());
        return result;
    }


    @RequestMapping(value = "/getPermissionCheck")
    @ResponseBody
    public Result<Object> getPermissionCheck(Integer roleId) {
        Result<Object> result = new Result<Object>();
        result.setStatus(10000);
        result.setData(roleService.getPermissionCheck(roleId));
        return result;
    }


    @RequestMapping(value = "/updateRolePermission")
    @ResponseBody
    public Result<Object> updateRolePermission(Integer perId,Integer roleId,Boolean check) {
        Result<Object> result = new Result<Object>();
        result.setStatus(10000);
        result.setData(roleService.updateRolePermission(perId,roleId,check));
        return result;
    }


    @RequestMapping(value = "/getRoleLists")
    @ResponseBody
    public Map<String, Object> getRoleLists(Pageable pageable) {
        Map<String, Object> result = new HashMap<>();
        List<Role> modelPage = roleDao.findAll();
        result.put("rows", modelPage);
        result.put("total", modelPage.size());
        return result;
    }


    @RequestMapping(value = "/updateRole")
    @ResponseBody
    public Result<Object> updateRole(Role roleModel) {
        Result<Object> result = new Result<Object>();
        Integer status = roleService.updateRole(roleModel);
        result.setStatus(status == 1 ? 10000 : 20000);
        result.setMsg(status == 1 ? "操作成功" : "操作失败");
        return result;
    }

}
