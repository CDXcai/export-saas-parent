package com.itheima.web.controller.chatRoom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chatRoom")
public class ChatRoomController {
    /**
     * 跳转到聊天室页面
     * @return
     */
    @RequestMapping("/toChatRoom")
    public String toChatRoom(){
        // 跳转到聊天室页面
        return "charRoom/chatRoom";
    }

}
