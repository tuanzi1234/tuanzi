package com.example.controller;

import com.example.common.Result;
import com.example.entity.Category;
import com.example.service.CategoryService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    //添加分类
    @PostMapping("/add")// 通过POST请求接收一个Category对象，将其添加到系统中
    public Result add(@RequestBody Category category){
        categoryService.add(category);// 调用CategoryService的add方法来添加分类
        return Result.success();// 返回成功结果，表示分类添加成功
    }

    //修改分类
    @PutMapping("/update")
    public Result update(@RequestBody Category category){
        categoryService.updateById(category);
        return Result.success();
    }

    //删除单个分类
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }

    //批量删除分类
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        categoryService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有分类
    @GetMapping("/selectAll")
    public Result selectAll(Category category){
        List<Category> list = categoryService.selectAll(category);
        return Result.success(list);
    }

    //根据id查询分类
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Category category = categoryService.selectById(id);
        return Result.success(category);
    }

    //分页查询分类
    @GetMapping("/selectPage")
    public Result selectPage(Category category,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Category> pageInfo  = categoryService.selectPage(category, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的分类信息列表
        return Result.success(pageInfo);
    }

}
