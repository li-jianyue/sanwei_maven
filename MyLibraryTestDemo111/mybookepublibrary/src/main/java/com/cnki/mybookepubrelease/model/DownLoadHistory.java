package com.cnki.mybookepubrelease.model;

import com.huangfei.library.activeandroid.Model;
import com.huangfei.library.activeandroid.annotation.Column;
import com.huangfei.library.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2019/12/18.
 * 下载历史
 */
@Table(name = "DownLoadHistory")
public class DownLoadHistory extends Model {
    @Column(name = "bookfileid")
    private String bookfileid;//图书标识
    @Column(name = "bookname")
    private String bookname;//图书名称
    @Column(name = "savepath")
    private String savepath;//存储路径

    @Column(name = "time")
    private long time;

    public String getBookfileid() {
        return bookfileid;
    }

    public void setBookfileid(String bookfileid) {
        this.bookfileid = bookfileid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getSavepath() {
        return savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
