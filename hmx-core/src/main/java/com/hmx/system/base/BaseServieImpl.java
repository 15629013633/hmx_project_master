package com.hmx.system.base;

import com.hmx.system.base.BaseServie;
import com.hmx.utils.result.PageBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class BaseServieImpl<T> implements BaseServie<T> {

    @Resource
    public BaseMapper <T> baseMapper;

    @Override
    public PageBean<T> getPage(PageBean<T> page, T obj) {
        System.out.println("BaseServieImpl.getPage");
        return null;
    }

    @Override
    public void insert(T obj) throws Exception {
        baseMapper.insert(obj);
    }

    @Override
    public void edit(T obj) throws Exception {
        baseMapper.update(obj);
        System.out.println("BaseServieImpl.edit");
    }

    @Override
    public void delete(Integer id) throws Exception {
        baseMapper.delete(id);
    }

}
