package com.example.controller;

import com.example.common.Result;
import com.example.entity.Information;
import com.example.service.InformationService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/information")
public class InformationController {

    @Resource
    private InformationService informationService;

    //添加食堂资讯
    @PostMapping("/add")// 通过POST请求接收一个Information对象，将其添加到系统中
    public Result add(@RequestBody Information information){
        informationService.add(information);// 调用InformationService的add方法来添加食堂资讯
        return Result.success();// 返回成功结果，表示食堂资讯添加成功
    }

    //修改食堂资讯
    @PutMapping("/update")
    public Result update(@RequestBody Information information){
        informationService.updateById(information);
        return Result.success();
    }

    //删除单个食堂资讯
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        informationService.deleteById(id);
        return Result.success();
    }

    //批量删除食堂资讯
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        informationService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有食堂资讯
    @GetMapping("/selectAll")
    public Result selectAll(Information information){
        List<Information> list = informationService.selectAll(information);
        return Result.success(list);
    }

    //根据id查询食堂资讯
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Information information = informationService.selectById(id);
        return Result.success(information);
    }

    //分页查询食堂资讯
    @GetMapping("/selectPage")
    public Result selectPage(Information information,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Information> pageInfo  = informationService.selectPage(information, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的食堂资讯信息列表
        return Result.success(pageInfo);
    }

}
