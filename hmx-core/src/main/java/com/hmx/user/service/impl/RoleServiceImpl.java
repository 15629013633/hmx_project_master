package com.hmx.user.service.impl;

import com.hmx.user.dao.PermissionMapper;
import com.hmx.user.dao.RoleMapper;
import com.hmx.user.dao.RolePermissionMapper;
import com.hmx.user.entity.po.*;
import com.hmx.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Integer updateRole(Role roleModel) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andNameEqualTo(roleModel.getName());
        List<Role> roles = roleMapper.selectByExample(roleExample);
        if(roles == null || roles.size() == 0){
            roleMapper.insert(roleModel);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Permission> findByAll() {
        return permissionMapper.selectAll();
    }

    @Override
    public List<RolePermission> getPermissionCheck(Integer roleId) {
        List<RolePermission> list = roleMapper.findPermissionAndRol(roleId);
        return list;
    }

    @Override
    public Integer updateRolePermission(Integer perId, Integer roleId, Boolean check) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        rolePermission.setPermissionId(perId);
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andPermissionIdEqualTo(perId).andRoleIdEqualTo(roleId);
        List<RolePermission> list = rolePermissionMapper.selectByExample(rolePermissionExample);
        if(list != null && list.size()>0){
            if(!check){
                rolePermissionMapper.deleteByExample(rolePermissionExample);
            }
        }else{
            rolePermissionMapper.insert(rolePermission);
        }

        return null;
    }
}