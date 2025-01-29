package com.example.controller;

import com.example.common.Result;
import com.example.entity.DishItem;
import com.example.service.DishItemService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishItem")
public class DishItemController {

    @Resource
    private DishItemService dishItemService;


    //添加点单信息
    @PostMapping("/add")// 通过POST请求接收一个DishItem对象，将其添加到系统中
    public Result add(@RequestBody DishItem dishItem){
        dishItemService.add(dishItem);// 调用DishItemService的add方法来添加点单信息
        return Result.success();// 返回成功结果，表示点单信息添加成功
    }

    //修改点单信息
    @PutMapping("/update")
    public Result update(@RequestBody DishItem dishItem){
        dishItemService.updateById(dishItem);
        return Result.success();
    }

    //删除单个点单信息
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        dishItemService.deleteById(id);
        return Result.success();
    }

    //批量删除点单信息
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        dishItemService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有点单信息
    @GetMapping("/selectAll")
    public Result selectAll(DishItem dishItem){
        List<DishItem> list = dishItemService.selectAll(dishItem);
        return Result.success(list);
    }

    //根据id查询点单信息
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        DishItem dishItem = dishItemService.selectById(id);
        return Result.success(dishItem);
    }

    //分页查询点单信息
    @GetMapping("/selectPage")
    public Result selectPage(DishItem dishItem,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<DishItem> pageInfo  = dishItemService.selectPage(dishItem, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的点单信息信息列表
        return Result.success(pageInfo);
    }

}
