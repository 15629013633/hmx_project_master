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
    private Integer type;       //0表示从管理系统添加的热词  1表示搜索时候保存到数据库的关键词  默认0
    private Integer frequency;   //搜索次数

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }
}
