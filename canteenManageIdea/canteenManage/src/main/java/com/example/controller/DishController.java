package com.example.controller;

import com.example.common.Result;
import com.example.entity.Dish;
import com.example.service.DishService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Resource
    private DishService dishService;

    //添加菜品
    @PostMapping("/add")// 通过POST请求接收一个Dish对象，将其添加到系统中
    public Result add(@RequestBody Dish dish){
        dishService.add(dish);// 调用DishService的add方法来添加菜品
        return Result.success();// 返回成功结果，表示菜品添加成功
    }

    //修改菜品
    @PutMapping("/update")
    public Result update(@RequestBody Dish dish){
        dishService.updateById(dish);
        return Result.success();
    }

    //删除单个菜品
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        dishService.deleteById(id);
        return Result.success();
    }

    //批量删除菜品
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        dishService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有菜品
    @GetMapping("/selectAll")
    public Result selectAll(Dish dish){
        List<Dish> list = dishService.selectAll(dish);
        return Result.success(list);
    }

    //根据id查询菜品
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Dish dish = dishService.selectById(id);
        return Result.success(dish);
    }

    //分页查询菜品
    @GetMapping("/selectPage")
    public Result selectPage(Dish dish,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Dish> pageInfo  = dishService.selectPage(dish, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的菜品信息列表
        return Result.success(pageInfo);
    }

    //根据销量查询菜品
    @GetMapping("/selectBySales")
    public Result selectBySales(){
        List<Dish> list = dishService.selectBySales();
        return Result.success(list);
    }

}
