package com.itheima.service.chatRoom;

import com.itheima.domain.chatRoom.CharRoomContent;

import java.util.List;

public interface CharRoomContentService {
    // 查询所有未读的消息
    List<CharRoomContent> findByCompany(String companyId,String userId);

    // 保存消息
    void save(CharRoomContent charRoomContent);

}
