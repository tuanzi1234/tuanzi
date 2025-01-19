package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.service.AdminService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    //添加管理员
    @PostMapping("/add")// 通过POST请求接收一个Admin对象，将其添加到系统中
    public Result add(@RequestBody Admin admin){
        adminService.add(admin);// 调用AdminService的add方法来添加管理员
        return Result.success();// 返回成功结果，表示管理员添加成功
    }

    //修改管理员
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin){
        adminService.updateById(admin);
        return Result.success();
    }

    //删除单个管理员
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();
    }

    //批量删除管理员
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        adminService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有管理员
    @GetMapping("/selectAll")
    public Result selectAll(Admin admin){
        List<Admin> list = adminService.selectAll(admin);
        return Result.success(list);
    }

    //根据id查询管理员
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Admin admin = adminService.selectById(id);
        return Result.success(admin);
    }

    //分页查询管理员
    @GetMapping("/selectPage")
    public Result selectPage(Admin admin,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Admin> pageInfo  = adminService.selectPage(admin, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的管理员信息列表
        return Result.success(pageInfo);
    }

}
