package com.example.controller;

import com.example.common.Result;
import com.example.entity.Sideshow;
import com.example.service.SideshowService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sideshow")
public class SideshowController {

    @Resource
    private SideshowService sideshowService;

    //添加轮播图
    @PostMapping("/add")// 通过POST请求接收一个Sideshow对象，将其添加到系统中
    public Result add(@RequestBody Sideshow sideshow){
        sideshowService.add(sideshow);// 调用SideshowService的add方法来添加轮播图
        return Result.success();// 返回成功结果，表示轮播图添加成功
    }

    //修改轮播图
    @PutMapping("/update")
    public Result update(@RequestBody Sideshow sideshow){
        sideshowService.updateById(sideshow);
        return Result.success();
    }

    //删除单个轮播图
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        sideshowService.deleteById(id);
        return Result.success();
    }

    //批量删除轮播图
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        sideshowService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有轮播图
    @GetMapping("/selectAll")
    public Result selectAll(Sideshow sideshow){
        List<Sideshow> list = sideshowService.selectAll(sideshow);
        return Result.success(list);
    }

    //根据id查询轮播图
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Sideshow sideshow = sideshowService.selectById(id);
        return Result.success(sideshow);
    }

    //分页查询轮播图
    @GetMapping("/selectPage")
    public Result selectPage(Sideshow sideshow,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Sideshow> pageInfo  = sideshowService.selectPage(sideshow, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的轮播图信息列表
        return Result.success(pageInfo);
    }

}
