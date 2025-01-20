package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Student;
import com.example.exception.CustomException;
import com.example.mapper.StudentMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Resource
    private StudentMapper studentMapper;// 注入StudentMapper，用于数据库操作

    // 查询所有学生
    public List<Student> selectAll(Student student) {
        return studentMapper.selectAll(student);
    }

    // 根据id查询学生
    public Student selectById(Integer id) {
        return studentMapper.selectById(id);
    }

    // 分页查询学生
    public PageInfo<Student> selectPage(Student student, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<Student> list = studentMapper.selectAll(student); // 执行查询，根据传入的查询条件获取学生列表
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加学生
    public void add(Student student) {
        Student dbStudent = studentMapper.selectByUsername(student.getUsername());
        if (ObjectUtil.isNotNull(dbStudent)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);// 如果用户名已存在，则抛出"用户已存在"的异常
        }
        if (ObjectUtil.isEmpty(student.getPassword())) {
            student.setPassword(BCrypt.hashpw(Constants.USER_DEFAULT_PASSWORD, BCrypt.gensalt()));// 如果密码为空，则设置默认密码为"123456"
        }
        if (ObjectUtil.isEmpty(student.getName())) {
            student.setName(student.getUsername());// 如果姓名为空，则设置默认姓名为用户名
        }
        student.setRole(RoleEnum.STUDENT.name());// 设置学生角色为"STUDENT"
        studentMapper.insert(student);// 调用mapper层添加学生
    }

    // 根据id更新学生信息
    public void updateById(Student student) {
        studentMapper.updateById(student);
    }

    // 根据id删除单个学生
    public void deleteById(Integer id) {
        studentMapper.deleteById(id);
    }


    // 批量删除学生
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            studentMapper.deleteById(id);
        }
    }

    // 登录
    public Student login(Account account) {
        Student dbStudent = studentMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbStudent)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!BCrypt.checkpw(account.getPassword(), dbStudent.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token

        String token = TokenUtils.createToken(dbStudent.getId() + "-" + dbStudent.getRole(), dbStudent.getPassword());
        dbStudent.setToken(token);
        // 登录成功
        return dbStudent;
    }

    // 修改密码
    public void updatePassword(Account account) {
        Student dbStudent = studentMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbStudent)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!BCrypt.checkpw(account.getPassword(), dbStudent.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        // 生成新密码的哈希值
        String newPasswordHash = BCrypt.hashpw(account.getNewPassword(), BCrypt.gensalt());
        dbStudent.setPassword(newPasswordHash);
        studentMapper.updateById(dbStudent);
    }

    // 注册
    public void register(Account account) {
        Student student = new Student();
        BeanUtils.copyProperties(account, student);
        // 加密密码
        student.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt()));
        add(student);
    }
}

