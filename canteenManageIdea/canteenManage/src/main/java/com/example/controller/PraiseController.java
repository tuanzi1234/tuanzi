package com.example.controller;

import com.example.common.Result;
import com.example.entity.Praise;
import com.example.service.PraiseService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/praise")
public class PraiseController {

    @Resource
    private PraiseService praiseService;

    //添加点赞记录
    @PostMapping("/add")// 通过POST请求接收一个Praise对象，将其添加到系统中
    public Result add(@RequestBody Praise praise){
        praiseService.add(praise);// 调用PraiseService的add方法来添加点赞记录
        return Result.success();// 返回成功结果，表示点赞记录添加成功
    }



    //删除单个点赞记录
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        praiseService.deleteById(id);
        return Result.success();
    }

    //批量删除点赞记录
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        praiseService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有点赞记录
    @GetMapping("/selectAll")
    public Result selectAll(Praise praise){
        List<Praise> list = praiseService.selectAll(praise);
        return Result.success(list);
    }

    //根据id查询点赞记录
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Praise praise = praiseService.selectById(id);
        return Result.success(praise);
    }

    //分页查询点赞记录
    @GetMapping("/selectPage")
    public Result selectPage(Praise praise,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Praise> pageInfo  = praiseService.selectPage(praise, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的点赞记录信息列表
        return Result.success(pageInfo);
    }

}
