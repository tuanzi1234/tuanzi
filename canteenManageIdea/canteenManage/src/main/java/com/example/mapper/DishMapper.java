package com.example.mapper;

import com.example.entity.Dish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DishMapper {

    // 根据id查询菜品
    @Select("select * from `dish` where id = #{id}")
    Dish selectById(Integer id);

    // 查询所有菜品
    List<Dish> selectAll(Dish dish);

    // 添加菜品
    void insert(Dish dish);

    // 根据id修改菜品
    void updateById(Dish dish);

    // 根据id删除单个菜品
    @Delete("delete from `dish` where id = #{id}")
    void deleteById(Integer id);



}
