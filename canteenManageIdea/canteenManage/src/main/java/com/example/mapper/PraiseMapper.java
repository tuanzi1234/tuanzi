package com.example.mapper;

import com.example.entity.Praise;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PraiseMapper {

    // 根据id查询点赞记录
    @Select("select * from `praise` where id = #{id}")
    Praise selectById(Integer id);

    // 查询所有点赞记录
    List<Praise> selectAll(Praise praise);

    // 添加点赞记录
    void insert(Praise praise);

    // 根据id删除单个点赞记录
    @Delete("delete from `praise` where id = #{id}")
    void deleteById(Integer id);

    // 根据id删除单个点赞记录
    @Delete("delete from `praise` where dish_id = #{DishId}")
    void deleteByDishId(Integer id);
}
