package com.zt.nightrun.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：by chris
 * 日期：on 2017/9/19 - 下午7:49.
 * 邮箱：android_cjx@163.com
 */
@Entity
public class Message {
    @Id
    @NotNull
    private long timeStamp;
    private String title;
    private String content;

    @Generated(hash = 1366923659)
    public Message(long timeStamp, String title, String content) {
        this.timeStamp = timeStamp;
        this.title = title;
        this.content = content;
    }

    @Generated(hash = 637306882)
    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "timeStamp=" + timeStamp +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
