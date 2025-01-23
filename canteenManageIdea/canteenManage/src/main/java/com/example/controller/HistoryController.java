package com.example.controller;

import com.example.common.Result;
import com.example.entity.History;
import com.example.service.HistoryService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Resource
    private HistoryService historyService;

    //添加浏览记录
    @PostMapping("/add")// 通过POST请求接收一个History对象，将其添加到系统中
    public Result add(@RequestBody History history){
        historyService.add(history);// 调用HistoryService的add方法来添加浏览记录
        return Result.success();// 返回成功结果，表示浏览记录添加成功
    }

    //修改浏览记录
    @PutMapping("/update")
    public Result update(@RequestBody History history){
        historyService.updateById(history);
        return Result.success();
    }

    //删除单个浏览记录
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        historyService.deleteById(id);
        return Result.success();
    }

    //批量删除浏览记录
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        historyService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有浏览记录
    @GetMapping("/selectAll")
    public Result selectAll(History history){
        List<History> list = historyService.selectAll(history);
        return Result.success(list);
    }

    //根据id查询浏览记录
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        History history = historyService.selectById(id);
        return Result.success(history);
    }

    //分页查询浏览记录
    @GetMapping("/selectPage")
    public Result selectPage(History history,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<History> pageInfo  = historyService.selectPage(history, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的浏览记录信息列表
        return Result.success(pageInfo);
    }

}
