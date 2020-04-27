package com.cnki.mybookepubrelease.model;

public class CategoryVedioTuis {
    private String Id;
    private String VideoName;
    private String BianDao;
    private String Duration;
    private String Cover;
    public void setId(String Id) {
        this.Id = Id;
    }
    public String getId() {
        return Id;
    }

    public void setVideoName(String VideoName) {
        this.VideoName = VideoName;
    }
    public String getVideoName() {
        return VideoName;
    }

    public void setBianDao(String BianDao) {
        this.BianDao = BianDao;
    }
    public String getBianDao() {
        return BianDao;
    }

    public void setDuration(String Duration) {
        this.Duration = Duration;
    }
    public String getDuration() {
        return Duration;
    }

    public void setCover(String Cover) {
        this.Cover = Cover;
    }
    public String getCover() {
        return Cover;
    }
}
