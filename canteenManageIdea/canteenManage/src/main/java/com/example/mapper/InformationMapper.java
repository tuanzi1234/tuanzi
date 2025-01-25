package com.example.mapper;

import com.example.entity.Information;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InformationMapper {

    // 根据id查询食堂资讯
    @Select("select * from `information` where id = #{id}")
    Information selectById(Integer id);

    // 查询所有食堂资讯
    List<Information> selectAll(Information information);

    // 添加食堂资讯
    void insert(Information information);

    // 根据id修改食堂资讯
    void updateById(Information information);

    // 根据id删除单个食堂资讯
    @Delete("delete from `information` where id = #{id}")
    void deleteById(Integer id);



}
