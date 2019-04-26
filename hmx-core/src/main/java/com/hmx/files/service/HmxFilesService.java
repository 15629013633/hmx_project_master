package com.hmx.files.service;

import com.hmx.files.dto.HmxFilesDto;
import com.hmx.files.entity.HmxFiles;
import com.hmx.utils.result.PageBean;

import java.util.List;

/**
 * Created by Administrator on 2019/4/26.
 */
public interface HmxFilesService {
    /**
     * @Method: insert
     * @Description: 添加
     * @param hmxFiles 要添加的对象
     * @return
     */
    Boolean insert ( HmxFiles hmxFiles);

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
     * @param hmxFiles 要修改的对象
     * @return true 修改成功  false 修改失败
     */
    Boolean update ( HmxFiles hmxFiles );

    /**
     * @Method: info
     * @Description: 根据自增主键查询对象信息
     * @param hmxFilesId 根据自增对象查询信息
     * @return HmxFiles 查询的对象
     */
    HmxFiles info (Integer hmxFilesId);

    /**
     * @Method: getPage
     * @Description: 分页查询
     * @param page 分页参数
     * @param hmxFilesDto 查询条件
     * @return PageBean<HmxFiles> 查询到的分页值
     */
    PageBean<HmxFiles> getPage(PageBean<HmxFiles> page, HmxFilesDto hmxFilesDto);

    /**
     * @Method: list
     * @Description: 查询某个条件下的所有数据
     * @param HmxFilesDto 查询参数
     * @return List<HmxFiles> 符合条件的list集合
     */
    List<HmxFiles> list(HmxFilesDto HmxFilesDto );

}
