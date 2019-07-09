package com.hmx.user.dto;

import com.hmx.user.entity.po.UserModel;

/**
 * Created by Administrator on 2019/7/8.
 */
public class UserModelDto extends UserModel{
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
