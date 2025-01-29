package com.example.controller;

import com.example.common.Result;
import com.example.entity.OrdersItem;
import com.example.service.OrdersItemService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordersItem")
public class OrdersItemController {

    @Resource
    private OrdersItemService ordersItemService;

    //添加订单详细信息
    @PostMapping("/add")// 通过POST请求接收一个OrdersItem对象，将其添加到系统中
    public Result add(@RequestBody OrdersItem ordersItem){
        ordersItemService.add(ordersItem);// 调用OrdersItemService的add方法来添加订单详细信息
        return Result.success();// 返回成功结果，表示订单详细信息添加成功
    }

    //修改订单详细信息
    @PutMapping("/update")
    public Result update(@RequestBody OrdersItem ordersItem){
        ordersItemService.updateById(ordersItem);
        return Result.success();
    }

    //删除单个订单详细信息
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        ordersItemService.deleteById(id);
        return Result.success();
    }

    //批量删除订单详细信息
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        ordersItemService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有订单详细信息
    @GetMapping("/selectAll")
    public Result selectAll(OrdersItem ordersItem){
        List<OrdersItem> list = ordersItemService.selectAll(ordersItem);
        return Result.success(list);
    }

    //根据id查询订单详细信息
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        OrdersItem ordersItem = ordersItemService.selectById(id);
        return Result.success(ordersItem);
    }

    //分页查询订单详细信息
    @GetMapping("/selectPage")
    public Result selectPage(OrdersItem ordersItem,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<OrdersItem> pageInfo  = ordersItemService.selectPage(ordersItem, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的订单详细信息信息列表
        return Result.success(pageInfo);
    }

}
