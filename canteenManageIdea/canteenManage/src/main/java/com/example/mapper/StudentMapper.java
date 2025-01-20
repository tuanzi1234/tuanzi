package com.example.mapper;

import com.example.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapper {

    // 根据id查询学生
    @Select("select * from `student` where id = #{id} order by username ")
    Student selectById(Integer id);

    // 根据用户名查询学生
    @Select("select * from `student` where username = #{username} order by username ")
    Student selectByUsername(String username);

    // 查询所有学生
    List<Student> selectAll(Student student);

    // 添加学生
    void insert(Student student);

    // 根据id修改学生
    void updateById(Student student);

    // 根据id删除单个学生
    @Delete("delete from `student` where id = #{id}")
    void deleteById(Integer id);



}
