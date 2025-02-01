package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HtmlUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.entity.Information;
import com.example.mapper.AdminMapper;
import com.example.mapper.InformationMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationService {

    @Resource
    private InformationMapper informationMapper;// 注入InformationMapper，用于数据库操作
    @Autowired
    private AdminMapper adminMapper;

    // 查询所有食堂资讯
    public List<Information> selectAll(Information information) {
        return informationMapper.selectAll(information);
    }

    // 根据id查询食堂资讯
    public Information selectById(Integer id) {
        return informationMapper.selectById(id);
    }

    // 分页查询食堂资讯
    public PageInfo<Information> selectPage(Information information, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<Information> list = informationMapper.selectAll(information); // 执行查询，根据传入的查询条件获取食堂资讯列表
        Account account = TokenUtils.getCurrentAccount(); // 获取当前登录用户的信息
        for (Information dbInformation : list) {
            if (account == null || !RoleEnum.ADMIN.name().equals(account.getRole())) {
                dbInformation.setContent(HtmlUtil.cleanHtmlTag(dbInformation.getContent()));//去掉富文本编辑器的html标签
            }
            Admin admin = adminMapper.selectByUsername(dbInformation.getUsername());
            dbInformation.setAvatar(admin.getAvatar());//引入发布人的头像
        }
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加食堂资讯
    public void add(Information information) {
        //新增时获取当前新增资讯的发布人
        Account account = TokenUtils.getCurrentAccount();
        information.setUsername(account.getUsername());
        information.setTime(DateUtil.now());// 设置公告发布时间
        informationMapper.insert(information);// 调用mapper层添加食堂资讯
    }

    // 根据id更新食堂资讯信息
    public void updateById(Information information) {

        informationMapper.updateById(information);
    }

    // 根据id删除单个食堂资讯
    public void deleteById(Integer id) {
        informationMapper.deleteById(id);
    }


    // 批量删除食堂资讯
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            informationMapper.deleteById(id);
        }
    }


}
