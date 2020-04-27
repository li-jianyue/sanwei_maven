package com.cnki.mybookepubrelease.model;

import java.util.List;

public class SanWei_VedioDetailBean {
    private String Id;
    private String VideoName;
    private String Cover;
    private String BianDao;
    private String Duration;
    private String PublishDate;
    private String AddTime;
    private String Category1;
    private String Category2;
    private String Category3;
    private String CategoryCode;
    private String PlayingTotal;
    private String Sharing;
    private String Collecting;
    private String Grade;
    private String Description;
    private String Zhujiang;
    private String Brand;
    private String Source;
    private String Copyright;
    private List<VideoItems> VideoItems;
    private List<CategoryVedioTuis> CategoryTuis;
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

    public void setCover(String Cover) {
        this.Cover = Cover;
    }
    public String getCover() {
        return Cover;
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

    public void setPublishDate(String PublishDate) {
        this.PublishDate = PublishDate;
    }
    public String getPublishDate() {
        return PublishDate;
    }

    public void setAddTime(String AddTime) {
        this.AddTime = AddTime;
    }
    public String getAddTime() {
        return AddTime;
    }

    public void setCategory1(String Category1) {
        this.Category1 = Category1;
    }
    public String getCategory1() {
        return Category1;
    }

    public void setCategory2(String Category2) {
        this.Category2 = Category2;
    }
    public String getCategory2() {
        return Category2;
    }

    public void setCategory3(String Category3) {
        this.Category3 = Category3;
    }
    public String getCategory3() {
        return Category3;
    }

    public void setCategoryCode(String CategoryCode) {
        this.CategoryCode = CategoryCode;
    }
    public String getCategoryCode() {
        return CategoryCode;
    }

    public void setPlayingTotal(String PlayingTotal) {
        this.PlayingTotal = PlayingTotal;
    }
    public String getPlayingTotal() {
        return PlayingTotal;
    }

    public void setSharing(String Sharing) {
        this.Sharing = Sharing;
    }
    public String getSharing() {
        return Sharing;
    }

    public void setCollecting(String Collecting) {
        this.Collecting = Collecting;
    }
    public String getCollecting() {
        return Collecting;
    }

    public void setGrade(String Grade) {
        this.Grade = Grade;
    }
    public String getGrade() {
        return Grade;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
    public String getDescription() {
        return Description;
    }

    public void setZhujiang(String Zhujiang) {
        this.Zhujiang = Zhujiang;
    }
    public String getZhujiang() {
        return Zhujiang;
    }

    public void setBrand(String Brand) {
        this.Brand = Brand;
    }
    public String getBrand() {
        return Brand;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }
    public String getSource() {
        return Source;
    }

    public void setCopyright(String Copyright) {
        this.Copyright = Copyright;
    }
    public String getCopyright() {
        return Copyright;
    }

    public void setVideoItems(List<VideoItems> VideoItems) {
        this.VideoItems = VideoItems;
    }
    public List<VideoItems> getVideoItems() {
        return VideoItems;
    }

    public void setCategoryTuis(List<CategoryVedioTuis> CategoryTuis) {
        this.CategoryTuis = CategoryTuis;
    }
    public List<CategoryVedioTuis> getCategoryTuis() {
        return CategoryTuis;
    }
}
