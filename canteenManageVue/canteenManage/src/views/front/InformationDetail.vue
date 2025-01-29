<template>
  <div style="width: 60%; margin: 20px auto;">
    <div style="font-size: 20px; font-weight: bold; text-align: center;">{{ data.informationData.title }}</div>
    <div style="display: flex; margin-top: 20px; align-items: center; justify-content: center; color:#828282"> 
      <div>发布人：{{ data.informationData.username }}</div>
      <div style="margin: 0 20px;">{{ data.informationData.time }}</div>
      <el-icon size="20px"><View /></el-icon>
      <div>{{ data.informationData.num }}</div>
    </div>
    <div v-html="data.informationData.content" style="margin-top: 20px;"></div>
  </div>
</template>

<script setup>
import request from '@/utils/request';
import { reactive } from 'vue';
import { ElMessage } from 'element-plus';
import router from '@/router';


const data = reactive({
  informationDataId: router.currentRoute.value.query.id,
  informationData: {},
})

//加载食堂资讯详情的函数
const loadInformation = () => {
  data.informationDataId = router.currentRoute.value.query.id
  request.get('/information/selectById/' + data.informationDataId).then(res => {
    if (res.code === '200') {
      data.informationData = res.data 
      updateNum()
    }else {
      ElMessage.error(res.msg)
    }
  })
}
loadInformation()

//更新浏览量的函数
const updateNum = () => {
  data.informationData.num = data.informationData.num + 1
  request.put('/information/update', data.informationData)
}

</script>