package com.example.mapper;

import com.example.entity.Orders;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrdersMapper {

    // 根据id查询订单信息
    @Select("select * from `orders` where id = #{id}")
    Orders selectById(Integer id);

    // 查询所有订单信息
    List<Orders> selectAll(Orders orders);

    // 添加订单信息
    int insert(Orders orders);

    // 根据id修改订单信息
    void updateById(Orders orders);

    // 根据id删除单个订单信息
    @Delete("delete from `orders` where id = #{id}")
    void deleteById(Integer id);



}
