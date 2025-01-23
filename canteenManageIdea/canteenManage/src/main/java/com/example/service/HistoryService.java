package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Dish;
import com.example.entity.History;
import com.example.mapper.DishMapper;
import com.example.mapper.HistoryMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    @Resource
    private HistoryMapper historyMapper;// 注入HistoryMapper，用于数据库操作
    @Resource
    private DishMapper dishMapper;// 注入DishMapper，用于数据库操作

    // 查询所有浏览记录
    public List<History> selectAll(History history) {
        return historyMapper.selectAll(history);
    }

    // 根据id查询浏览记录
    public History selectById(Integer id) {
        return historyMapper.selectById(id);
    }

    // 分页查询浏览记录
    public PageInfo<History> selectPage(History history, Integer pageNum, Integer pageSize) {
        Account currentAccount = TokenUtils.getCurrentAccount();
        if(RoleEnum.STUDENT.name().equals(currentAccount.getRole())){
            history.setStudentId(currentAccount.getId());
        }
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<History> list = historyMapper.selectAll(history); // 执行查询，根据传入的查询条件获取浏览记录列表
        //遍历前端需要的菜品信息
        for (History dbHistory : list) {
            Integer dishId = dbHistory.getDishId();
            Dish dish =  dishMapper.selectById(dishId);
            dbHistory.setDish(dish);
        }
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加浏览记录
    public void add(History history) {
        Account currentAccount = TokenUtils.getCurrentAccount();
        history.setStudentId(currentAccount.getId());
        // 查询该学生有没有浏览过该菜品


        List<History> histories = historyMapper.selectAll(history);
        if (CollectionUtil.isNotEmpty(histories)) {
            //如果有，则更新最新时间
            History dbHistory = histories.get(0);
            dbHistory.setTime(DateUtil.now());
            historyMapper.updateById(dbHistory);
        }else {
            //如果没有，则添加
            history.setTime(DateUtil.now());// 设置公告发布时间
            historyMapper.insert(history);// 调用mapper层添加浏览记录
        }

    }

    // 根据id更新浏览记录信息
    public void updateById(History history) {
        historyMapper.updateById(history);
    }

    // 根据id删除单个浏览记录
    public void deleteById(Integer id) {
        historyMapper.deleteById(id);
    }


    // 批量删除浏览记录
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            historyMapper.deleteById(id);
        }
    }


}
