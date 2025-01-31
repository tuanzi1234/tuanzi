package com.example.mapper;

import com.example.entity.Warehouse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WarehouseMapper {

    // 根据id查询库存
    @Select("select * from `warehouse` where id = #{id}")
    Warehouse selectById(Integer id);

    // 查询所有库存
    List<Warehouse> selectAll(Warehouse warehouse);

    // 添加库存
    void insert(Warehouse warehouse);

    // 根据id修改库存
    void updateById(Warehouse warehouse);

    // 根据id删除单个库存
    @Delete("delete from `warehouse` where id = #{id}")
    void deleteById(Integer id);



}
