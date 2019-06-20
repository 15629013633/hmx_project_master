package com.hmx.system.entity;

/**
 * 等级实体类
 * Created by songjinbao on 2019/6/20.
 */
public class GradeModel {
    private Integer gradeId;   //等级id
    private String title;     //等级名称
    private Integer userLevel;    //级别   默认0

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }
}
