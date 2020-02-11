package com.itheima.domain.chatRoom;

import javax.xml.crypto.Data;
import java.io.Serializable;

/**
 * 发送的消息表
 */
public class CharRoomContent implements Serializable {
    private String time; // 消息产生的时间
    private String context;// 消息的 内容
    private String companyId;// 哪个企业之间的消息
    private String userId;// 消息的创建人
    private String userName; // 发消息人的昵称

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
