

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class CargoProvider {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        context.start();
        System.out.println("CargoProvider启动成功");
        System.in.read(); // 按任意键退出
    }
}
