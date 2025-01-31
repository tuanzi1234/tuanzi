package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Warehouse;
import com.example.mapper.WarehouseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    @Resource
    private WarehouseMapper warehouseMapper;// 注入WarehouseMapper，用于数据库操作

    // 查询所有库存信息
    public List<Warehouse> selectAll(Warehouse warehouse) {
        return warehouseMapper.selectAll(warehouse);
    }

    // 根据id查询库存信息
    public Warehouse selectById(Integer id) {
        return warehouseMapper.selectById(id);
    }

    // 分页查询库存信息
    public PageInfo<Warehouse> selectPage(Warehouse warehouse, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<Warehouse> list = warehouseMapper.selectAll(warehouse); // 执行查询，根据传入的查询条件获取库存信息列表
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加库存信息
    public void add(Warehouse warehouse) {
        warehouse.setTime(DateUtil.now());// 设置材料购入时间
        warehouseMapper.insert(warehouse);// 调用mapper层添加库存信息
    }

    // 根据id更新库存信息信息
    public void updateById(Warehouse warehouse) {
        warehouseMapper.updateById(warehouse);
    }

    // 根据id删除单个库存信息
    public void deleteById(Integer id) {
        warehouseMapper.deleteById(id);
    }


    // 批量删除库存信息
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            warehouseMapper.deleteById(id);
        }
    }


}
