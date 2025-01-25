package com.example.mapper;

import com.example.entity.Feedback;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FeedbackMapper {

    // 根据id查询反馈意见
    @Select("select * from `feedback` where id = #{id}")
    Feedback selectById(Integer id);

    // 查询所有反馈意见
    List<Feedback> selectAll(Feedback feedback);

    // 添加反馈意见
    void insert(Feedback feedback);

    // 根据id修改反馈意见
    void updateById(Feedback feedback);

    // 根据id删除单个反馈意见
    @Delete("delete from `feedback` where id = #{id}")
    void deleteById(Integer id);



}
