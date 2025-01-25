package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Dish;
import com.example.entity.Collect;
import com.example.mapper.DishMapper;
import com.example.mapper.CollectMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectService {

    @Resource
    private CollectMapper collectMapper;// 注入CollectMapper，用于数据库操作
    @Resource
    private DishMapper dishMapper;// 注入DishMapper，用于数据库操作

    // 查询所有收藏记录
    public List<Collect> selectAll(Collect collect) {
        return collectMapper.selectAll(collect);
    }

    // 根据id查询收藏记录
    public Collect selectById(Integer id) {
        return collectMapper.selectById(id);
    }

    // 分页查询收藏记录
    public PageInfo<Collect> selectPage(Collect collect, Integer pageNum, Integer pageSize) {
        Account currentAccount = TokenUtils.getCurrentAccount();
        if(RoleEnum.STUDENT.name().equals(currentAccount.getRole())){
            collect.setStudentId(currentAccount.getId());
        }
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<Collect> list = collectMapper.selectAll(collect); // 执行查询，根据传入的查询条件获取收藏记录列表
        //遍历前端需要的菜品信息
        for (Collect dbCollect : list) {
            Integer dishId = dbCollect.getDishId();
            Dish dish =  dishMapper.selectById(dishId);
            dbCollect.setDish(dish);
        }
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }




    // 根据id删除单个收藏记录
    public void deleteById(Integer id) {
        collectMapper.deleteById(id);
    }


    // 批量删除收藏记录
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            collectMapper.deleteById(id);
        }
    }


    // 添加收藏记录
    public void add(Collect collect) {
        Account currentAccount = TokenUtils.getCurrentAccount();
        collect.setStudentId(currentAccount.getId());
        // 查询该学生有没有收藏过该菜品
        List<Collect> collects = collectMapper.selectAll(collect);
        if(CollectionUtil.isNotEmpty(collects)){
            // 已经收藏过了,删除记录
            Collect dbCollect = collects.get(0);
            deleteById(dbCollect.getId());
        }else{
            // 没有收藏过，添加记录
            collectMapper.insert(collect);
        }

    }
}
