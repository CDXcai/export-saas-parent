package com.itheima.web.controller.aspect;

import com.itheima.domain.log.SysLog;
import com.itheima.domain.system.User;
import com.itheima.service.log.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAspect {

    @Autowired
    private LogService logService;

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    //切面=切点(切入点:正在执行的方法 ProceedingJoinPoint:切点对象 ) +增强(增强的方法)
    @Around("execution(* com.itheima.web.controller.*.*.*(..))")
    public Object around(ProceedingJoinPoint pjp){//method.invoke(对象);
        //加入数据库日志即可
        //System.out.println("拦截到了请求");
        //放行
        Object o = null;
        try {
            //创建对象
            SysLog sysLog = new SysLog();
            User loginUser =(User) session.getAttribute("loginUser");

            /*if(方法==xxx方法名称){
                 不保存日志
            }*/

            //获得切点的签名对象
            //了解
            MethodSignature methodSignature =  (MethodSignature)pjp.getSignature();
            Method method = methodSignature.getMethod();

            //注解的解析: 注解可以写的位置 方法上 类上 构造上 参数上 , 必须使用反射对象
            //通过method对象获得注解  annotation
            boolean flag = method.isAnnotationPresent(RequestMapping.class); //判断是否含有requestMapping注解


            if(loginUser != null && flag){ //方法上必须具备requestMapping注解才可以保存日志
                //String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                //如果是日期类型 new Date();数据库自动转换  如果是字符串 必须使用SimpleDateFormat
                sysLog.setTime(new Date()); //当前操作的时间
                sysLog.setCompanyName(loginUser.getCompanyName());//当前操作人的企业名称
                sysLog.setCompanyId(loginUser.getCompanyId());//当前操作人的企业id
                sysLog.setUserName(loginUser.getUserName());//当前操作的人

                String remoteAddr = request.getRemoteAddr();
                sysLog.setIp(remoteAddr);//操作人的ip地址 request.getRemouteAddr(); 获得远程主机的ip地址

                if(flag){
                    //获得注解
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    //获得注解中的属性  注解的属性调用 属性的名称()  对象中的属性调用get方法()
                    String methodName = annotation.name();
                    sysLog.setAction(methodName); //操作方法的中文名称(注解中自己添加的name属性)
                }
                sysLog.setMethod(method.getName());//操作方法的英文名称

                //日志的保存
                logService.save(sysLog);
            }

            o = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return o;
    }
}
