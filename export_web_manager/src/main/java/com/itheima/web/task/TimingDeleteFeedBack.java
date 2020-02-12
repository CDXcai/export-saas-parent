package com.itheima.web.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domain.feedback.Feedback;
import com.itheima.service.FeedbackService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimingDeleteFeedBack {
    @Reference
    private FeedbackService feedbackService;

    public void deleteFeedback() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        String today = simpleDateFormat.format(new Date());
        //状态一为解决的反馈
        String state = "1";
        //查询所有日期等于今天并且解决的反馈
        List<Feedback> feedbackIdList = feedbackService.findByToday(today, state);
        if (feedbackIdList != null && !feedbackIdList.isEmpty()) {
            for (Feedback feedback : feedbackIdList) {
                simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
                today = simpleDateFormat.format(new Date());
                String createDay = simpleDateFormat.format(feedback.getCreateTime());
                //代表只要不是当天的，就删除
                if (!today.equals(createDay)) {
                    feedbackService.delete(feedback.getFeedbackId());
                    System.out.println("删除反馈");
                }
                System.out.println("。。。。。。。。");
            }

        }else {
            System.out.println("今日没有要删除的反馈");
        }
    }

    }
