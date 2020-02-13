

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class FeedbackProvider {
    public static void main(String[] args){
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
            context.start();
            System.out.println("FeedbackProdiver启动成功");
            System.in.read(); // 按任意键退出
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
