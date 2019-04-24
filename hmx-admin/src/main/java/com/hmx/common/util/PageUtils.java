package com.hmx.common.util;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;


public class PageUtils {
    /**
     * 创建分页请求。
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * @param sortType 排序字段
     * @param direction 排序方向
     */
    public static PageRequest buildPageRequest(int pageNum, int pageSize, String sortType, String direction) {
        Sort sort = null;

        if (sortType == null || sortType.isEmpty()) {
            return new PageRequest(pageNum - 1, pageSize);
        } else if (direction != null && !direction.isEmpty()) {
            if (Sort.Direction.ASC.equals(direction)) {
                sort = new Sort(Sort.Direction.ASC, sortType);
            } else {
                sort = new Sort(Sort.Direction.DESC, sortType);
            }
            return new PageRequest(pageNum - 1, pageSize, sort);
        } else {
            sort = new Sort(Direction.ASC, sortType);
            return new PageRequest(pageNum - 1, pageSize, sort);
        }
    }

    /**
     * 创建分页请求(该方法可以放到util类中).
     */
    public static PageRequest buildPageRequest(int pageNum, int pageSize, String sortType) {
        return buildPageRequest(pageNum, pageSize, sortType, null);
    }

    /**
     * 创建分页请求
     */
    public static PageRequest buildPageRequest(int pageNum, int pageSize, Sort sort) {
        return new PageRequest(pageNum - 1, pageSize, sort);
    }

    /**
     * 创建分页请求(该方法可以放到util类中).
     */
    public static PageRequest buildPageRequest(int pageNum, int pageSize) {
        return buildPageRequest(pageNum, pageSize, null, null);
    }
}
