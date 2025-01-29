package com.example.controller;

import com.example.common.Result;
import com.example.entity.Search;
import com.example.service.SearchService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    //添加搜索信息
    @PostMapping("/add")// 通过POST请求接收一个Search对象，将其添加到系统中
    public Result add(@RequestBody Search search){
        searchService.add(search);// 调用SearchService的add方法来添加搜索信息
        return Result.success();// 返回成功结果，表示搜索信息添加成功
    }

   

    //删除单个搜索信息
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        searchService.deleteById(id);
        return Result.success();
    }

    //批量删除搜索信息
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        searchService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有搜索信息
    @GetMapping("/selectAll")
    public Result selectAll(Search search){
        List<Search> list = searchService.selectAll(search);
        return Result.success(list);
    }

    //根据id查询搜索信息
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Search search = searchService.selectById(id);
        return Result.success(search);
    }

    //分页查询搜索信息
    @GetMapping("/selectPage")
    public Result selectPage(Search search,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Search> pageInfo  = searchService.selectPage(search, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的搜索信息信息列表
        return Result.success(pageInfo);
    }

}
