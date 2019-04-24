package com.hmx.movie.dto;

import com.hmx.movie.entity.HmxMovie;
/**
 * Dto entity
 */
public class HmxMovieDto extends HmxMovie {

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