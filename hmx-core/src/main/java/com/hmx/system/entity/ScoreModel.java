package com.hmx.system.entity;

/**
 * Created by Administrator on 2019/6/2.
 */
public class ScoreModel {
    private Integer scoreId;   //评分id
    private String userPhone;   //用户手机号
    private Integer contentId;  //内容id
    private double score;        //分数
    private long createTime; //评分时间

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
