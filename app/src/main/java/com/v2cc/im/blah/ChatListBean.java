package com.v2cc.im.blah;

import android.database.Cursor;

/**
 * 聊天记录bean
 *
 * @author yeliangliang
 *         ChatListBean
 *         2015-8-5 上午10:33:52
 */
public class ChatListBean {
    private String id;// id
    private String name;// 姓名
    private String passName;// 账户
    private String time;// 时间
    private String content;// 内容
    private String imgPath;// 图片地址
    private String source;// 来源 you or me
    private String status;// 状态 0已读 1未读

    public ChatListBean() {
    }

    public ChatListBean(String id, String name, String passName, String time, String content,
                        String imgPath, String source, String status) {
        super();
        this.id = id;
        this.name = name;
        this.passName = passName;
        this.time = time;
        this.content = content;
        this.imgPath = imgPath;
        this.source = source;
        this.status = status;
    }

    public ChatListBean(Cursor cursor) {
        this.id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        this.name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        this.passName = cursor.getString(cursor.getColumnIndexOrThrow("passName"));
        this.time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
        this.content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
        this.imgPath = cursor.getString(cursor.getColumnIndexOrThrow("imgPath"));
        this.source = cursor.getString(cursor.getColumnIndexOrThrow("source"));
        this.status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassName() {
        return passName;
    }

    public void setPassName(String passName) {
        this.passName = passName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
