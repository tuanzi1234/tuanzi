package com.example.common.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private JWTInterceptor jwtInterceptor;

    /**
     * 配置拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加自定义的JWT拦截器，并指定拦截路径和排除路径
        registry.addInterceptor(jwtInterceptor)
                // 拦截所有路径
                .addPathPatterns("/**")
                // 排除根路径
                .excludePathPatterns("/")
                // 排除相关路径,实现游客登录
                .excludePathPatterns(
                        "/login",//登录
                        "/register",//注册
                        "/files/**",//文件上传
                        "/resetPassword",//重置密码
                        "/dish/selectPage",//分页查询菜品
                        "/sideshow/selectAll",//轮播图
                        "/category/selectAll",//分类
                        "/dish/selectBySales",//销量查询菜品
                        "/praise/selectAll",//点赞
                        "/collect/selectAll",//收藏
                        "/search/selectAll",//搜索记录
                        "/dish/selectById/**",//根据id查询菜品
                        "/history/add",//添加搜索记录
                        "/ordersItem/selectAll",//查询评论
                        "/information/selectPage",//分页查询资讯
                        "/information/selectById/**",//根据id查询资讯
                        "/information/update",//更新资讯浏览量
                        "/notice/selectAll",//系统公告
                        "/search/add");//添加搜索记录
    }
}
