package com.example.mapper;

import com.example.entity.History;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HistoryMapper {

    // 根据id查询历史记录
    @Select("select * from `history` where id = #{id}")
    History selectById(Integer id);

    // 查询所有历史记录
    List<History> selectAll(History history);

    // 添加历史记录
    void insert(History history);

    // 根据id修改历史记录
    void updateById(History history);

    // 根据id删除单个历史记录
    @Delete("delete from `history` where id = #{id}")
    void deleteById(Integer id);

    // 根据id删除单个历史记录
    @Delete("delete from `history` where dish_id = #{DishId}")
    void deleteByDishId(Integer id);
}
