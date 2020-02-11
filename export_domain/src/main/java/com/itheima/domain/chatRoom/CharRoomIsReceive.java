package com.itheima.domain.chatRoom;

import javax.xml.crypto.Data;
import java.io.Serializable;

/**
 * 是否看过此条消息
 */
public class CharRoomIsReceive implements Serializable {
    private String userId; // 哪个用户阅读了此条消息
    private String messageTime;// 阅读了哪个时间的消息

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
