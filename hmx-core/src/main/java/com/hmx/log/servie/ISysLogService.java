package com.hmx.log.servie;

import com.hmx.log.dto.SysLogDto;
import com.hmx.log.entity.SysLog;
import com.hmx.utils.result.PageBean;

import java.util.List;

/**
 * Created by Administrator on 2019/7/7.
 */
public interface ISysLogService {
    /**
     * @Method: insert
     * @Description: 添加
     * @param sysLog 要添加的对象
     * @return
     */
    Boolean insert ( SysLog sysLog);

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
     * @param sysLog 要修改的对象
     * @return true 修改成功  false 修改失败
     */
    Boolean update ( SysLog sysLog );

    /**
     * @Method: info
     * @Description: 根据自增主键查询对象信息
     * @param scoreId 根据自增对象查询信息
     * @return HmxFiles 查询的对象
     */
    SysLog info (Integer scoreId);

    /**
     * @Method: getPage
     * @Description: 分页查询
     * @param page 分页参数
     * @param sysLogDto 查询条件
     * @return PageBean<HmxFiles> 查询到的分页值
     */
    PageBean<SysLog> getPage(PageBean<SysLog> page, SysLogDto sysLogDto);

    /**
     * @Method: list
     * @Description: 查询某个条件下的所有数据
     * @param sysLogDto 查询参数
     * @return List<HmxFiles> 符合条件的list集合
     */
    List<SysLog> list(SysLogDto sysLogDto  );
}
