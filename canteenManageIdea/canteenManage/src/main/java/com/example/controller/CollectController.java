package com.example.controller;

import com.example.common.Result;
import com.example.entity.Collect;
import com.example.service.CollectService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    private CollectService collectService;

    //添加收藏记录
    @PostMapping("/add")// 通过POST请求接收一个Collect对象，将其添加到系统中
    public Result add(@RequestBody Collect collect){
        collectService.add(collect);// 调用CollectService的add方法来添加收藏记录
        return Result.success();// 返回成功结果，表示收藏记录添加成功
    }



    //删除单个收藏记录
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        collectService.deleteById(id);
        return Result.success();
    }

    //批量删除收藏记录
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        collectService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有收藏记录
    @GetMapping("/selectAll")
    public Result selectAll(Collect collect){
        List<Collect> list = collectService.selectAll(collect);
        return Result.success(list);
    }

    //根据id查询收藏记录
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Collect collect = collectService.selectById(id);
        return Result.success(collect);
    }

    //分页查询收藏记录
    @GetMapping("/selectPage")
    public Result selectPage(Collect collect,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Collect> pageInfo  = collectService.selectPage(collect, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的收藏记录信息列表
        return Result.success(pageInfo);
    }

}
