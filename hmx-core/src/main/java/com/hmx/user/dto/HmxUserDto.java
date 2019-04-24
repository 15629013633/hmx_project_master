package com.hmx.user.dto;

import com.hmx.user.entity.HmxUser;
/**
 * Dto entity
 */
public class HmxUserDto extends HmxUser {

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