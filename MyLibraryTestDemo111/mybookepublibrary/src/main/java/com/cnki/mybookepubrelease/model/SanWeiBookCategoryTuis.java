package com.cnki.mybookepubrelease.model;

public class SanWeiBookCategoryTuis {
    private String Id;
    private String BookName;
    private String Author;
    private String Cover;
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

    public void setAuthor(String Author) {
        this.Author = Author;
    }
    public String getAuthor() {
        return Author;
    }

    public void setCover(String Cover) {
        this.Cover = Cover;
    }
    public String getCover() {
        return Cover;
    }
}
