package com.example.mapper;

import com.example.entity.Notice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NoticeMapper {

    // 根据id查询系统公告
    @Select("select * from `notice` where id = #{id}")
    Notice selectById(Integer id);

    // 查询所有系统公告
    List<Notice> selectAll(Notice notice);

    // 添加系统公告
    void insert(Notice notice);

    // 根据id修改系统公告
    void updateById(Notice notice);

    // 根据id删除单个系统公告
    @Delete("delete from `notice` where id = #{id}")
    void deleteById(Integer id);



}
