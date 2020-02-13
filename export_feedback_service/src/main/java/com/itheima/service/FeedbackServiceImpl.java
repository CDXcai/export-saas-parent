package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.feedback.FeedbackDao;
import com.itheima.domain.feedback.Feedback;
import com.itheima.domain.feedback.FeedbackExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;

    /**
     * 新增
     * @param feedback
     */
    @Override
    public void save(Feedback feedback) {
        feedbackDao.insertSelective(feedback);
    }

    /**
     * 修改
     * @param feedback
     */
    @Override
    public void update(Feedback feedback) {
        feedbackDao.updateByPrimaryKey(feedback);
    }

    /**
     * 查询
     * @param feedbackExample
     * @return
     */
    @Override
    public List<Feedback> findAll(FeedbackExample feedbackExample) {
        List<Feedback> feedbackList = feedbackDao.selectByExample(feedbackExample);
        return feedbackList;
    }

    @Override
    public List<Feedback> findByUserId(String userId, String isShare) {
        return feedbackDao.findByUserId(userId,isShare);
    }

    @Override
    public void delete(String id) {
        feedbackDao.deleteByPrimaryKey(id);
    }

    @Override
    public Feedback findById(String id) {
        return feedbackDao.selectByPrimaryKey(id);
    }

    @Override
    public Integer findByNewFeedback(String state) {
        return feedbackDao.findByNewFeedback(state);
    }

    @Override
    public List<Feedback> findByToday(String today, String state) {
        return feedbackDao.findByToday(today,state);
    }
}
