package com.hmx.user.dao;

import com.hmx.user.entity.po.Permission;
import com.hmx.user.entity.po.Role;
import com.hmx.user.entity.po.RoleExample;
import java.util.List;

import com.hmx.user.entity.po.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    @Select("select * from hmx_role")
    List<Role> findAll();

    @Select("select srp.* from role_permission srp join hmx_permission sp on srp.permission_id = sp.id where sp.pid <> 0 and srp.role_id =#{roleId} ")
    List<RolePermission> findPermissionAndRol(@Param("roleId") Integer roleId);
}