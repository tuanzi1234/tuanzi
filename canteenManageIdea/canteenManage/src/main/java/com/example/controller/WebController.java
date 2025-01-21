package com.example.controller;

import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.service.AdminService;
import com.example.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    @Resource
    private AdminService adminService;
    @Resource
    private StudentService studentService;

    // 测试接口
    @GetMapping("/")
    public Result test() {
        return Result.success();
    }

    // 登录接口
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        Account loginAccount = null;
        if(RoleEnum.ADMIN.name().equals(account.getRole())){
            loginAccount = adminService.login(account);
        }
        else if(RoleEnum.STUDENT.name().equals(account.getRole())){
            loginAccount = studentService.login(account);
        }
     
        return Result.success(loginAccount);
    }

   //注册接口
    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        studentService.register(account);
        return Result.success();
    }

    //修改密码
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        }
        if (RoleEnum.STUDENT.name().equals(account.getRole())) {
            studentService.updatePassword(account);
        }
        return Result.success();
    }

    //重置密码
    @PutMapping("/resetPassword")
    public Result resetPassword(@RequestBody Account account) {
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.resetPassword(account);
        }
        if (RoleEnum.STUDENT.name().equals(account.getRole())) {
            studentService.resetPassword(account);
        }
        return Result.success();
    }

}
