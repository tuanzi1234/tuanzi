package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;// 注入CategoryMapper，用于数据库操作

    // 查询所有分类
    public List<Category> selectAll(Category category) {
        return categoryMapper.selectAll(category);
    }

    // 根据id查询分类
    public Category selectById(Integer id) {
        return categoryMapper.selectById(id);
    }

    // 分页查询分类
    public PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<Category> list = categoryMapper.selectAll(category); // 执行查询，根据传入的查询条件获取分类列表
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加分类
    public void add(Category category) {
        categoryMapper.insert(category);// 调用mapper层添加分类
    }

    // 根据id更新分类信息
    public void updateById(Category category) {
        categoryMapper.updateById(category);
    }

    // 根据id删除单个分类
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }


    // 批量删除分类
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            categoryMapper.deleteById(id);
        }
    }


}
