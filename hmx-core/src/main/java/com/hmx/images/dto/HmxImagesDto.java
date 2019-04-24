package com.hmx.images.dto;

import com.hmx.images.entity.HmxImages;

/**
 * Dto entity
 */
public class HmxImagesDto extends HmxImages {

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