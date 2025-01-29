package com.example.controller;

import com.example.common.Result;
import com.example.entity.DishItem;
import com.example.entity.Orders;
import com.example.entity.Student;
import com.example.service.OrdersService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Resource
    private OrdersService ordersService;

    //添加订单信息
    @PostMapping("/add")// 通过POST请求接收一个Orders对象，将其添加到系统中
    public Result add(@RequestBody Orders orders){
        ordersService.add(orders);// 调用OrdersService的add方法来添加订单信息
        return Result.success();// 返回成功结果，表示订单信息添加成功
    }

    //修改订单信息
    @PutMapping("/update")
    public Result update(@RequestBody Orders orders){
        ordersService.updateById(orders);
        return Result.success();
    }

    //删除单个订单信息
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        ordersService.deleteById(id);
        return Result.success();
    }

    //批量删除订单信息
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        ordersService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有订单信息
    @GetMapping("/selectAll")
    public Result selectAll(Orders orders){
        List<Orders> list = ordersService.selectAll(orders);
        return Result.success(list);
    }

    //根据id查询订单信息
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Orders orders = ordersService.selectById(id);
        return Result.success(orders);
    }

    //分页查询订单信息
    @GetMapping("/selectPage")
    public Result selectPage(Orders orders,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Orders> pageInfo  = ordersService.selectPage(orders, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的订单信息信息列表
        return Result.success(pageInfo);
    }

    //下单
    @PostMapping("/create")
    public Result create(@RequestBody List<DishItem> dishes){// 接收一个DishItem对象列表,对象和前端的json数据保持一致
        ordersService.create(dishes);
        return Result.success();
    }

    //支付订单
    @PostMapping("/pay")// 通过POST请求接收一个Orders对象，将其添加到系统中
    public Result pay(@RequestBody Orders orders){
        Student student = ordersService.pay(orders);// 调用OrdersService的add方法来添加订单信息
        return Result.success(student);// 返回成功结果，表示订单信息添加成功
    }
}
