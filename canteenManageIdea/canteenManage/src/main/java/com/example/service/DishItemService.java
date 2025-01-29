package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Notice;
import com.example.mapper.NoticeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Resource
    private NoticeMapper noticeMapper;// 注入NoticeMapper，用于数据库操作

    // 查询所有系统公告
    public List<Notice> selectAll(Notice notice) {
        return noticeMapper.selectAll(notice);
    }

    // 根据id查询系统公告
    public Notice selectById(Integer id) {
        return noticeMapper.selectById(id);
    }

    // 分页查询系统公告
    public PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<Notice> list = noticeMapper.selectAll(notice); // 执行查询，根据传入的查询条件获取系统公告列表
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加系统公告
    public void add(Notice notice) {
        notice.setTime(DateUtil.now());// 设置公告发布时间
        noticeMapper.insert(notice);// 调用mapper层添加系统公告
    }

    // 根据id更新系统公告信息
    public void updateById(Notice notice) {
        noticeMapper.updateById(notice);
    }

    // 根据id删除单个系统公告
    public void deleteById(Integer id) {
        noticeMapper.deleteById(id);
    }


    // 批量删除系统公告
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            noticeMapper.deleteById(id);
        }
    }


}
