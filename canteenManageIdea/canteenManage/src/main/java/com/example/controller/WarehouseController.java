package com.example.controller;

import com.example.common.Result;
import com.example.entity.Warehouse;
import com.example.service.WarehouseService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Resource
    private WarehouseService warehouseService;

    //添加库存信息
    @PostMapping("/add")// 通过POST请求接收一个Warehouse对象，将其添加到系统中
    public Result add(@RequestBody Warehouse warehouse){
        warehouseService.add(warehouse);// 调用WarehouseService的add方法来添加库存信息
        return Result.success();// 返回成功结果，表示库存信息添加成功
    }

    //修改库存信息
    @PutMapping("/update")
    public Result update(@RequestBody Warehouse warehouse){
        warehouseService.updateById(warehouse);
        return Result.success();
    }

    //删除单个库存信息
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        warehouseService.deleteById(id);
        return Result.success();
    }

    //批量删除库存信息
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        warehouseService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有库存信息
    @GetMapping("/selectAll")
    public Result selectAll(Warehouse warehouse){
        List<Warehouse> list = warehouseService.selectAll(warehouse);
        return Result.success(list);
    }

    //根据id查询库存信息
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Warehouse warehouse = warehouseService.selectById(id);
        return Result.success(warehouse);
    }

    //分页查询库存信息
    @GetMapping("/selectPage")
    public Result selectPage(Warehouse warehouse,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Warehouse> pageInfo  = warehouseService.selectPage(warehouse, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的库存信息信息列表
        return Result.success(pageInfo);
    }

}
