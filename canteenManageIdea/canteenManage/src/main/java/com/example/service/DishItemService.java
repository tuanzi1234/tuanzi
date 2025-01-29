package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Dish;
import com.example.entity.DishItem;
import com.example.mapper.DishItemMapper;
import com.example.mapper.DishMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishItemService {

    @Resource
    private DishItemMapper dishItemMapper;// 注入DishItemMapper，用于数据库操作
    @Resource
    private DishMapper dishMapper;

    // 查询所有点单信息
    public List<DishItem> selectAll(DishItem dishItem) {
       List<DishItem> list = dishItemMapper.selectAll(dishItem);
       for (DishItem item : list) {
           Dish dish = dishMapper.selectById(item.getDishId());
           if (ObjectUtil.isNotNull(dish)) {
               item.setDish(dish);
           }
       }
       return list;
    }

    // 根据id查询点单信息
    public DishItem selectById(Integer id) {
        return dishItemMapper.selectById(id);
    }

    // 分页查询点单信息
    public PageInfo<DishItem> selectPage(DishItem dishItem, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<DishItem> list = this.selectAll(dishItem); // 执行查询，根据传入的查询条件获取点单信息列表
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加点单信息
    public void add(DishItem dishItem) {
        //点单前，查询数据库中是否有该菜品
        List<DishItem> list = dishItemMapper.selectAll(dishItem);
        if (CollectionUtil.isNotEmpty(list)) {
            //如果有，则将点单数量加1
            DishItem dbDishItem = list.get(0);
            dbDishItem.setNum(dbDishItem.getNum() + dishItem.getNum());
            dishItemMapper.updateById(dbDishItem);
        }else {
            //如果没有，则添加新的点单信息
            dishItemMapper.insert(dishItem);// 调用mapper层添加点单信息
        }
    }

    // 根据id更新点单信息信息
    public void updateById(DishItem dishItem) {
        dishItemMapper.updateById(dishItem);
    }




    // 根据id删除单个点单信息
    public void deleteById(Integer id) {

        dishItemMapper.deleteById(id);
    }

    // 批量删除点单信息
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            dishItemMapper.deleteById(id);
        }
    }

}
