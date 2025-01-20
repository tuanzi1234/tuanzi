package com.example.service;

import com.example.entity.Sideshow;
import com.example.mapper.SideshowMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SideshowService {

    @Resource
    private SideshowMapper sideshowMapper;// 注入SideshowMapper，用于数据库操作

    // 查询所有轮播图
    public List<Sideshow> selectAll(Sideshow sideshow) {
        return sideshowMapper.selectAll(sideshow);
    }

    // 根据id查询轮播图
    public Sideshow selectById(Integer id) {
        return sideshowMapper.selectById(id);
    }

    // 分页查询轮播图
    public PageInfo<Sideshow> selectPage(Sideshow sideshow, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<Sideshow> list = sideshowMapper.selectAll(sideshow); // 执行查询，根据传入的查询条件获取轮播图列表
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加轮播图
    public void add(Sideshow sideshow) {
        sideshowMapper.insert(sideshow);// 调用mapper层添加轮播图
    }

    // 根据id更新轮播图信息
    public void updateById(Sideshow sideshow) {
        sideshowMapper.updateById(sideshow);
    }

    // 根据id删除单个轮播图
    public void deleteById(Integer id) {
        sideshowMapper.deleteById(id);
    }


    // 批量删除轮播图
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            sideshowMapper.deleteById(id);
        }
    }


}
