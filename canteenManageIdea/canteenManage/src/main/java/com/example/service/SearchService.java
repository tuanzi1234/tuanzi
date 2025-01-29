package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Search;
import com.example.mapper.SearchMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Resource
    private SearchMapper searchMapper;// 注入SearchMapper，用于数据库操作

    // 查询所有搜索信息
    public List<Search> selectAll(Search search) {
        return searchMapper.selectAll(search);
    }

    // 根据id查询搜索信息
    public Search selectById(Integer id) {
        return searchMapper.selectById(id);
    }

    // 分页查询搜索信息
    public PageInfo<Search> selectPage(Search search, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<Search> list = searchMapper.selectAll(search); // 执行查询，根据传入的查询条件获取搜索信息列表
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加搜索信息
    public void add(Search search) {
        //添加之前查询是否有相同的搜索信息，如果有删掉原来的记录，再添加新记录，如果没有则添加
        List<Search> searches = searchMapper.selectAll(search);
        if (CollectionUtil.isNotEmpty(searches)) {
            Search dbSearch = searches.get(0);
            deleteById(dbSearch.getId());
        }
        searchMapper.insert(search);// 调用mapper层添加搜索信息
    }



    // 根据id删除单个搜索信息
    public void deleteById(Integer id) {
        searchMapper.deleteById(id);
    }


    // 批量删除搜索信息
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            searchMapper.deleteById(id);
        }
    }


}
