package com.hmx.files.dto;

import com.hmx.files.entity.HmxFiles;

/**
 * Created by Administrator on 2019/4/26.
 */
public class HmxFilesDto extends HmxFiles {
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
