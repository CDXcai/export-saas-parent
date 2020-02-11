package com.itheima.service.chatRoom.impl;

import com.itheima.dao.chatRoom.CharRoomContentDao;
import com.itheima.dao.chatRoom.CharRoomIsReceiveDao;
import com.itheima.domain.chatRoom.CharRoomContent;
import com.itheima.domain.chatRoom.CharRoomIsReceive;
import com.itheima.service.chatRoom.CharRoomContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharRoomContentServiceImpl implements CharRoomContentService {
    @Autowired
    private CharRoomContentDao charRoomContentDao;
    @Autowired
    private CharRoomIsReceiveDao charRoomIsReceiveDao;
    @Override
    public List<CharRoomContent> findByCompany(String companyId,String userId) {
        // 查询所有未读的消息
        List<CharRoomContent> charRoomContents = charRoomContentDao.findByCompanyId(companyId, userId);
        // 消息设置为已读
        for (CharRoomContent charRoomContent : charRoomContents) {
            // 创建已读对象
            CharRoomIsReceive charRoomIsReceive = new CharRoomIsReceive();
            // 设置userId
            charRoomIsReceive.setUserId(userId);
            // 设置已读消息的时间
            charRoomIsReceive.setMessageTime(charRoomContent.getTime());
            // 插入到数据库
            charRoomIsReceiveDao.insert(charRoomIsReceive);
        }
        return charRoomContents;
    }

    @Override
    public void save(CharRoomContent charRoomContent) {
        // 把消息信息添加到数据库中
        charRoomContentDao.save(charRoomContent);
    }
}
