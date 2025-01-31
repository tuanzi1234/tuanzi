package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.entity.*;
import com.example.exception.CustomException;
import com.example.mapper.*;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrdersService {

    @Resource
    private OrdersMapper ordersMapper;// 注入OrdersMapper，用于数据库操作
    @Resource
    private OrdersItemMapper ordersItemMapper;// 注入OrdersItemMapper，用于数据库操作
    @Resource
    private DishItemMapper dishItemMapper;// 注入DishItemMapper，用于数据库操作
    @Resource
    private StudentMapper studentMapper;// 注入StudentMapper，用于数据库操作
    @Resource
    private DishMapper dishMapper;

    // 查询所有订单信息
    public List<Orders> selectAll(Orders orders) {
        return ordersMapper.selectAll(orders);
    }

    // 根据id查询订单信息
    public Orders selectById(Integer id) {
        return ordersMapper.selectById(id);
    }

    // 分页查询订单信息
    public PageInfo<Orders> selectPage(Orders orders, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);// 开启分页功能，指定当前页码和每页显示的记录数
        List<Orders> list = ordersMapper.selectAll(orders); // 执行查询，根据传入的查询条件获取订单信息列表
        return PageInfo.of(list);// 将查询结果封装成PageInfo对象，便于获取分页信息
    }

    // 添加订单信息
    public void add(Orders orders) {
        ordersMapper.insert(orders);// 调用mapper层添加订单信息
    }

    // 根据id更新订单信息
    public void updateById(Orders orders) {
        ordersMapper.updateById(orders);
    }

    // 恢复订单中的库存
    private void resetDish(Integer orderId) {
        // 查询所有订单详细信息
        OrdersItem query = new OrdersItem();
        query.setOrderId(orderId);
        List<OrdersItem> ordersItems = ordersItemMapper.selectAll(query);
        for (OrdersItem ordersItem : ordersItems) {
            // 查询对应的菜品
            Dish dish = dishMapper.selectById(ordersItem.getDishId());
            if (ObjectUtil.isNotNull(dish)) {
                //查询订单状态，若已完成，则不恢复库存

                    // 恢复库存
                    dish.setNum(dish.getNum() + ordersItem.getNum());
                    dishMapper.updateById(dish);

            }
        }
    }
    // 根据id删除单个订单信息
    public void deleteById(Integer id) {

        // 恢复库存
        Orders orders = ordersMapper.selectById(id); // 获取订单对象
        if (!"已上餐".equals(orders.getStatus())) { // 检查订单状态是否为“已完成”
            resetDish(id);
        }
        ordersMapper.deleteById(id);
        // 删除订单详细信息
        ordersItemMapper.deleteByOrderId(id);
    }


    // 批量删除订单信息
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {

            Orders orders = ordersMapper.selectById(id); // 获取订单对象
            if (!"已上餐".equals(orders.getStatus())) { // 检查订单状态是否为“已完成”
                // 恢复库存
                resetDish(id);
            }
            // 删除订单信息
            ordersMapper.deleteById(id);
            // 删除订单详细信息
            ordersItemMapper.deleteByOrderId(id);
        }
    }


    // 创建订单
    @Transactional(rollbackFor = Exception.class)
    public void create(List<DishItem> dishes) {
        //获取用户信息
        Account currentAccount = TokenUtils.getCurrentAccount();
        //往订单信息表初始化数据
        // 创建一个新的订单对象
        Orders orders = new Orders();
        // 初始化订单总价为0.0
        Double total = 0.0;
        // 遍历所有的菜品项，计算总价
        for (DishItem dishItem : dishes) {
            // 获取当前菜品项的菜品信息
            Dish dish = dishItem.getDish();
            // 累加当前菜品的价格到总价中
            total += dish.getPrice() * dishItem.getNum();
            // 减去库存
            dish.setNum(dish.getNum() - dishItem.getNum());
            dishMapper.updateById(dish);
        }
        // 设置订单的总价
        orders.setPrice(total);
        //判断学生余额是否足够
        Student student = studentMapper.selectById(currentAccount.getId());
        if (student.getAccount() < total) {
            throw new CustomException("500","余额不足，请到个人中心充值");
        }
        // 设置订单的学号为当前账户的ID
        orders.setStudentId(currentAccount.getId());
        // 设置订单号为当前时间的格式化字符串，以确保唯一性
        orders.setOrderNo(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        // 设置订单的创建时间为当前时间
        orders.setCreateTime(DateUtil.now());
        // 设置订单的初始状态为"待支付"
        orders.setStatus("待支付");
        // 插入订单到数据库并获取生成的订单ID
        ordersMapper.insert(orders);

// 遍历菜品列表，为每个菜品创建订单详细信息记录
        for (DishItem dishItem : dishes) {
            // 创建一个新的订单详细信息对象
            OrdersItem ordersItem = new OrdersItem();
            // 设置订单ID
            ordersItem.setOrderId(orders.getId());
            // 设置菜品数量
            ordersItem.setNum(dishItem.getNum());
            // 设置学生ID
            ordersItem.setStudentId(dishItem.getStudentId());
            // 设置菜品ID
            ordersItem.setDishId(dishItem.getDishId());
            // 将订单详细信息插入到数据库
            ordersItemMapper.insert(ordersItem);
        }

        //清空点单中的数据
        for (DishItem dishItem : dishes) {
            dishItemMapper.deleteById(dishItem.getId());
        }
    }

    // 支付订单
    @Transactional(rollbackFor = Exception.class)
    public Student pay(Orders orders) {
        //获取用户信息
        Account currentAccount = TokenUtils.getCurrentAccount();
        //判断学生余额是否足够
        Student student = studentMapper.selectById(currentAccount.getId());
        if (student.getAccount() < orders.getPrice()) {
            throw new CustomException("500","余额不足，请到个人中心充值");
        }
        //更新学生余额
        student.setAccount(student.getAccount() - orders.getPrice());
        studentMapper.updateById(student);
        //支付成功，扣除余额,更新状态
        orders.setPayTime(DateUtil.now());
        orders.setPayNo(RandomUtil.randomNumbers(30));
        orders.setStatus("已支付");
        ordersMapper.updateById(orders);

        // 生成token

        String token = TokenUtils.createToken(student.getId() + "-" + student.getRole(), student.getPassword());
        student.setToken(token);
        return student;

    }
}
