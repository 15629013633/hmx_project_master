package com.hmx.system.dto;

import com.hmx.system.entity.HmxVideo;

/**
 * Created by Administrator on 2019/5/19.
 */
public class HmxVideoDto extends HmxVideo {
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
