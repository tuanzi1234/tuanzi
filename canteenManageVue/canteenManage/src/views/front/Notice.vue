<template>
<div class="card" style="width: 50%; margin: 20px auto;">
  <div class="card-title" style="margin: 0 0 20px 15px; font-weight: bold; font-size: 20px; ">个人中心</div>
  <el-timeline style="max-width: 600px">
    <el-timeline-item
      v-for="(activity) in data.noticeData"
      :key="activity.id"
      color= "#409EFF"
      :timestamp="activity.time"
      placement="top"
    >
    <div style=" font-weight: bold; font-size: 15px; padding-bottom:10px ;">{{ activity.title }}</div>
      {{ activity.content }}
    </el-timeline-item>
  </el-timeline>
</div>
</template>

<script setup>
import { reactive } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'


// 公告表单数据
const data = reactive({
  noticeData: [],// 表格数据对象，初始为空
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

<style scoped>

</style>