package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Dish;
import com.example.entity.Praise;
import com.example.mapper.DishMapper;
import com.example.mapper.PraiseMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PraiseService {

    @Resource
    private PraiseMapper praiseMapper;// 注入PraiseMapper，用于数据库操作
    @Resource
    private DishMapper dishMapper;// 注入DishMapper，用于数据库操作

    // 查询所有点赞记录
    public List<Praise> selectAll(Praise praise) {
        return praiseMapper.selectAll(praise);
    }

    // 根据id查询点赞记录
    public Praise selectById(Integer id) {
        return praiseMapper.selectById(id);
    }

    // 分页查询点赞记录
    public PageInfo<Praise> selectPage(Praise praise, Integer pageNum, Integer pageSize) {
        Account currentAccount = TokenUtils.getCurrentAccount();
        if(RoleEnum.STUDENT.name().equals(currentAccount.getRole())){
            praise.setStudentId(currentAccount.getId());
        }
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<Praise> list = praiseMapper.selectAll(praise); // 执行查询，根据传入的查询条件获取点赞记录列表
        //遍历前端需要的菜品信息
        for (Praise dbPraise : list) {
            Integer dishId = dbPraise.getDishId();
            Dish dish =  dishMapper.selectById(dishId);
            dbPraise.setDish(dish);
        }
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }




    // 根据id删除单个点赞记录
    public void deleteById(Integer id) {
        praiseMapper.deleteById(id);
    }


    // 批量删除点赞记录
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            praiseMapper.deleteById(id);
        }
    }


    // 添加点赞记录
    public void add(Praise praise) {
        Account currentAccount = TokenUtils.getCurrentAccount();
        praise.setStudentId(currentAccount.getId());
        // 查询该学生有没有点赞过该菜品
        List<Praise> praises = praiseMapper.selectAll(praise);
        if(CollectionUtil.isNotEmpty(praises)){
            // 已经点赞过了,删除记录
            Praise dbPraise = praises.get(0);
            deleteById(dbPraise.getId());
        }else{
            // 没有点赞过，添加记录
            praiseMapper.insert(praise);
        }

    }
}
