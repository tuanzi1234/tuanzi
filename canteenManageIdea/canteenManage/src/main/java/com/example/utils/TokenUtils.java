package com.example.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.common.Constants;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.service.AdminService;
import com.example.service.StudentService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;



// 生成token的工具类
@Component
public class TokenUtils {

    // 创建日志记录器，用于记录日志
    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    @Resource
    private AdminService adminService;
    @Resource
    private StudentService studentService;


    // 静态变量，用于存储AdminService实例
    private static AdminService staticAdminService;
    // 静态变量，用于存储StudentService实例
    private static StudentService staticStudentService;


    // 初始化方法，在Bean初始化时调用
    @PostConstruct
    public void init() {
        // 将AdminService实例赋值给静态变量
        staticAdminService = adminService;
        // 将StudentService实例赋值给静态变量
        staticStudentService = studentService;
    }

    // 创建JWT token
    public static String createToken(String data, String sign) {
        // audience存储数据的媒介 储存Id和用户角色
        return JWT.create().withAudience(data)
                // 设置过期时间为1天后
                .withExpiresAt(DateUtil.offsetDay(new Date(), 1))
                // 使用HMAC256算法进行签名
                .sign(Algorithm.HMAC256(sign));
    }

    // 获取当前登录的用户
    public static Account getCurrentAccount() {
        try {
            // 获取当前请求对象
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 从请求头中获取token
            String token = request.getHeader(Constants.TOKEN);
            // 解析token，获取audience
            String audience = JWT.decode(token).getAudience().get(0);
            // 从audience中解析出用户ID和角色
            String[] userRole = audience.split("-");
            Integer userId = Integer.valueOf(userRole[0]);
            String role = userRole[1];
            // 根据角色判断用户属于哪个表，并查询用户数据
            if (role.equals(RoleEnum.ADMIN.name())) {
                return staticAdminService.selectById(userId);
            }
            if (role.equals(RoleEnum.STUDENT.name())) {
                return staticStudentService.selectById(userId);
            }
        } catch (Exception e) {
            // 记录获取当前登录用户信息失败的错误日志
            log.error("获取当前登录用户信息失败", e);
        }
        return null;
    }
}
