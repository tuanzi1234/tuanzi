<template>
  <div style="height: 100vh; display: flex; justify-content: center; align-items: center;">
    <div style="width: 30%;">
      <img src="../assets/imgs/404.png" alt="" style="width: 100%;">
      <div style="font-size: 30px; text-align: center;">找不到页面！<router-link :to="homeRoute">请返回主页</router-link></div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

// 定义 homeRoute 变量
const homeRoute = ref('/');
// 从本地存储中获取用户信息
const user = JSON.parse(localStorage.getItem('project-user' || '{}'))
// 根据用户角色设置 homeRoute
if (!user) {
  homeRoute.value = '/front/home';
} else if (user.role === 'STUDENT') {
  homeRoute.value = '/front/home';
}
else if (user.role === 'ADMIN') {
  homeRoute.value = '/manager/home';
}

</script>