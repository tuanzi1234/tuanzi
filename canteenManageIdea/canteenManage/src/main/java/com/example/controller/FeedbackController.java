package com.example.controller;

import com.example.common.Result;
import com.example.entity.Feedback;
import com.example.service.FeedbackService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    //添加反馈意见
    @PostMapping("/add")// 通过POST请求接收一个Feedback对象，将其添加到系统中
    public Result add(@RequestBody Feedback feedback){
        feedbackService.add(feedback);// 调用FeedbackService的add方法来添加反馈意见
        return Result.success();// 返回成功结果，表示反馈意见添加成功
    }

    //修改反馈意见
    @PutMapping("/update")
    public Result update(@RequestBody Feedback feedback){
        feedbackService.updateById(feedback);
        return Result.success();
    }

    //删除单个反馈意见
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        feedbackService.deleteById(id);
        return Result.success();
    }

    //批量删除反馈意见
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        feedbackService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有反馈意见
    @GetMapping("/selectAll")
    public Result selectAll(Feedback feedback){
        List<Feedback> list = feedbackService.selectAll(feedback);
        return Result.success(list);
    }

    //根据id查询反馈意见
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Feedback feedback = feedbackService.selectById(id);
        return Result.success(feedback);
    }

    //分页查询反馈意见
    @GetMapping("/selectPage")
    public Result selectPage(Feedback feedback,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Feedback> pageInfo  = feedbackService.selectPage(feedback, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的反馈意见信息列表
        return Result.success(pageInfo);
    }

}
