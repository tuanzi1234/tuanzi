package com.example.mapper;

import com.example.entity.Search;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SearchMapper {

    // 根据id查询搜索信息
    @Select("select * from `search` where id = #{id}")
    Search selectById(Integer id);

    // 查询所有搜索信息
    List<Search> selectAll(Search search);

    // 添加搜索信息
    void insert(Search search);

    // 根据id删除单个搜索信息
    @Delete("delete from `search` where id = #{id}")
    void deleteById(Integer id);



}
