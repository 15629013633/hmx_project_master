package com.hmx.verifylog.dto;

import com.hmx.verifylog.entity.HmxVerifylog;
/**
 * Dto entity
 */
public class HmxVerifylogDto extends HmxVerifylog {

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