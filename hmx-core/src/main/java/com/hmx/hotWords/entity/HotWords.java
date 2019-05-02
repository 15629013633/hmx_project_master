package com.hmx.hotWords.entity;

import java.util.Date;

/**
 * 热词实体类
 * Created by Administrator on 2019/5/2.
 */
public class HotWords {
    private Integer hotWordId;
    private String title;   //热词名称
    private Integer sort;  //热词排序
    private Date createDate;   //创建时间

    public HotWords(){
        super();
    }

    public HotWords(Integer hotWordId,String title,Integer sort,Date createDate){
        super();
        this.hotWordId = hotWordId;
        this.title = title;
        this.sort= sort;
        this.createDate = createDate;
    }

    public Integer getHotWordId() {
        return hotWordId;
    }

    public void setHotWordId(Integer hotWordId) {
        this.hotWordId = hotWordId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
