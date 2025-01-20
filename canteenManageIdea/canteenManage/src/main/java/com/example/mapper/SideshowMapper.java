package com.example.mapper;

import com.example.entity.Sideshow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SideshowMapper {

    // 根据id查询轮播图
    @Select("select * from `sideshow` where id = #{id}")
    Sideshow selectById(Integer id);

    // 查询所有轮播图
    List<Sideshow> selectAll(Sideshow sideshow);

    // 添加轮播图
    void insert(Sideshow sideshow);

    // 根据id修改轮播图
    void updateById(Sideshow sideshow);

    // 根据id删除单个轮播图
    @Delete("delete from `sideshow` where id = #{id}")
    void deleteById(Integer id);



}
