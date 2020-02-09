package com.hmx.system.base;

import com.hmx.system.entity.AreaModel;
import com.hmx.utils.result.PageBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Component
public interface BaseServie<T> {



    PageBean<T> getPage(PageBean<T> page, T obj);

    void insert(T obj) throws Exception;

    void edit(T obj) throws Exception;

    void delete(Integer id) throws Exception;

}
