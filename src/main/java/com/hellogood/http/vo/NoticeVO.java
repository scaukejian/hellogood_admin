package com.hellogood.http.vo;

/**
 * Created by kejian
 * 通知
 */
public class NoticeVO {

    private Integer userId;//用户ID
    private Integer type;//类型
    private String content; //类容

    public NoticeVO(Integer userId) {
        this.userId = userId;
    }

    public NoticeVO(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public NoticeVO(Integer userId, Integer type, String content) {
        this.userId = userId;
        this.type = type;
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NoticeVO{" +
                "userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
