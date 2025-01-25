package com.example.mapper;

import com.example.entity.Collect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CollectMapper {

    // 根据id查询收藏记录
    @Select("select * from `collect` where id = #{id}")
    Collect selectById(Integer id);

    // 查询所有收藏记录
    List<Collect> selectAll(Collect collect);

    // 添加收藏记录
    void insert(Collect collect);

    // 根据id删除单个收藏记录
    @Delete("delete from `collect` where id = #{id}")
    void deleteById(Integer id);

    // 根据id删除单个收藏记录
    @Delete("delete from `collect` where dish_id = #{DishId}")
    void deleteByDishId(Integer id);
}
