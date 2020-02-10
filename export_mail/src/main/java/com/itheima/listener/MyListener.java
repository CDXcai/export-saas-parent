package com.itheima.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.common.utils.MailUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 邮件监听器
 */
public class MyListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            //1.解析数据
            JsonNode jsonNode = new ObjectMapper().readTree(message.getBody());
            //2.获得数据
            String to = jsonNode.get("to").asText();
            String subject = jsonNode.get("subject").asText();
            String content = jsonNode.get("content").asText();

            System.out.println(to);
            System.out.println(subject);
            System.out.println(content);
            //3.发送邮件
            MailUtil.sendMsg(to , subject , content);
            System.out.println("发送邮件ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
