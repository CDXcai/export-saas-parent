package com.itheima.web.controller.chatRoom;

import com.itheima.domain.chatRoom.CharRoomContent;
import com.itheima.domain.system.User;
import com.itheima.service.chatRoom.CharRoomContentService;
import com.itheima.service.chatRoom.CharRoomIsReceiveService;
import com.itheima.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/chatRoom")
public class ChatRoomController extends BaseController {
    @Autowired
    private CharRoomContentService charRoomContentService; // 消息的内容
    @Autowired
    private CharRoomIsReceiveService charRoomIsReceiveService; // 已读消息列表
    /**
     * 跳转到聊天室页面
     * @return
     */
    @RequestMapping("/toChatRoom")
    public String toChatRoom(){
        // 跳转到聊天室页面
        return "charRoom/chatRoom";
    }


    // 接受消息
    @RequestMapping("/receiveMassage")
    @ResponseBody
    public List<CharRoomContent> receiveMassage(){
        // 获取当前的登录的对象
        User user = (User) session.getAttribute("loginUser");
        // 查询所有未读的消息
        List<CharRoomContent> contentList = charRoomContentService.findByCompany(companyId,user.getId());
        return contentList;
    }


    // 发送消息
    @RequestMapping("/sendMessage")
    @ResponseBody
    public CharRoomContent sendMessage(String context){
        // 获取当前登录对象
        User user = (User) session.getAttribute("loginUser");
        // 创建一个消息对象
        CharRoomContent charRoomContent = new CharRoomContent();
        {// 赋值属性
            charRoomContent.setCompanyId(companyId);
            charRoomContent.setContext(context);
            charRoomContent.setUserId(user.getId());
            // 获取当前时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String data = simpleDateFormat.format(new Date());
            charRoomContent.setTime(data);
            charRoomContent.setUserName(user.getUserName());
        }
        // 添加到数据库中
        charRoomContentService.save(charRoomContent);
        // 返回消息内容
        return charRoomContent;
    }




}
