package com.hmx.hotWords.srvice;

import com.hmx.hotWords.dto.HotWordsDto;
import com.hmx.hotWords.entity.HotWords;
import com.hmx.utils.result.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/2.
 */
public interface HotWordsService {
    /**
     * @Method: insert
     * @Description: 添加
     * @param hotWords 要添加的对象
     * @return
     */
    Boolean insert ( HotWords hotWords);

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
     * @param hotWords 要修改的对象
     * @return true 修改成功  false 修改失败
     */
    Boolean update ( HotWords hotWords );

    /**
     * @Method: info
     * @Description: 根据自增主键查询对象信息
     * @param hotWordsId 根据自增对象查询信息
     * @return HmxFiles 查询的对象
     */
    HotWords info (Integer hotWordsId);

    /**
     * @Method: getPage
     * @Description: 分页查询
     * @param page 分页参数
     * @param hotWordsDto 查询条件
     * @return PageBean<HmxFiles> 查询到的分页值
     */
    PageBean<HotWords> getPage(PageBean<HotWords> page, HotWordsDto hotWordsDto);

    /**
     * @Method: list
     * @Description: 查询某个条件下的所有数据
     * @param hotWordsDto 查询参数
     * @return List<HmxFiles> 符合条件的list集合
     */
    List<HotWords> list(HotWordsDto hotWordsDto );

    /**
     * 热词添加
     * @param hotWordsDto
     * @return
     */
    Map<String,Object> addHotWord(HotWordsDto hotWordsDto);

    /**
     * 修改热词
     * @param hotWordsDto
     * @return
     */
    Map<String,Object> editHotWord(HotWordsDto hotWordsDto);

    /**
     * 查询热词列表
     * @param page
     * @param hotWordsDto
     * @return
     */
    PageBean<HotWords> list(PageBean<HotWords> page, HotWordsDto hotWordsDto);
}
