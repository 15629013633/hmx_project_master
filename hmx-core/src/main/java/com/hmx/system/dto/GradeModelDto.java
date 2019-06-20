package com.hmx.system.dto;

import com.hmx.system.entity.GradeModel;

/**
 * Created by Administrator on 2019/6/20.
 */
public class GradeModelDto extends GradeModel {
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
