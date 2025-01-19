package com.example.mapper;

import com.example.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper {

    // 根据id查询分类
    @Select("select * from `category` where id = #{id}")
    Category selectById(Integer id);

    // 查询所有分类
    List<Category> selectAll(Category category);

    // 添加分类
    void insert(Category category);

    // 根据id修改分类
    void updateById(Category category);

    // 根据id删除单个分类
    @Delete("delete from `category` where id = #{id}")
    void deleteById(Integer id);



}
