package com.hmx.hotWords.dto;

import com.hmx.hotWords.entity.HotWords;

/**
 * Created by Administrator on 2019/5/2.
 */
public class HotWordsDto extends HotWords{
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
