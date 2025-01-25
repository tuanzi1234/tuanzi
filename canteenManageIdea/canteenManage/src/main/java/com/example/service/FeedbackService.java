package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Feedback;
import com.example.mapper.FeedbackMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;// 注入FeedbackMapper，用于数据库操作

    // 查询所有反馈意见
    public List<Feedback> selectAll(Feedback feedback) {
        return feedbackMapper.selectAll(feedback);
    }

    // 根据id查询反馈意见
    public Feedback selectById(Integer id) {
        return feedbackMapper.selectById(id);
    }

    // 分页查询反馈意见
    public PageInfo<Feedback> selectPage(Feedback feedback, Integer pageNum, Integer pageSize) {
        Account currentAccount = TokenUtils.getCurrentAccount();
        // 非管理员只能查看自己的反馈意见
        if (RoleEnum.STUDENT.name().equals(currentAccount.getRole())) {
            feedback.setStudentId(currentAccount.getId());
        }
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<Feedback> list = feedbackMapper.selectAll(feedback); // 执行查询，根据传入的查询条件获取反馈意见列表
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加反馈意见
    public void add(Feedback feedback) {
        Account currentAccount = TokenUtils.getCurrentAccount();
        feedback.setStudentId(currentAccount.getId());
        feedback.setCreateTime(DateUtil.now());// 设置公告发布时间
        feedbackMapper.insert(feedback);// 调用mapper层添加反馈意见
    }

    // 根据id更新反馈意见信息
    public void updateById(Feedback feedback) {
        //获取当前时间
        feedback.setReplyTime(DateUtil.now());
        feedbackMapper.updateById(feedback);
    }

    // 根据id删除单个反馈意见
    public void deleteById(Integer id) {
        feedbackMapper.deleteById(id);
    }


    // 批量删除反馈意见
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            feedbackMapper.deleteById(id);
        }
    }


}
