package com.example.mapper;

import com.example.entity.OrdersItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrdersItemMapper {

    // 根据id查询订单信息
    @Select("select * from `orders_item` where id = #{id}")
    OrdersItem selectById(Integer id);

    // 查询所有订单信息
    List<OrdersItem> selectAll(OrdersItem ordersItem);

    // 添加订单信息
    void insert(OrdersItem ordersItem);

    // 根据id修改订单信息
    void updateById(OrdersItem ordersItem);

    // 根据id删除单个订单信息
    @Delete("delete from `orders_item` where id = #{id}")
    void deleteById(Integer id);

    // 根据订单id删除订单信息
    @Delete("delete from `orders_item` where order_id = #{orderId}")
    void deleteByOrderId(Integer orderId);

}
