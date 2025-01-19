package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper {

    // 根据id查询管理员
    @Select("select * from `admin` where id = #{id}")
    Admin selectById(Integer id);

    // 根据用户名查询管理员
    @Select("select * from `admin` where username = #{username}")
    Admin selectByUsername(String username);

    // 查询所有管理员
    List<Admin> selectAll(Admin admin);

    // 添加管理员
    void insert(Admin admin);

    // 根据id修改管理员
    void updateById(Admin admin);

    // 根据id删除单个管理员
    @Delete("delete from `admin` where id = #{id}")
    void deleteById(Integer id);



}
