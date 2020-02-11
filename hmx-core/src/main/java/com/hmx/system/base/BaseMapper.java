package com.hmx.system.base;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {
    /**
     * 按条件查询条数
     * @param paramsMap
     * @return
     */
    Integer count(HashMap<String,Object> paramsMap);

    /**
     * 分页查询
     * @param paramsMap
     * @return
     */
    List<T> selectPage(HashMap<String,Object> paramsMap);

    /**
     * 新增数据
     * @param obj
     */
    void insert(T obj);

    /**
     * 更新数据
     * @param obj
     */
    void update(T obj);

    /**
     * 根据主键删除单个
     * @param id
     */
    void delete(Integer id);

    /**
     * 根据主键删除多个
     * Object 可以是String或者Integer类型
     * @param ids
     */
    void deletes(List<Object> ids);

    /**
     * 根据主键id查询对象
     * @param id
     * @return
     */
    T selectById(Object id);

    /**
     * 根据条件插叙对象
     * @param obj
     * @return
     */
    List<T> selectByParam(Object obj);

    /**
     * 根据主键查找对象
     * @param id
     * @return
     */
    T getObjectById(Integer id);
}
