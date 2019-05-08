package com.hmx.base.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songjinbao on 2019/5/8.
 */
public class RcmbModel {
    private Integer mode;   //1为轮播图  2为一级分类列表  3为媒资列表
    private String icon;
    private boolean hasMore;
    private String title;
    private Integer showTitle;   //是否展示标题  0不展示  1展示
    private List<RowsModel> rows = new ArrayList<>();

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(Integer showTitle) {
        this.showTitle = showTitle;
    }

    public List<RowsModel> getRows() {
        return rows;
    }

    public void setRows(List<RowsModel> rows) {
        this.rows = rows;
    }
}
