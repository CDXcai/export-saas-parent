package com.itheima.dao.chatRoom;

import com.itheima.domain.chatRoom.CharRoomContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CharRoomContentDao {

    // 查询指定用户未读的消息
    List<CharRoomContent> findByCompanyId(@Param("cid") String companyId,@Param("uid") String userId);
    // 消息信息添加到数据库中
    void save(CharRoomContent charRoomContent);
}
