package com.hmx.system.service;

import com.hmx.system.dto.HmxGovDto;
import com.hmx.system.entity.HmxGov;
import com.hmx.utils.result.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/10.
 */
public interface HmxGovService {
    /**
     * @Method: insert
     * @Description: 添加
     * @param comment 要添加的对象
     * @return
     */
    Boolean insert ( HmxGov comment);

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
     * @param comment 要修改的对象
     * @return true 修改成功  false 修改失败
     */
    Boolean update ( HmxGov comment );

    /**
     * @Method: info
     * @Description: 根据自增主键查询对象信息
     * @param commentId 根据自增对象查询信息
     * @return HmxFiles 查询的对象
     */
    HmxGov info (Integer commentId);

    /**
     * @Method: getPage
     * @Description: 分页查询
     * @param page 分页参数
     * @param hmxGovDto 查询条件
     * @return PageBean<HmxFiles> 查询到的分页值
     */
    PageBean<HmxGov> getPage(PageBean<HmxGov> page, HmxGovDto hmxGovDto);

    /**
     * @Method: list
     * @Description: 查询某个条件下的所有数据
     * @param hmxGovDto 查询参数
     * @return List<HmxFiles> 符合条件的list集合
     */
    List<HmxGov> list(HmxGovDto hmxGovDto );

    /**
     * 热词添加
     * @param hmxGovDto
     * @return
     */
    Map<String,Object> addComment(HmxGovDto hmxGovDto);

    /**
     * 修改热词
     * @param hmxGovDto
     * @return
     */
    Map<String,Object> editComment(HmxGovDto hmxGovDto);

    /**
     * 更新评论的状态以控制显示或者隐藏
     * @param ids
     * @return
     */
    boolean updateStatus(String ids,String type);

    boolean examination(String ids);
}
