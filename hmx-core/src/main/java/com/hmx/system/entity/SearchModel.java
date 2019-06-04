package com.hmx.system.entity;

import com.hmx.category.dto.HmxCategoryContentDto;

/**
 * 搜索实体类
 * Created by Administrator on 2019/5/3.
 */
public class SearchModel extends HmxCategoryContentDto {
    private String videoId;
    private long width;
    private long height;
    private long size;
    //private String playURL;
    private String bitrate;
    private Integer definition;   //画质 0表示全部  1标清  2高清  3超清  4 1080P
    private Integer duration;   //时长  0表示全部  1 60分钟以上  2 30-60分钟   3 10-30分钟   4  10分钟以内
    private String format;
    private String fps;
    private long encrypt;
    private Integer parentCategoryId;
//    private String plaintext;
//    private String complexity;
//    private String streamType;
    private String rand;
    private String jobId;
//    private String preprocessStatus;
//    private String watermarkId;
//    private String status;
//    private String creationTime;
//    private String modificationTime;
//    private String encryptType;
//    private String narrowBandType;

    private Integer dateTime;   //日期  0全部  1 一天内   2 一周内   3 一月内  4 一年内


    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public Integer getDefinition() {
        return definition;
    }

    public void setDefinition(Integer definition) {
        this.definition = definition;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFps() {
        return fps;
    }

    public void setFps(String fps) {
        this.fps = fps;
    }

    public long getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(long encrypt) {
        this.encrypt = encrypt;
    }

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getDateTime() {
        return dateTime;
    }

    public void setDateTime(Integer dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
