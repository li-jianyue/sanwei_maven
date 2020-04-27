package com.cnki.mybookepubrelease.dao;

import com.cnki.mybookepubrelease.model.DownLoadHistory;
import com.huangfei.library.activeandroid.query.Delete;
import com.huangfei.library.activeandroid.query.Select;
import com.huangfei.library.activeandroid.query.Update;

import java.util.List;

/**
 * Created by Administrator on 2020/4/6.
 * 搜索历史数据库操作类
 */
public class DownLoadHistoryDao {
    private static volatile DownLoadHistoryDao instance = null;

    private DownLoadHistoryDao() {
    }
    public static DownLoadHistoryDao getInstance() {
        if (instance == null) {
            synchronized (DownLoadHistoryDao.class) {
                if (instance == null) {
                    instance = new DownLoadHistoryDao();
                }
            }
        }
        return instance;
    }

    /**
     * 插入单条数据
     * @param bookfileid 图书id
     * @param bookname 图书名称
     * @param savepath 下载路径
     */
    public void save(String bookfileid,String bookname, String savepath) {
        DownLoadHistory downLoadHistory = new DownLoadHistory();
        downLoadHistory.setBookfileid(bookfileid);
        downLoadHistory.setBookname(bookname);
        downLoadHistory.setSavepath(savepath);
        downLoadHistory.setTime(System.currentTimeMillis());
        downLoadHistory.save();
    }

    /**
     * 删除
     */
    public void deleteAll() {
        new Delete().from(DownLoadHistory.class).execute();
    }



    public List<DownLoadHistory> selectById(String bookid){
        List<DownLoadHistory> list =new Select()
                .from(DownLoadHistory.class)
                .where("bookfileid = ?",bookid).execute();
            return list;
    }
    /**
     * 查询
     */
    public List<DownLoadHistory> selectAll() {
        List<DownLoadHistory> list = new Select().from(DownLoadHistory.class)
                .orderBy("time DESC")
                .execute();
        return list;
    }

    /**
     * 更新
     */
    public void update(DownLoadHistory history) {
        new Update(DownLoadHistory.class)
                .set("time = ?", System.currentTimeMillis())
                .where("Id = ?", history.getId())
                .execute();
    }
}
