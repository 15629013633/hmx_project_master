package com.hmx.user.service;

import com.hmx.user.entity.po.Permission;
import com.hmx.user.entity.po.Role;
import com.hmx.user.entity.po.RolePermission;

import java.util.List;

public interface RoleService {
    //更新角色信息
    Integer updateRole(Role roleModel);

    //获取所有权限页面
    List<Permission> findByAll();

    //获取子权限页面
    List<RolePermission> getPermissionCheck(Integer roleId);

    //更新权限
    Integer updateRolePermission(Integer perId,Integer roleId,Boolean check);
}