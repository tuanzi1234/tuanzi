package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Dish;
import com.example.entity.DishItem;
import com.example.entity.OrdersItem;
import com.example.mapper.DishMapper;
import com.example.mapper.OrdersItemMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersItemService {

    @Resource
    private OrdersItemMapper ordersItemMapper;// 注入OrdersItemMapper，用于数据库操作
    @Resource
    private DishMapper dishMapper;

    // 查询所有订单详细信息
    public List<OrdersItem> selectAll(OrdersItem ordersItem) {
        List<OrdersItem> list = ordersItemMapper.selectAll(ordersItem);
        for (OrdersItem item : list) {
            Dish dish = dishMapper.selectById(item.getDishId());
            if (ObjectUtil.isNotNull(dish)) {
                item.setDish(dish);
            }
        }
        return list;
    }

    // 根据id查询订单详细信息
    public OrdersItem selectById(Integer id) {
        return ordersItemMapper.selectById(id);
    }

    // 分页查询订单详细信息
    public PageInfo<OrdersItem> selectPage(OrdersItem ordersItem, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<OrdersItem> list = ordersItemMapper.selectAll(ordersItem); // 执行查询，根据传入的查询条件获取订单详细信息列表
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加订单详细信息
    public void add(OrdersItem ordersItem) {
        ordersItemMapper.insert(ordersItem);// 调用mapper层添加订单详细信息
    }

    // 根据id更新订单详细信息
    public void updateById(OrdersItem ordersItem) {
        ordersItemMapper.updateById(ordersItem);
    }

    // 根据id删除单个订单详细信息
    public void deleteById(Integer id) {
        ordersItemMapper.deleteById(id);
    }


    // 批量删除订单详细信息
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            ordersItemMapper.deleteById(id);
        }
    }


}
