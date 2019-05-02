package com.hmx.system.service;

import com.hmx.system.dto.MessageDto;
import com.hmx.system.entity.Message;
import com.hmx.utils.result.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/2.
 */
public interface MessageService {
    /**
     * @Method: insert
     * @Description: 添加
     * @param message 要添加的对象
     * @return
     */
    Boolean insert ( Message message);

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
     * @param message 要修改的对象
     * @return true 修改成功  false 修改失败
     */
    Boolean update ( Message message );

    /**
     * @Method: info
     * @Description: 根据自增主键查询对象信息
     * @param messageID 根据自增对象查询信息
     * @return HmxFiles 查询的对象
     */
    Message info (Integer messageID);

    /**
     * @Method: getPage
     * @Description: 分页查询
     * @param page 分页参数
     * @param messageDto 查询条件
     * @return PageBean<HmxFiles> 查询到的分页值
     */
    PageBean<Message> getPage(PageBean<Message> page, MessageDto messageDto);

    /**
     * @Method: list
     * @Description: 查询某个条件下的所有数据
     * @param messageDto 查询参数
     * @return List<HmxFiles> 符合条件的list集合
     */
    List<Message> list(MessageDto messageDto );

    /**
     * 热词添加
     * @param messageDto
     * @return
     */
    Map<String,Object> addMessage(MessageDto messageDto);

    /**
     * 修改热词
     * @param messageDto
     * @return
     */
    Map<String,Object> editHotWord(MessageDto messageDto);

    /**
     * 查询热词列表
     * @param page
     * @param hotWordsDto
     * @return
     */
    PageBean<Message> list(PageBean<Message> page, MessageDto hotWordsDto,Integer type);
}
