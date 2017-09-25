package com.hellogood.http.vo;

import java.util.Date;
import java.util.List;

/**
 * 短信通知
 * Created by kejian
 */
public class SmsNoticeTask {
    private Integer sourceId;//来源ID
    private String type;//moment
    private List<String> phones;//手机号码
    private String content;//内容
    private Date executeTime;//执行时间

    public SmsNoticeTask() {
    }

    public SmsNoticeTask(Integer sourceId, String type, List<String> phones, String content, Date executeTime) {
        this.sourceId = sourceId;
        this.type = type;
        this.phones = phones;
        this.content = content;
        this.executeTime = executeTime;
    }

    public SmsNoticeTask(String type, List<String> phones, String content, Date executeTime) {
        this.type = type;
        this.phones = phones;
        this.content = content;
        this.executeTime = executeTime;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    @Override
    public String toString() {
        return "SmsNoticeTask{" +
                "sourceId=" + sourceId +
                ", type='" + type + '\'' +
                ", phones=" + phones +
                ", content='" + content + '\'' +
                ", executeTime=" + executeTime +
                '}';
    }
}
