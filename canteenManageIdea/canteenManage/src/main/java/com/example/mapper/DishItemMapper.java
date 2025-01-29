package com.example.mapper;

import com.example.entity.DishItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DishItemMapper {

    // 根据id查询点单信息
    @Select("select * from `dish_item` where id = #{id}")
    DishItem selectById(Integer id);

    // 查询所有点单信息
    List<DishItem> selectAll(DishItem dishItem);

    // 添加点单信息
    void insert(DishItem dishItem);

    // 根据id修改点单信息
    void updateById(DishItem dishItem);

    // 根据id删除单个点单信息
    @Delete("delete from `dish_item` where id = #{id}")
    void deleteById(Integer id);



}
