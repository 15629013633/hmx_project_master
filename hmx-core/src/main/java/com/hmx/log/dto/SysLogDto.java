package com.hmx.log.dto;

import com.hmx.log.entity.SysLog;

/**
 * Created by Administrator on 2019/7/7.
 */
public class SysLogDto extends SysLog {
    private Integer limit;

    private String orderByClause;

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }
}
