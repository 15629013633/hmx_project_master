package com.hmx.system.service;

import com.hmx.system.dto.SysManageDto;
import com.hmx.system.entity.SysManage;
import com.hmx.utils.result.PageBean;

import java.util.List;

public interface SysManageService {

    /**
     * @Method: insert
     * @Description: 添加
     * @param sysManage 要添加的对象
     * @return
     */
    int insert ( SysManage sysManage);

    /**
     * @Method: deleteByIdArray
     * @Description: 批量删除
     * @param ids 将要删除的对象主键字符串 例如:1,5,10,12
     * @return true 删除成功  false 删除失败
     */
    Boolean deleteByIdArray(String ids);

    /**
     * @Method: update
     * @Description: 修改
     * @param sysManage 要修改的对象
     * @return true 修改成功  false 修改失败
     */
    Boolean update ( SysManage sysManage );

    /**
     * @Method: info
     * @Description: 根据自增主键查询对象信息
     * @param sysManageId 根据自增对象查询信息
     * @return HmxFiles 查询的对象
     */
    SysManage info (Integer sysManageId);

    /**
     * @Method: getPage
     * @Description: 分页查询
     * @param page 分页参数
     * @param tagtabDto 查询条件
     * @return PageBean<HmxFiles> 查询到的分页值
     */
    PageBean<SysManage> getPage(PageBean<SysManage> page, SysManageDto tagtabDto);

    /**
     * @Method: list
     * @Description: 查询某个条件下的所有数据
     * @param sysManageDto 查询参数
     * @return List<HmxFiles> 符合条件的list集合
     */
    List<SysManage> list(SysManageDto sysManageDto );

}
