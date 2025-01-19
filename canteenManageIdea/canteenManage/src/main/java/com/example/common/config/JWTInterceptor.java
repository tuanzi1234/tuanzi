package com.example.common.config;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import com.example.service.StudentService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


//JWT拦截器
@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Resource
    private AdminService adminService;
    @Resource
    private StudentService studentService;


    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request  当前的HTTP请求
     * @param response 当前的HTTP响应
     * @param handler  被调用的处理器对象，本质上是一个方法对象，对反射技术中的Method对象进行了再包装
     * @return 如果返回true，则继续执行后续的拦截器和处理器；如果返回false，则中断执行
     * @throws Exception 如果在处理过程中发生异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从HTTP请求头中获取token
        String token = request.getHeader(Constants.TOKEN);
        // 如果请求头中没有token，则从请求参数中获取
        if (ObjectUtil.isNull(token)) {
            token = request.getParameter(Constants.TOKEN);
        }
        // 如果还是没有token，则抛出异常
        if (ObjectUtil.isNull(token)) {
            throw new CustomException(ResultCodeEnum.TOKEN_INVALID_ERROR);
        }
        Account account = null;
        try {
            // 解析token，获取audience
            String audience = JWT.decode(token).getAudience().get(0);
            // 从audience中解析出用户ID和角色
            String userId = audience.split("-")[0];
            String role = audience.split("-")[1];
            // 根据角色判断用户属于哪个表，并查询用户数据
            if (RoleEnum.ADMIN.name().equals(role)) {
                account = adminService.selectById(Integer.valueOf(userId));
            }
            if (RoleEnum.STUDENT.name().equals(role)) {
                account = studentService.selectById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            // 如果解析token失败，则抛出异常
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
        // 如果用户不存在，则抛出"用户不存在"异常
        if (ObjectUtil.isNull(account)) {
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
        try {
            // 通过用户密码，再次验证token合法性
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(account.getPassword())).build();
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            // 如果验证失败，则抛出异常
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
        return true;
    }
}
