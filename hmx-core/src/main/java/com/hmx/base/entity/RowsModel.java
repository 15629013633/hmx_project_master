package com.hmx.base.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songjinbao on 2019/5/8.
 */
public class RowsModel {
    private Integer row;
    private Integer line;
    private String imageType;
    private List<?> items = new ArrayList<Object>();

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }
}
