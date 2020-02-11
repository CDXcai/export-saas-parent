package com.itheima.web.controller.feedback;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.domain.feedback.Feedback;
import com.itheima.domain.feedback.FeedbackExample;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.FeedbackService;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.UserService;
import com.itheima.web.controller.base.BaseController;
import com.itheima.web.controller.rabbitMQ.RabbitMQProducer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


@Controller
@RequestMapping("/extend/feedback")
public class FeedbackController extends BaseController {

    @Reference
    private FeedbackService feedbackService;
    @Autowired
    private UserService userService;
    @Autowired
    private RabbitMQProducer rabbitMQProducer;
    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "/list",name = "查看反馈列表")
    @ResponseBody
    public Integer list(){
        List<Feedback> feedbackList=null;
        Integer feedbackNum = null;
        User loginUser = (User) session.getAttribute("loginUser");
        FeedbackExample feedbackExample = new FeedbackExample();
        if (loginUser.getDegree()==0){
            feedbackList = feedbackService.findAll(feedbackExample);
            //查询所有未解决的反馈 状态0为 未解决
            String state="0";
            feedbackNum = feedbackService.findByNewFeedback(state);
        }else {
            //查询自己的和公开的反馈 0为公开1为不公开
            feedbackList=feedbackService.findByUserId(loginUser.getId(),"0");
        }
        session.setAttribute("feedbackList",feedbackList);
        return feedbackNum;
    }
    @RequestMapping(value = "/edit",name = "新增反馈")
    public String edit(Feedback feedback){
        User loginUser = (User) session.getAttribute("loginUser");
        //新增为用户提交反馈，修改为saas管理员解答反馈
        System.out.println(feedback);
        if (StringUtils.isBlank(feedback.getFeedbackId())){
            //设置id
            feedback.setFeedbackId(UUID.randomUUID().toString());
            //反馈者的用户名
            feedback.setInputBy(loginUser.getUserName());
            //反馈者id
            feedback.setCreateBy(loginUser.getId());
            //反馈创建时间
            feedback.setCreateTime(new Date());
            feedback.setInputTime(new Date());
            //反馈者所在部门
            feedback.setCreateDept(loginUser.getDeptId());
            //反馈者联系电话
            feedback.setTel(loginUser.getTelephone());
            //设置状态为未解决 为解决状态码为0
            feedback.setState("0");
            //查询所有的saas管理员
            List<User> userList = userService.findByDegree("0");
            if(userList !=null && !userList.isEmpty()){
                //给所有的saas管理员发送邮件提醒
                for (User user : userList) {
                    Map<String, String> map = new HashMap<>();
                    map.put("to",user.getEmail());
                    map.put("subject","有新的反馈问题待解决");
                    map.put("content","有一条新的反馈来自于"+feedback.getInputBy()+"用户");
                    //rabbitMQProducer.sendData("mail.send",map);
                }
            }
            //添加到数据库
            feedbackService.save(feedback);
        }else {
            //解决人
            feedback.setAnswerBy(loginUser.getUserName());
            //解决时间
            feedback.setAnswerTime(new Date());
            //查询发送反馈的人
            User user = userService.findById(feedback.getCreateBy());
            if (user!=null) {
                Map<String, String> map = new HashMap<>();
                map.put("to", user.getEmail());
                map.put("subject", "您的反馈问题已有答复");
                map.put("content", loginUser.getUserName() + "解决了您的问题，请登陆网站查看");
                //rabbitMQProducer.sendData("mail.send",map);
            }
            //设置状态已解决
            feedback.setState("1");
            feedbackService.update(feedback);
        }
        //跳转用的，暂时没有好的替代
        List<Module> moduleList = moduleService.findByUser(loginUser);
        request.setAttribute("moduleList",moduleList);
        return "home/main";
    }
    @RequestMapping(value = "/toAdd",name = "用户准备新建反馈")
    public String toAdd(){
        return "/extend/feedback/feedback-add";
    }
    @RequestMapping(value = "/toUpdate",name = "查看反馈")
    public String toUpdate(String id){
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser.getDegree()!=0){
            return "redirect:/extend/feedback/toView.do?id="+id;
        }
        Feedback feedback = feedbackService.findById(id);
        request.setAttribute("feedback",feedback);

        return "/extend/feedback/feedback-answer";
    }
    @RequestMapping(value = "/toView",name = "查看反馈")
    public String toView(String id){
        Feedback feedback = feedbackService.findById(id);
        request.setAttribute("feedback",feedback);
        return "/extend/feedback/feedback-view";
    }
}
