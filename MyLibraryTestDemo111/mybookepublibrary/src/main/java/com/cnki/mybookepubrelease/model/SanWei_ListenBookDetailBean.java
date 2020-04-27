package com.cnki.mybookepubrelease.model;

import java.util.List;

public class SanWei_ListenBookDetailBean {
    private String Id;
    private String BookName;
    private String Cover;
    private String Author;
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
    private String ZhujiangDescription;
    private String ZhujiangImg;
    private String Brand;
    private String Source;
    private String Copyright;
    private List<AudioItems> AudioItems;
    private List<SanWeiListenBookCategoryTuis> CategoryTuis;
    private List<SanWeiListenBookCategoryTuis> AuthorTuis;
    public void setId(String Id) {
        this.Id = Id;
    }
    public String getId() {
        return Id;
    }

    public void setBookName(String BookName) {
        this.BookName = BookName;
    }
    public String getBookName() {
        return BookName;
    }

    public void setCover(String Cover) {
        this.Cover = Cover;
    }
    public String getCover() {
        return Cover;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }
    public String getAuthor() {
        return Author;
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

    public void setZhujiangDescription(String ZhujiangDescription) {
        this.ZhujiangDescription = ZhujiangDescription;
    }
    public String getZhujiangDescription() {
        return ZhujiangDescription;
    }

    public void setZhujiangImg(String ZhujiangImg) {
        this.ZhujiangImg = ZhujiangImg;
    }
    public String getZhujiangImg() {
        return ZhujiangImg;
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

    public void setAudioItems(List<AudioItems> AudioItems) {
        this.AudioItems = AudioItems;
    }
    public List<AudioItems> getAudioItems() {
        return AudioItems;
    }

    public void setSanWeiBookCategoryTuis(List<SanWeiListenBookCategoryTuis> SanWeiBookCategoryTuis) {
        this.CategoryTuis = SanWeiBookCategoryTuis;
    }
    public List<SanWeiListenBookCategoryTuis> getSanWeiBookCategoryTuis() {
        return CategoryTuis;
    }

    public void setAuthorTuis(List<SanWeiListenBookCategoryTuis> AuthorTuis) {
        this.AuthorTuis = AuthorTuis;
    }
    public List<SanWeiListenBookCategoryTuis> getAuthorTuis() {
        return AuthorTuis;
    }

}
