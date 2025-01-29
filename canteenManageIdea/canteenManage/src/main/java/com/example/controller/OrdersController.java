package com.example.controller;

import com.example.common.Result;
import com.example.entity.Notice;
import com.example.service.NoticeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    //添加系统公告
    @PostMapping("/add")// 通过POST请求接收一个Notice对象，将其添加到系统中
    public Result add(@RequestBody Notice notice){
        noticeService.add(notice);// 调用NoticeService的add方法来添加系统公告
        return Result.success();// 返回成功结果，表示系统公告添加成功
    }

    //修改系统公告
    @PutMapping("/update")
    public Result update(@RequestBody Notice notice){
        noticeService.updateById(notice);
        return Result.success();
    }

    //删除单个系统公告
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        noticeService.deleteById(id);
        return Result.success();
    }

    //批量删除系统公告
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        noticeService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有系统公告
    @GetMapping("/selectAll")
    public Result selectAll(Notice notice){
        List<Notice> list = noticeService.selectAll(notice);
        return Result.success(list);
    }

    //根据id查询系统公告
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Notice notice = noticeService.selectById(id);
        return Result.success(notice);
    }

    //分页查询系统公告
    @GetMapping("/selectPage")
    public Result selectPage(Notice notice,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Notice> pageInfo  = noticeService.selectPage(notice, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的系统公告信息列表
        return Result.success(pageInfo);
    }

}
