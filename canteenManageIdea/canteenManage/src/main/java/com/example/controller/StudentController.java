package com.example.controller;

import com.example.common.Result;
import com.example.entity.Student;
import com.example.service.StudentService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    //添加学生
    @PostMapping("/add")// 通过POST请求接收一个Student对象，将其添加到系统中
    public Result add(@RequestBody Student student){
        studentService.add(student);// 调用StudentService的add方法来添加学生
        return Result.success();// 返回成功结果，表示学生添加成功
    }

    //修改学生
    @PutMapping("/update")
    public Result update(@RequestBody Student student){
        studentService.updateById(student);
        return Result.success();
    }

    //删除单个学生
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        studentService.deleteById(id);
        return Result.success();
    }

    //批量删除学生
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids){
        studentService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有学生
    @GetMapping("/selectAll")
    public Result selectAll(Student student){
        List<Student> list = studentService.selectAll(student);
        return Result.success(list);
    }

    //根据id查询学生
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Student student = studentService.selectById(id);
        return Result.success(student);
    }

    //分页查询学生
    @GetMapping("/selectPage")
    public Result selectPage(Student student,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Student> pageInfo  = studentService.selectPage(student, pageNum, pageSize);// 调用分页查询方法，获取指定页码和页大小的学生信息列表
        return Result.success(pageInfo);
    }

}
