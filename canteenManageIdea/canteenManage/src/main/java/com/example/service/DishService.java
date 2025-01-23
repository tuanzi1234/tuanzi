package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Category;
import com.example.entity.Dish;
import com.example.mapper.CategoryMapper;
import com.example.mapper.DishMapper;
import com.example.mapper.HistoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishService {

    @Resource
    private DishMapper dishMapper;// 注入DishMapper，用于数据库操作

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private HistoryMapper historyMapper;

    // 查询所有菜品
    public List<Dish> selectAll(Dish dish) {
        return dishMapper.selectAll(dish);
    }

    // 根据id查询菜品
    public Dish selectById(Integer id) {
        Dish dish = dishMapper.selectById(id);
        Category category = categoryMapper.selectById(dish.getCategoryId());
        if (ObjectUtil.isNotNull(category)) {
            dish.setCategoryName(category.getTitle());
        }
        return dish;
    }

    //根据销量查询菜品
    public List<Dish> selectBySales() {
        return dishMapper.selectBySales();

    }

    // 分页查询菜品
    public PageInfo<Dish> selectPage(Dish dish, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<Dish> list = dishMapper.selectAll(dish); // 执行查询，根据传入的查询条件获取菜品列表
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加菜品
    public void add(Dish dish) {
        dish.setStatus("上架");// 设置菜品状态为上架
        dishMapper.insert(dish);// 调用mapper层添加菜品
    }

    // 根据id更新菜品信息
    public void updateById(Dish dish) {
        dishMapper.updateById(dish);
    }



    // 根据id删除单个菜品
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        dishMapper.deleteById(id);
        //删除浏览记录
        historyMapper.deleteByDishId(id);
    }


    // 批量删除菜品
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            dishMapper.deleteById(id);
            //删除浏览记录
            historyMapper.deleteByDishId(id);
        }
    }


}
