<template>
  <div class="front-header">
    <div class="front-header-left">
      <!------------------ 标题及logo --------------------->
      <div class="front-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">食堂管理</div>
      </div>
    </div>
    <!---------------------- 导航栏 ------------------------->
    <div class="front-header-center">
      <el-menu class="front-header-center-menu" :default-active="router.currentRoute.value.path" router mode="horizontal" >
        <el-menu-item index="/front/home">首页</el-menu-item>
        <el-menu-item v-if="data.user && data.user.name && data.user.role === 'STUDENT'" index="/front/dishItem">我的点单</el-menu-item>
        <el-menu-item v-if="data.user && data.user.name && data.user.role === 'STUDENT'" index="/front/orders">我的订单</el-menu-item>
        <el-menu-item index="/front/information">食堂资讯</el-menu-item>
        <el-menu-item v-if="data.user && data.user.name && data.user.role === 'STUDENT'" index="/front/feedback">反馈意见</el-menu-item>
        <el-menu-item index="/front/Notice">系统公告</el-menu-item>
      </el-menu>
    </div>
    <!------------------------------------- 搜索栏 ---------------------------------------->
    <div style="width: 500px;">
      <el-input v-model="data.name" prefix-icon="Search" style="width:240px; margin-right: 10px;"
        placeholder="请输入菜品名称"></el-input>
      <el-button @click="navTo('/front/search?name=' + data.name)">搜索</el-button>
    </div>
    <!---------------------- 个人信息/下拉菜单/登录注册 ------------------------->
    <div class="front-header-right">
      <!---------------------- 登录/注册 ------------------------->
      <div v-if="!data.user.id">
        <el-button @click="router.push('/login')">登录</el-button>
        <el-button @click="router.push('/register')">注册</el-button>
      </div>
      <!---------------------- 退出登录 ------------------------->
      <div v-else>
        <el-dropdown style="cursor: pointer;">
          <div style="padding-right: 20px; display: flex; align-items: center; ">
            <img style="width: 40px; height: 40px; border-radius: 50%;  object-fit: cover; " :src="data.user.avatar"
              alt="">
            <span style="margin-left: 5px; font-size: 15px; color: #333; font-weight: 500;">{{ data.user.name
              }}</span><el-icon style="color: #333;"><arrow-down></arrow-down></el-icon>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item v-if="data.user && data.user.name && data.user.role === 'STUDENT'" @click="$router.push('/front/person')">个人中心</el-dropdown-item>
              <el-dropdown-item v-if="data.user && data.user.name && data.user.role === 'STUDENT'" @click="$router.push('/front/history')">历史浏览</el-dropdown-item>
              <el-dropdown-item v-if="data.user && data.user.name && data.user.role === 'STUDENT'" @click="$router.push('/front/praise')">我赞过的</el-dropdown-item>
              <el-dropdown-item v-if="data.user && data.user.name && data.user.role === 'STUDENT'" @click="$router.push('/front/collect')">我的收藏</el-dropdown-item>
              <el-dropdown-item v-if="data.user && data.user.name && data.user.role === 'STUDENT'" @click="$router.push('/front/myFeedback')">我的反馈</el-dropdown-item>
              <el-dropdown-item v-if="data.user && data.user.name && data.user.role === 'STUDENT'" @click="$router.push('/front/password')">修改密码</el-dropdown-item>
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
  <div class="main-body">
    <router-view @updateUser="updateUser"></router-view>
  </div>
</template>

<script setup>
import router from '@/router/index.js'
import { reactive } from 'vue'
import request from '@/utils/request'

// 用户表单数据
const data = reactive({
  user: JSON.parse(localStorage.getItem('project-user')) || '{}',
  top: null,
  noticeData: [],
  name: null,
})
// 在前端同步更新用户信息的函数
const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem('project-user') || '{}')
}

// 加载公告的函数
const loadNotice = () => {
  request.get('/notice/selectAll').then(res => {
    data.noticeData = res.data;
    let i = 0;
    // 轮播公告
    if (data.noticeData.length && data.noticeData) {
      data.top = data.noticeData[0].content
      setInterval(() => {
        data.top = data.noticeData[i].content
        i++
        if (i === data.noticeData.length) {
          i = 0
        }
      }, 3000)
    }
  })
}
loadNotice()
// 退出登录的函数
const logout = () => {
  // 清空本地存储
  localStorage.removeItem('project-user');
  // 退出登录
  setInterval(() => {
    location.href = '/login'
  }, 500)
}

//搜索功能跳转页面的函数
const navTo = (url) => {
  if (!data.name) {
    ElMessage.error('请输入搜索内容')
    return 
  }
  location.href = url
}
</script>



<style scoped>
@import '@/assets/css/front.css'
</style>