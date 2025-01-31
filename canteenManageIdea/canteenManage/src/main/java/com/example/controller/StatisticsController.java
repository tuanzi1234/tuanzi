package com.example.controller;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.*;
import com.example.service.*;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    private DishService dishService;
    @Resource
    private OrdersService ordersService;
    @Resource
    private InformationService informationService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private OrdersItemService ordersItemService;

    // 统计数据
    @GetMapping("/base")
    public Result base() {
        // 查询所有订单，过滤掉待支付状态的订单，并计算订单数量和总金额
        List<Orders> orders = ordersService.selectAll(new Orders())
                .stream()
                .filter(x -> !"待支付".equals(x.getStatus()))
                .collect(Collectors.toList());

        // 创建一个HashMap用于存储统计信息
        Map<String, Object> map = new HashMap<>();

        // 放入菜品总数
        map.put("dishNum", dishService.selectAll(new Dish()).size());

        // 放入订单总数
        map.put("orderNum", orders.size());

        // 放入订单总金额
        map.put("orderTotal", orders.stream().mapToDouble(Orders::getPrice).sum());

        // 放入信息总数
        map.put("InfoNum", informationService.selectAll(new Information()).size());

        // 返回包含统计信息的成功结果
        return Result.success(map);
    }

    //平滑折线图:近一周销售额趋势图
    @GetMapping("/line")
    public Result line() {
        // 获取最近7天的日期数据（年-月-日）放在xList里
        Date today = new Date();
        DateTime start = DateUtil.offsetDay(today, -7);
        List<String> xList = DateUtil.rangeToList(start, today, DateField.DAY_OF_YEAR).stream().map(DateUtil::formatDate).toList();

        // 初始化y轴数据列表，用于存放每天的销售额
        List<Double> yList = new ArrayList<>();

        // 查询数据库中所有已支付的订单
        List<Orders> orders = ordersService.selectAll(new Orders()).stream().filter(x ->!"待支付".equals(x.getStatus())).collect(Collectors.toList());;

        // 遍历日期数据，查询当天的销售额，放在yList里
        for(String day : xList){
            double sum = orders.stream().filter(x -> x.getCreateTime().contains(day)).mapToDouble(Orders::getPrice).sum();
            yList.add(sum);
        }

        // 创建一个映射，用于存储x轴和y轴的数据
        Map<String, Object> map = new HashMap<>();
        map.put("xList", xList);
        map.put("yList", yList);

        // 返回包含数据的Result对象
        return Result.success(map);
    }

    //饼图1:不同分类下的菜品库存饼状图
    @GetMapping("/pie1")
    public Result pie1() {

        // 初始化一个列表，用于存储每个类别及其对应菜肴数量的信息
        List<Map<String, Object>> list = new ArrayList<>();

        // 获取所有类别
        List<Category> categories = categoryService.selectAll(new Category());

        // 获取所有菜肴
        List<Dish> dishes = dishService.selectAll(new Dish());

        // 遍历每个类别
        for (Category category : categories) {
            // 创建一个映射，用于存储当前类别的名称和其对应的菜肴数量
            Map<String, Object> map = new HashMap<>();

            // 将类别名称放入映射
            map.put("name", category.getTitle());

            // 计算当前类别下的菜肴数量，并将结果放入映射
            map.put("value", dishes.stream().filter(x -> x.getCategoryId().equals(category.getId())).count());

            // 将当前类别的信息添加到列表中
            list.add(map);
        }

        // 返回包含所有类别及其菜肴数量的列表
        return Result.success(list);
    }

    // 饼图2：正在点餐的菜品统计占比饼状图
    @GetMapping("/pie2")
    public Result pie2() {

        // 初始化一个空的列表，用于存储最终处理后的结果
        List<Map<String, Object>> list = new ArrayList<>();

        // 获取所有菜品项，这里使用空的DishItem对象作为参数调用selectAll方法，可能为了获取默认查询条件下的所有菜品项
        List<OrdersItem> ordersItems = ordersItemService.selectAll(new OrdersItem());

        // 使用Stream API对菜品项进行分组和求和，结果存储在Map中，键为菜品ID，值为该菜品的总数量
        Map<Integer, Integer> collect = ordersItems.stream().collect(Collectors.groupingBy(OrdersItem::getDishId, Collectors.reducing(0, OrdersItem::getNum, Integer::sum)));

        // 遍历分组后的Map，处理每个菜品的总数量
        for (Integer dishId : collect.keySet()) {
            // 初始化一个HashMap，用于存储菜品名称和对应的总数量
            Map<String, Object> map = new HashMap<>();

            // 根据菜品ID查询菜品信息
            Dish dish = dishService.selectById(dishId);

            // 检查查询到的菜品是否不为空
            if (ObjectUtil.isNotEmpty(dish)) {
                // 将菜品名称和总数量放入Map中
                map.put("name", dish.getName());
                map.put("value", collect.get(dishId));

                // 将处理后的Map添加到结果列表中
                list.add(map);
            }
        }

        // 返回成功结果，包含处理后的列表
        return Result.success(list);
    }
}
