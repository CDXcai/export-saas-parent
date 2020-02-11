package com.itheima.service;

import com.itheima.domain.feedback.Feedback;
import com.itheima.domain.feedback.FeedbackExample;

import java.util.List;

public interface FeedbackService {
    /**
     * 新增反馈
     * @param feedback
     */
    void save(Feedback feedback);

    /**
     * 修改反馈
     * @param feedback
     */
    void update(Feedback feedback);

    /**
     * 查询反馈
     */
    List<Feedback> findAll(FeedbackExample feedbackExample);

    List<Feedback> findByUserId(String id, String isShare);

    void delete(String id);

    Feedback findById(String id);

    Integer findByNewFeedback(String state);
}
