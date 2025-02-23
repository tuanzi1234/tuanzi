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
        // 1. 用户存在性校验
        Student dbStudent = studentMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbStudent)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR); // 用户不存在时抛出业务异常
        }

        // 2. 密码安全校验
        if (!BCrypt.checkpw(account.getPassword(), dbStudent.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR); // 密码不匹配时抛出业务异常
        }

        // 3. Token生成与身份绑定
        String token = TokenUtils.createToken(
                dbStudent.getId() + "-" + dbStudent.getRole(), // 组合用户ID与角色作为身份标识
                dbStudent.getPassword()                        // 使用数据库存储的密码哈希作为签名密钥
        );
        dbStudent.setToken(token);  // 将生成的令牌绑定到用户对象

        return dbStudent;  // 返回包含令牌的完整用户信息
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

    /**
     * 用户注册时密码加密过程说明：
     * 1. 使用BCrypt强哈希算法进行加密
     * 2. BCrypt.gensalt() 生成随机的加密盐值，增强安全性
     * 3. BCrypt.hashpw() 将明文密码与盐值组合，进行多轮哈希计算（默认10轮）
     * 4. 生成的哈希值格式包含：算法版本号、计算成本、随机盐值、最终哈希值
     */
    public void register(Account account) {
        Student student = new Student();
        BeanUtils.copyProperties(account, student);
        // 加密密码
        student.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt()));
        add(student);
    }


    // 重置密码
    public void resetPassword(Account account) {
        Student dbStudent = studentMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbStudent)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if(!dbStudent.getPhone().equals(account.getPhone())){
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        // 生成新密码的哈希值
        String newPasswordHash = BCrypt.hashpw(Constants.USER_DEFAULT_PASSWORD, BCrypt.gensalt());
        dbStudent.setPassword(newPasswordHash);
        studentMapper.updateById(dbStudent);
    }
}

