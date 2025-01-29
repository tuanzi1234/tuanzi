<template>
  <div class="manager-container">
    <!------------------- 头部 ----------------->
    <div class="manager-header">
      <!------------------ 标题及logo --------------------->
      <div class="manager-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">食堂管理</div>
      </div>
      <!------------------ 顶部导航栏 --------------------->
      <div class="manager-header-center">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/manager/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>
            {{ router.currentRoute.value.meta.name }}
          </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!------------------ 下拉菜单展示个人信息 --------------------->
      <div class="manager-header-right">
        <el-dropdown style="cursor: pointer;">
          <div style="padding-right: 20px; display: flex; align-items: center;">
            <img style="width: 40px; height: 40px; border-radius: 50%; object-fit: cover; " :src="data.user.avatar" alt="">
            <span style="margin-left: 5px; color: #FFFFFF	;">{{data.user.name}}</span><el-icon color="#FFFFFF"><arrow-down></arrow-down></el-icon>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="router.push('/manager/person')">个人资料</el-dropdown-item>
              <el-dropdown-item @click="router.push('/manager/password')">修改密码</el-dropdown-item>
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <!------------------- 主体（头部结束） ----------------->
    <div class="manager-body" style="display: flex;">
      <div class="manager-main-left">
        <!------------------ 左侧导航栏 --------------------->
        <el-menu router 
                 :default-openeds="['1', '2']" 
                 :default-active="router.currentRoute.value.path">
          <!------- 首页 -------->
          <el-menu-item index="/manager/home">
            <el-icon>
              <HomeFilled />
            </el-icon>
            <span>系统首页</span>
          </el-menu-item>
          <!------- 信息管理 -------->
          <el-sub-menu index="1">
            <template #title>
              <el-icon>
                <Menu />
              </el-icon>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/manager/category">菜品分类管理</el-menu-item>
            <el-menu-item index="/manager/dish">菜品信息管理</el-menu-item>
            <el-menu-item index="/manager/sideshow">轮播图管理</el-menu-item>
            <el-menu-item index="/manager/notice">系统公告管理</el-menu-item>
            <el-menu-item index="/manager/history">浏览记录管理</el-menu-item>
            <el-menu-item index="/manager/praise">点赞记录管理</el-menu-item>
            <el-menu-item index="/manager/feedback">反馈意见管理</el-menu-item>
            <el-menu-item index="/manager/collect">收藏记录管理</el-menu-item>
            <el-menu-item index="/manager/information">食堂资讯管理</el-menu-item>
            <el-menu-item index="/manager/dishItem">学生点单管理</el-menu-item>
            <el-menu-item index="/manager/orders">学生订单管理</el-menu-item>
          </el-sub-menu>
          <!---------- 用户管理 ---------->
          <el-sub-menu index="2">
            <template #title>
              <el-icon>
                <Menu />
              </el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/manager/admin">管理员信息</el-menu-item>
            <el-menu-item index="/manager/student">学生信息</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </div>
      <div class="manager-main-right">
        <router-view @updateUser="updateUser"></router-view>
      </div>
    </div>
    <!------------------- 底部（主体部分结束） ----------------->

  </div>
</template>
<script setup>
import router from '@/router/index.js';
import { reactive } from 'vue';

const data = reactive({
  user: JSON.parse(localStorage.getItem('project-user') || '{}')
})

// 退出登录的函数
const logout = () => {
  // 清空本地存储
  localStorage.removeItem('project-user');
  // 退出登录
  setInterval(() => {
    location.href = '/login'
  }, 500)
}

if(!data.user.id) {
  logout();
  ElMessage.error('请先登录');
}

// 在前端同步更新用户信息的函数
const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem('project-user') || '{}')
}

</script>
<style scoped>
@import "@/assets/css/manager.css";
</style>