<template>
  <div>
    <div class="card" style="margin: 10px 0 20px 0;">{{ data.user?.name }}，欢迎使用食堂管理系统！</div>
    <div  style=" display: flex " >
      <div class="card" style="flex: 1; height: 350px;">
        <div class="card-title" style="margin: 10px 0 20px 15px; font-weight: bold; font-size: 20px; ">公告</div>
        <el-timeline style="max-width: 600px">
          <el-timeline-item v-for="(activity, index) in data.noticeData" :key="index" :timestamp="activity.time">
            {{ activity.content }}
          </el-timeline-item>
        </el-timeline>
      </div>
      <div style="flex: 1;"></div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'

// 用户表单数据
//公告的数据
const data = reactive({
  user: JSON.parse(localStorage.getItem('project-user') || '{}'),
  noticeData: {}
}
)
// 加载公告的函数
const loadNotice = () => {
  request.get('/notice/selectAll').then(res => {
    if (res.code === '200') {
      data.noticeData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadNotice()
</script>