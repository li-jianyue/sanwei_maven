package com.cnki.mybookepubrelease.model;

public class AudioItems {
    private String Id;
    private String SubTitle;
    private String SortNo;
    private String Duration;
    private String FilePath;
    public void setId(String Id) {
        this.Id = Id;
    }
    public String getId() {
        return Id;
    }

    public void setSubTitle(String SubTitle) {
        this.SubTitle = SubTitle;
    }
    public String getSubTitle() {
        return SubTitle;
    }

    public void setSortNo(String SortNo) {
        this.SortNo = SortNo;
    }
    public String getSortNo() {
        return SortNo;
    }

    public void setDuration(String Duration) {
        this.Duration = Duration;
    }
    public String getDuration() {
        return Duration;
    }

    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }
    public String getFilePath() {
        return FilePath;
    }

}
