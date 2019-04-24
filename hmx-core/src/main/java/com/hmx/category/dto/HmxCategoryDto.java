package com.hmx.category.dto;

import java.util.Date;

import com.hmx.category.entity.HmxCategory;
/**
 * Dto entity
 */
public class HmxCategoryDto extends HmxCategory {

	private Integer limit;

	private String orderByClause;
	
	private Date beginDate;
	
	private Date endDate;
	

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

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