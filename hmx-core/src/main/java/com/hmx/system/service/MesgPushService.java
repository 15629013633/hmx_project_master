package com.hmx.system.service;

import com.hmx.system.dto.MesgPushDto;
import com.hmx.system.entity.MesgPush;
import com.hmx.utils.result.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/11.
 */
public interface MesgPushService {
    /**
     * @Method: insert
     * @Description: 添加
     * @param mesgPush 要添加的对象
     * @return
     */
    Boolean insert ( MesgPush mesgPush);

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
     * @param mesgPush 要修改的对象
     * @return true 修改成功  false 修改失败
     */
    Boolean update ( MesgPush mesgPush );

    /**
     * @Method: info
     * @Description: 根据自增主键查询对象信息
     * @param commentId 根据自增对象查询信息
     * @return HmxFiles 查询的对象
     */
    MesgPush info (Integer commentId);

    /**
     * @Method: getPage
     * @Description: 分页查询
     * @param page 分页参数
     * @param mesgPushDto 查询条件
     * @return PageBean<HmxFiles> 查询到的分页值
     */
    PageBean<MesgPush> getPage(PageBean<MesgPush> page, MesgPushDto mesgPushDto);

    /**
     * @Method: list
     * @Description: 查询某个条件下的所有数据
     * @param mesgPushDto 查询参数
     * @return List<HmxFiles> 符合条件的list集合
     */
    List<MesgPush> list(MesgPushDto mesgPushDto );

    /**
     * 热词添加
     * @param mesgPushDto
     * @return
     */
    Map<String,Object> addComment(MesgPushDto mesgPushDto);

    /**
     * 修改热词
     * @param mesgPushDto
     * @return
     */
    Map<String,Object> editComment(MesgPushDto mesgPushDto);

    /**
     * 更新评论的状态以控制显示或者隐藏
     * @param ids
     * @return
     */
    boolean updateStatus(String ids,String type);
}
