package com.itheima.common.utils;


import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
	/**
	 *
	 * @param to 收件人
	 * @param subject  主题
	 * @param content 内容
	 * @throws Exception
	 */
	//实现邮件发送的方法
	public static void sendMsg(String to ,String subject ,String content) throws Exception{
		//1.登录网站  2.写信  3.发送邮件
		//配置
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.163.com");  //设置主机地址   smtp.qq.com    smtp.sina.com

		//授权开启 可以进行代码登录
		props.setProperty("mail.smtp.auth", "true");//授权认证 代码客户端访问 必须设置为true  需要手机验证

		//2.产生一个用于邮件发送的Session对象
		Session session = Session.getInstance(props); //连接会话   连接到发送邮件服务器上
		//--还没有登录---------------------------------------
		//3.产生一个邮件的消息对象
		MimeMessage message = new MimeMessage(session);

		//4.设置消息的发送者
		Address fromAddr = new InternetAddress("itcast_hzb163@163.com");
		message.setFrom(fromAddr);

		//5.设置消息的接收者
		Address toAddr = new InternetAddress(to);
		//TO 直接发送  CC抄送    BCC密送
		message.setRecipient(MimeMessage.RecipientType.TO, toAddr);

		//6.设置主题
		message.setSubject(subject);
		//7.设置正文
		//message.setText(content); //设置文本
		//message.setText(content, "text/html");
		//设置内容
		message.setContent(content , "text/html;charset=UTF-8");
		//-----------------------------------------写信完成
		//8.准备发送，得到火箭
		//登录 并发送
		Transport transport = session.getTransport("smtp");
		//9.设置火箭的发射目标
		//connect连接
		//密码只能写授权密码 (手机开启smtp的时候设置的密码  不等于网站的登录密码)
		transport.connect("smtp.163.com", "itcast_hzb163@163.com", "true123"); //密码 授权密码!=登陆密码
		//10.发送
		transport.sendMessage(message, message.getAllRecipients());

		//11.关闭
		transport.close();
	}

	public static void main(String[] args) throws Exception {
		String to="itcast_hzb126@126.com";//参数1 是接收人
		String subject = "这是我邮件的主题";
		String content= "<a href='www.baidu.com'>小李同学</a>"; //可以设置邮件的类型
		MailUtil.sendMsg(to ,subject  ,content );
		System.out.println("发送邮件成功");
	}

}
